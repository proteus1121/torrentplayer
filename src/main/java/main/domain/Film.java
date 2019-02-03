package main.domain;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Data
@Table(name="films")
public class Film
{
  @javax.persistence.Id
  @GeneratedValue
  private Long id;
  private String title;
  private String description;
  private String imageUrl;
  private String torrentUrl;
  private String enSubtitles;
  private String ruSubtitles;
}
