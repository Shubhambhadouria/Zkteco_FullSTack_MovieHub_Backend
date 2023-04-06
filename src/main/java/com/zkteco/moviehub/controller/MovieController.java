package com.zkteco.moviehub.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zkteco.moviehub.dto.MovieDTO;
import com.zkteco.moviehub.dto.Result;
import com.zkteco.moviehub.entity.Movie;
import com.zkteco.moviehub.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@Autowired
	private ModelMapper mapper;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

	@PostMapping("/add")
	public Result addMovieHandler(@Valid @RequestBody MovieDTO movie) {
		LOGGER.info("Inside Add Movie Method");
		Movie mov = this.mapper.map(movie, Movie.class);
		return movieService.addMovie(mov);
	}

	@GetMapping("/get")
	public Result getMovieHandler(@RequestParam String id) {
		LOGGER.info("Inside Get Movie By Id Method");
		return movieService.getMovieById(id);
	}
	
	@GetMapping("/getallmovies")
	public Result getMovieHandler() {
		LOGGER.info("Inside Get All Movies Method");
		return movieService.getAllMovies();
	}
	
	@PutMapping("/{id}")
	public Result updateMovieByIdHandler(@RequestBody MovieDTO movie,@PathVariable("id") String id) {
		LOGGER.info("Inside Update Movie Data Method");
		Movie mov = this.mapper.map(movie, Movie.class);
		return movieService.updateMovieById(mov, id);
	}
	
	@DeleteMapping("/{id}")
	public Result deleteMovieByIdHandler(@PathVariable("id") String id) {
		LOGGER.info("Inside Delete Movie By Id Method");
		return movieService.deleteMovieById(id);
	}
}
