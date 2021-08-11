package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Registro;

@Repository
public interface IRegistroRepo extends JpaRepository<Registro, Long>{
	

}
