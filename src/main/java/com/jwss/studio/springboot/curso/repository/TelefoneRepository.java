package com.jwss.studio.springboot.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jwss.studio.springboot.curso.entity.TelefoneEntity;


@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<TelefoneEntity, Long>{
	@Query("select t from TelefoneEntity t where t.pessoa.id = ?1")
	public List<TelefoneEntity> getTelefones(Long pessoaid);
}
