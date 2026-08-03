package com.jwss.studio.springboot.curso.service;



import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jwss.studio.springboot.curso.entity.PessoaEntity;
import com.jwss.studio.springboot.curso.repository.PessoaRepository;

@Service
public class PessoaService {

private final PessoaRepository pessoaRepository;

    PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public String salvar(PessoaEntity entity) {
    		pessoaRepository.save(entity);
    		return "cadastro/cadastropessoa";
    }
    public List<PessoaEntity> listarPessoas( PessoaEntity pessoa){
    	 List<PessoaEntity> pessoas = pessoaRepository.findAll();
    	 return pessoas;
    }
    public Optional<PessoaEntity> carregaPessoa (Long pessoaid) {
    	return pessoaRepository.findById(pessoaid);
    }

    public void deletarPessoa(Long idpessoa) {

    	pessoaRepository.deleteById(idpessoa);


    }

    public List<PessoaEntity> pesquisarPorNome(String nome) {
    	List<PessoaEntity> pessoa = pessoaRepository.findPessoaByName(nome);
		return pessoa;
    }
    public List<PessoaEntity> pesquisarPorSexo(String sexo) {
    	List<PessoaEntity> pessoa = pessoaRepository.findPessoaBySexo(sexo);
		return pessoa;
    }
    public List<PessoaEntity> pesquisarPorNomeSexo(String nome, String sexo ) {
    	List<PessoaEntity> pessoa = pessoaRepository.findPessoaByNameSexo(nome, sexo);
		return pessoa;
    }


}
