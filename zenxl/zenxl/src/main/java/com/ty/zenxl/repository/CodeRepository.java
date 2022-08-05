package com.ty.zenxl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.Code;

/**
 * Interface to interact with db for {@code Code} entity class. Extends the
 * {@code JpaRepository} interface.
 * 
 * @author Abhishek
 * @verion 1.0
 */
public interface CodeRepository extends JpaRepository<Code, Integer> {

}
