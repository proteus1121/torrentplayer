package main.services;

import main.domain.Film;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface FilmService
{
  List<Film> listAll();

  Optional<Film> getById(Long id);

  Film saveOrUpdate(Film product);

  void delete(Long id);
}
