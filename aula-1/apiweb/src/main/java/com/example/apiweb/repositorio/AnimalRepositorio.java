package com.example.apiweb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apiweb.dominio.Animal;

@Repository
public interface AnimalRepositorio extends JpaRepository<Animal, Long> {}
