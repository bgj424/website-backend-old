package com.jp.website.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jp.website.exception.ResourceNotFoundException;
import com.jp.website.model.Project;
import com.jp.website.model.Technology;
import com.jp.website.repository.ProjectRepository;
import com.jp.website.repository.TechnologyRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TechnologyController {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TechnologyRepository technologyRepository;

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/technologies")
  public ResponseEntity<List<Technology>> getAllTechnologies() {
    List<Technology> technologies = new ArrayList<Technology>();

    technologyRepository.findAll().forEach(technologies::add);

    if (technologies.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(technologies, HttpStatus.OK);
  }
  
  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/projects/{projectId}/technologies")
  public ResponseEntity<List<Technology>> getAllTechnologiesByProjectId(@PathVariable(value = "projectId") Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ResourceNotFoundException("Not found Project with id = " + projectId);
    }

    List<Technology> technologies = technologyRepository.findTechnologiesByProjectsId(projectId);
    return new ResponseEntity<>(technologies, HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/technologies/{id}")
  public ResponseEntity<Technology> getTechnologiesById(@PathVariable(value = "id") Long id) {
    Technology technology = technologyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Technology with id = " + id));

    return new ResponseEntity<>(technology, HttpStatus.OK);
  }
  
  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/technologies/{technologyId}/projects")
  public ResponseEntity<List<Project>> getAllProjectsByTechnologyId(@PathVariable(value = "technologyId") Long technologyId) {
    if (!technologyRepository.existsById(technologyId)) {
      throw new ResourceNotFoundException("Not found Technology  with id = " + technologyId);
    }

    List<Project> projects = projectRepository.findProjectsByTechnologiesId(technologyId);
    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/projects/{projectId}/technologies")
  public ResponseEntity<Technology> addTechnology(@PathVariable(value = "projectId") Long projectId, @RequestBody Technology technologyRequest) {
    Technology technology = projectRepository.findById(projectId).map(project -> {
      long technologyId = technologyRequest.getId();
      
      // exists
      if (technologyId != 0L) {
        Technology _technology = technologyRepository.findById(technologyId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Technology with id = " + technologyId));
        
        project.removeTechnology(technologyId);
        project.addTechnology(technologyRequest);
        projectRepository.save(project);
        return _technology;
      }
      
      // create new
      project.addTechnology(technologyRequest);
      return technologyRepository.save(technologyRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + projectId));

    return new ResponseEntity<>(technology, HttpStatus.CREATED);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/technologies/{id}")
  public ResponseEntity<Technology> updateTechnology(@PathVariable("id") long id, @RequestBody Technology technologyRequest) {
    Technology technology = technologyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("TechnologyId " + id + "not found"));

    technology.setName(technologyRequest.getName());
    technology.setType(technologyRequest.getType());

    return new ResponseEntity<>(technologyRepository.save(technology), HttpStatus.OK);
  }
 
  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/projects/{projectId}/technologies/{technologyId}")
  public ResponseEntity<HttpStatus> deleteTechnologyFromProject(@PathVariable(value = "projectId") Long projectId, @PathVariable(value = "technologyId") Long technologyId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Project with id = " + projectId));
    
    project.removeTechnology(technologyId);
    projectRepository.save(project);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/technologies/{id}")
  public ResponseEntity<HttpStatus> deleteTechnology(@PathVariable("id") long id) {
    technologyRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
