package com.jp.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.website.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
  List<Technology> findTechnologiesByProjectsId(Long projectId);
}
