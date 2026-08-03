package com.jwss.studio.springboot.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jwss.studio.springboot.curso.entity.TelefoneEntity;
import com.jwss.studio.springboot.curso.repository.TelefoneRepository;

@Service
public class TelefoneService {

private final TelefoneRepository telefoneRepository;

    TelefoneService(TelefoneRepository telefoneRepository) {
        this.telefoneRepository = telefoneRepository;
    }

    public String salvarTelefone(TelefoneEntity telefone) {
    	telefoneRepository.save(telefone);
    	return "cadastro/telefones";
    }
    public List<TelefoneEntity> listarTelefones(Long pessoaid){
    	return telefoneRepository.getTelefones(pessoaid);
    }
    public Optional<TelefoneEntity> carregaTelefone (Long telefoneid) {
    	return telefoneRepository.findById(telefoneid);
    }

    public void deletarTelefone(Long telefoneid) {

    	telefoneRepository.deleteById(telefoneid);


    }

}
