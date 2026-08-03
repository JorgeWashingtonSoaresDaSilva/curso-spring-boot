package com.jwss.studio.springboot.curso.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jwss.studio.springboot.curso.entity.PessoaEntity;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long>  {

	@Query("select p from PessoaEntity p where p.nome like %?1%")
	List<PessoaEntity> findPessoaByName(String nome);
	@Query("select p from PessoaEntity p where p.sexo = ?1")
	List<PessoaEntity> findPessoaBySexo(String sexo);
	@Query("select p from PessoaEntity p where p.nome like %?1% and p.sexo =?2")
	List<PessoaEntity> findPessoaByNameSexo(String nome, String sexo);




}
