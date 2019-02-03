package main.services;

import main.domain.Film;
import main.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FilmServiceImpl implements FilmService
{
  private FilmRepository filmRepository;

  @Autowired
  public FilmServiceImpl(FilmRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  @Override public List<Film> listAll()
  {
    return StreamSupport.stream(filmRepository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @Override public Optional<Film> getById(Long id)
  {
    return filmRepository.findById(id);
  }

  @Override public Film saveOrUpdate(Film film)
  {
    filmRepository.save(film);
    return film;
  }

  @Override public void delete(Long id)
  {
    filmRepository.deleteById(id);
  }
}
