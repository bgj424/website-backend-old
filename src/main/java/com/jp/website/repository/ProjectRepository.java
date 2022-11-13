package com.jp.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.website.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByYear(int year);

  List<Project> findByTitleContaining(String title);
  
  List<Project> findProjectsByTechnologiesId(Long technologyId);
}
