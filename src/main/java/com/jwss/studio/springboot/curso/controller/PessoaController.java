package com.jwss.studio.springboot.curso.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jwss.studio.springboot.curso.repository.ProfissaoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jwss.studio.springboot.curso.entity.PessoaEntity;
import com.jwss.studio.springboot.curso.service.PessoaService;
import com.jwss.studio.springboot.curso.service.ReportUtilService;
import com.jwss.studio.springboot.curso.service.TelefoneService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller

public class PessoaController {


	private final PessoaService pessoaService;
	private final TelefoneService telefoneService;
	private final ReportUtilService reportUtilService;
	private final ProfissaoRepository profissaoRepository;

	PessoaController(PessoaService pessoaService, TelefoneService  telefoneService, ReportUtilService reportUtilService, ProfissaoRepository profissaoRepository) {
		this.pessoaService = pessoaService;
		this.telefoneService = telefoneService;
		this.reportUtilService = reportUtilService;
        this.profissaoRepository = profissaoRepository;
    }

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio(PessoaEntity pessoa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");// redireciona para tela de cadastroPessoa

		Iterable<PessoaEntity> pessoasIt = pessoaService.listarPessoas(pessoa); // carrega as pessoas cadastradas no banco de dados
		modelAndView.addObject("pessoas", pessoasIt);// adiciona a listas de objetos modelAndView mostra na tela
		modelAndView.addObject("profissoes",profissaoRepository.findAll());
		modelAndView.addObject("pessoaobj",new PessoaEntity()); // limpa modelAndView com uma pessoaEntity vazia
		return modelAndView;

	}
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa",consumes = {"multipart/form-data"})
	public ModelAndView salvar(@Valid PessoaEntity pessoa, BindingResult bindingResult, final MultipartFile file) throws IOException {
		pessoa.setTelefones(telefoneService.listarTelefones(pessoa.getId()));
		if(bindingResult.hasErrors()) {
			ModelAndView modelandView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<PessoaEntity> pessoasIt = pessoaService.listarPessoas(pessoa);
			modelandView.addObject("pessoas", pessoasIt);
			modelandView.addObject("pessoaobj",pessoa);

			List<String> msg = new ArrayList<>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			modelandView.addObject("msg", msg);
			return modelandView;
		}
		if (file.getSize() > 0){
			pessoa.setCurriculo(file.getBytes());
		}else {
			if (pessoa.getId() != null && pessoa.getId() > 0){
				byte[] curriculoTemp;
				curriculoTemp = pessoaService.carregaPessoa(pessoa.getId()).get().getCurriculo();
				pessoa.setCurriculo(curriculoTemp);
            }
		}
		pessoaService.salvar(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("profissoes",profissaoRepository.findAll());
		Iterable<PessoaEntity> pessoasIt = pessoaService.listarPessoas(pessoa);
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj",new PessoaEntity());

		return andView;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas(PessoaEntity pessoa) {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<PessoaEntity> pessoasIt = pessoaService.listarPessoas(pessoa);
		andView.addObject("profissoes",profissaoRepository.findAll());
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj",new PessoaEntity());
		return andView;
	}
	@GetMapping(value ="/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Optional<PessoaEntity> pessoa = pessoaService.carregaPessoa(idpessoa);
		modelAndView.addObject("profissoes",profissaoRepository.findAll());
		modelAndView.addObject("pessoaobj",pessoa.get());
		return modelAndView;
	}
	@GetMapping(value = "/deletarpessoa/{idpessoa}")
	public ModelAndView deletar(@PathVariable("idpessoa") Long idpessoa, PessoaEntity pessoaEntity) {

		pessoaService.deletarPessoa(idpessoa);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaService.listarPessoas(pessoaEntity));
		modelAndView.addObject("pessoaobj",new PessoaEntity());
		return modelAndView;
	}
	@PostMapping(value = "**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisar") String nomepesquisar,
			@RequestParam("sexopesquisa") String sexopesquisa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		if(sexopesquisa != null && !sexopesquisa.isEmpty()) {
			modelAndView.addObject("pessoas",pessoaService.pesquisarPorNomeSexo(nomepesquisar,sexopesquisa));

		} else {
			modelAndView.addObject("pessoas",pessoaService.pesquisarPorNome(nomepesquisar));
		}

		modelAndView.addObject("pessoaobj",new PessoaEntity());
		return modelAndView;
	}
	@GetMapping(value = "**/pesquisarpessoa")
	public void imprimePDF(@RequestParam("nomepesquisar") String nomepesquisar,
			@RequestParam("sexopesquisa") String sexopesquisa,PessoaEntity pessoa1 ,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			System.out.println("Imprime PDF");
			List<PessoaEntity> pessoas = new ArrayList<>();
			if(sexopesquisa != null && !sexopesquisa.isEmpty()
					&& nomepesquisar != null && !nomepesquisar.isEmpty()) {
				pessoas = pessoaService.pesquisarPorNomeSexo(nomepesquisar, sexopesquisa);
			}else if(nomepesquisar != null && !nomepesquisar.isEmpty()) {
				pessoas = pessoaService.pesquisarPorNome(nomepesquisar);
			}else if(sexopesquisa != null && !sexopesquisa.isBlank()) {
				pessoas = pessoaService.pesquisarPorSexo(sexopesquisa);
			}else {

				Iterable<PessoaEntity> pessoasIt = pessoaService.listarPessoas(pessoa1);
				for(PessoaEntity pessoa : pessoasIt) {
					pessoas.add(pessoa);
				}
			}
			// Chame o serviço que faz geração do relatório
			byte[] pdf = reportUtilService.gerarRelatorio(pessoas, "pessoa", request.getServletContext());

			// Tamanho da resposta
			response.setContentLength(pdf.length);

			// Definir na resposta tipo de arquivo
			response.setContentType("application/octet-stream");
			// definir o cabeçalho da nossa resposta
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
			response.setHeader(headerKey, headerValue);
			// Finaliza a reposta pro navegador
			response.getOutputStream().write(pdf);

	}






}
