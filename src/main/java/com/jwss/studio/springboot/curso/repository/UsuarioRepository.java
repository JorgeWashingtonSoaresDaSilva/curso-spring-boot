package com.jwss.studio.springboot.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jwss.studio.springboot.curso.entity.UsuarioEntity;
@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

}
