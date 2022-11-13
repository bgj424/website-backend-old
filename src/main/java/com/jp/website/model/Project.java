package com.jp.website.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @Column(name = "description", length = 10000)
  private String description;

  @Column(name = "year_created")
  private int year;
  
  public Long getRating() {
	return rating;
  }

  @Column(name = "project_url")
  private String url;
  
  @Column(name = "project_rating")
  private Long rating;
  
  @Column(name = "platform")
  private String platform;
  
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(name = "project_technologies",
        joinColumns = { @JoinColumn(name = "project_id") },
        inverseJoinColumns = { @JoinColumn(name = "technology_id") })
  private Set<Technology> technologies = new HashSet<>();
  
  public Project() {

  }

  public String getPlatform() {
	return platform;
}

public void setPlatform(String platform) {
	this.platform = platform;
}

public Project(String title, String description, int year, String url, Long rating, String platform) {
    this.title = title;
    this.description = description;
    this.year = year;
    this.url = url;
    this.rating = rating;
    this.platform = platform;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getYear() {
	return year;
  }
	
  public void setYear(int year) {
	this.year = year;
  }
  
  public void setRating(Long rating) {
	this.rating = rating;
  }
	
  public String getUrl() {
	return url;
  }
	
  public void setUrl(String url) {
	this.url = url;
  }
	
  public void setId(long id) {
	this.id = id;
  }

  public Set<Technology> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(Set<Technology> technologies) {
    this.technologies = technologies;
  }
  
  public void addTechnology(Technology technology) {
    this.technologies.add(technology);
    technology.getProjects().add(this);
  }
  
  public void removeTechnology(long technologyId) {
    Technology technology = this.technologies.stream().filter(t -> t.getId() == technologyId).findFirst().orElse(null);
    if (technology != null) {
      this.technologies.remove(technology);
      technology.getProjects().remove(this);
    }
  }

  @Override
public String toString() {
	return "Project [id=" + id + ", title=" + title + ", description=" + description + ", year=" + year + ", url=" + url
			+ ", rating=" + rating + ", platform=" + platform + ", technologies=" + technologies + "]";
}

}
