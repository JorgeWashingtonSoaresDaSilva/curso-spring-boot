package com.jwss.studio.springboot.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.jwss.studio.springboot.curso.entity.ProfissaoEntity;



public interface ProfissaoRepository extends JpaRepository<ProfissaoEntity, Long> {

}
