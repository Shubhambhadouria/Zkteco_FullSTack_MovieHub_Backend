package com.zkteco.moviehub.service;

import com.zkteco.moviehub.dto.Result;
import com.zkteco.moviehub.entity.Movie;

public interface MovieService {

	Result addMovie(Movie movie);

	Result getMovieById(String id);

	Result getAllMovies();

	Result updateMovieById(Movie movie, String id);

	Result deleteMovieById(String id);

}
