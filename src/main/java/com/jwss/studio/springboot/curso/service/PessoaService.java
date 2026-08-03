package com.jwss.studio.springboot.curso.service;




import org.springframework.stereotype.Service;

import com.jwss.studio.springboot.curso.repository.PessoaRepository;

@Service
public class PessoaService {

private final PessoaRepository pessoaRepository;


    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
}
