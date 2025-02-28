package com.mdhp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mdhp.model.Canvas;

import java.util.List;


@Repository
public interface CanvasRepository extends JpaRepository<Canvas, Long> {
    boolean existsByAreaAndActive(String area, boolean active);

    List<Canvas> findByActiveTrue();

    @Query(value = "SELECT a.area FROM canvas a WHERE a.active = true", nativeQuery = true)
    List<String> findAreaByActiveTrue();
}