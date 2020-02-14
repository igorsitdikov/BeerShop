package com.gp.beershop.repository;

import com.gp.beershop.entity.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, Integer> {

}