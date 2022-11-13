package com.jp.website.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jp.website.exception.ResourceNotFoundException;
import com.jp.website.model.Project;
import com.jp.website.repository.ProjectRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ProjectController {

  @Autowired
  ProjectRepository projectRepository;

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String title) {
    List<Project> projects = new ArrayList<Project>();

    if (title == null)
      projectRepository.findAll().forEach(projects::add);
    else
      projectRepository.findByTitleContaining(title).forEach(projects::add);

    if (projects.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/projects/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/projects")
  public ResponseEntity<Project> createProject(@RequestBody Project project) {
    Project _project = projectRepository.save(new Project(
    		project.getTitle(), 
    		project.getDescription(), 
    		project.getYear(), 
    		project.getUrl(),
    		project.getRating(),
    		project.getPlatform()
    ));
    return new ResponseEntity<>(_project, HttpStatus.CREATED);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/projects/{id}")
  public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
    Project _project = projectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + id));

    _project.setTitle(project.getTitle());
    _project.setDescription(project.getDescription());
    _project.setYear(project.getYear());
    _project.setUrl(project.getUrl());
    _project.setRating(project.getRating());
    _project.setPlatform(project.getPlatform());

    return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/projects/{id}")
  public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
    projectRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/projects")
  public ResponseEntity<HttpStatus> deleteAllProjects() {
    projectRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
