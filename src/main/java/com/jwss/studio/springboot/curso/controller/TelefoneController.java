package com.jwss.studio.springboot.curso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jwss.studio.springboot.curso.entity.PessoaEntity;
import com.jwss.studio.springboot.curso.entity.TelefoneEntity;
import com.jwss.studio.springboot.curso.service.PessoaService;
import com.jwss.studio.springboot.curso.service.TelefoneService;

@Controller
public class TelefoneController {
	private final TelefoneService telefoneService;
	private final PessoaService pessoaService;

	TelefoneController(TelefoneService telefoneService, PessoaService pessoaService) {
		this.telefoneService = telefoneService;
		this.pessoaService = pessoaService;
	}

	@GetMapping(value ="/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		Optional<PessoaEntity> pessoa = pessoaService.carregaPessoa(idpessoa);

		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneService.listarTelefones(idpessoa));
		// CORREÇÃO: Enviando um objeto de telefone vazio para o formulário th:object
		modelAndView.addObject("telefoneObj", new TelefoneEntity());

		return modelAndView;
	}

	@PostMapping("**/addfonepessoa/{pessoaid}")
	public ModelAndView adicionarTelefone(TelefoneEntity telefone, @PathVariable("pessoaid") Long pessoaid) {
		PessoaEntity pessoa = pessoaService.carregaPessoa(pessoaid).get();

		if(telefone != null && (telefone.getNumero().isEmpty()) || telefone.getTipo().isEmpty()){

			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneService.listarTelefones(pessoaid));

			List<String> msg = new ArrayList<>();
			if(telefone.getNumero().isEmpty()){
				msg.add("Numero deve ser informado");
			}
			if(telefone.getTipo().isEmpty()){
				msg.add("Tipo deve ser informado");
			}
			modelAndView.addObject("msg",msg);
			modelAndView.addObject("telefoneObj", new TelefoneEntity());
			return modelAndView;
		}


		telefone.setPessoa(pessoa);
		telefoneService.salvarTelefone(telefone);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneService.listarTelefones(pessoaid));
		// Limpa o formulário enviando um novo objeto após salvar
		modelAndView.addObject("telefoneObj", new TelefoneEntity());
		return modelAndView;
	}

	// CORREÇÃO: URL ajustada para receber também o pessoaid necessário no método
	@GetMapping(value ="/editarfonepessoa/{telefoneid}/pessoa/{pessoaid}")
	public ModelAndView editar(@PathVariable("telefoneid") Long telefoneid, @PathVariable("pessoaid") Long pessoaid) {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		PessoaEntity pessoa = pessoaService.carregaPessoa(pessoaid).get();
		Optional<TelefoneEntity> telefone = telefoneService.carregaTelefone(telefoneid);

		modelAndView.addObject("pessoaobj", pessoa);
		// Enviando o telefone carregado para edição com o mesmo nome que o formulário espera
		modelAndView.addObject("telefoneObj", telefone.get());
		modelAndView.addObject("telefones", telefoneService.listarTelefones(pessoaid));

		return modelAndView;
	}
	@GetMapping(value = "/deletarfonepessoa/{telefoneid}")
	public ModelAndView deletarTelefone(@PathVariable("telefoneid") Long telefoneid) {

		// 1. Carrega o telefone para descobrir de qual pessoa ele é antes de deletar
		Optional<TelefoneEntity> telefone = telefoneService.carregaTelefone(telefoneid);
		PessoaEntity pessoa = telefone.get().getPessoa();

		// 2. Deleta o telefone do banco de dados
		telefoneService.deletarTelefone(telefoneid); // Certifique-se de que este método existe no seu TelefoneService

		// 3. Prepara o retorno para a mesma página de telefones atualizada
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneService.listarTelefones(pessoa.getId()));
		modelAndView.addObject("telefoneObj", new TelefoneEntity()); // Reseta o formulário

		return modelAndView;
	}

}
