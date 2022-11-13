package com.jp.website.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "technologies")
public class Technology {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;
  
  @Column(name = "type")
  private String type;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      },
      mappedBy = "technologies")
  @JsonIgnore
  private Set<Project> projects = new HashSet<>();

  public String getType() {
	return type;
  }

  public void setType(String type) {
	this.type = type;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public Technology() {

  }
  
  public Technology(String name, String type) {
    this.name = name;
    this.type = type;
  }
  
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Project> getProjects() {
    return projects;
  }

  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }
  
  @Override
  public String toString() {
	return "Technology [id=" + id + ", name=" + name + ", type=" + type + ", projects=" + projects + ", getType()="
			+ getType() + ", getId()=" + getId() + ", getName()=" + getName() + ", getProjects()=" + getProjects()
			+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
  
}
