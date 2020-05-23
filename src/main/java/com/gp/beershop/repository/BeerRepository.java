package com.gp.beershop.repository;

import com.gp.beershop.entity.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, Long> {
    Optional<BeerEntity> findFirstByName(String name);

    List<BeerEntity> findAllById(Iterable<Long> ids);
}
