package com.zkteco.moviehub.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkteco.moviehub.dto.MovieDTO;
import com.zkteco.moviehub.dto.Result;
import com.zkteco.moviehub.entity.Movie;
import com.zkteco.moviehub.repository.MovieRepository;
import com.zkteco.moviehub.service.MovieService;

import jakarta.validation.Valid;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private ModelMapper mapper;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	Date date = new Date();

	private Result validator(@Valid Movie movie) {

		String title = movie.getTitle();
		String releaseDate = movie.getReleaseDate();
		String runtime = movie.getRunTime();
		String genre = movie.getGenre();
		String rating = movie.getRating();
		String synopsis = movie.getSynopsis();
		String director = movie.getDirector();
		String movieCast = movie.getMovieCast();

		// Validation for title
		if (Objects.nonNull(title) && !title.equals("")) {
			if ((title.length() < 36 && title.length() > 0)) {
				if (movieRepo.findByTitle(title) != null) {
					return new Result("ERR101", "Movie title should be unique", null);
				}
			} else {
				return new Result("ERR102", "Movie title must not be greater than 36 Characters", null);
			}
		} else {
			return new Result("ERR103", "Movie title can not be null", null);
		}

		// Validation for Release Date
		if(Objects.nonNull(releaseDate) && !releaseDate.equals("")){
			if(!releaseDate.matches("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {
				return new Result("ERR","Date Should be in Format yyyy-MM-DD",null);
			}
		}else {
			return new Result("ERR","Date Should be Null",null);
		}
		
		// Validation for genre
		if (Objects.nonNull(genre) && !genre.equals("")) {
			if (!(genre.length() < 36 && genre.length() > 0)) {
				return new Result("ERR104", "Genre must not be greater than 36 Characters", null);
			}
		}

		// Validation for runtime
		if (Objects.nonNull(runtime)) {
			if (!(runtime.length() < 4 && runtime.length() > 0)) {
				return new Result("ERR104", "Runtime must not be greater than 999 Minutes", null);
			}
		}

		// Validation for Rating
		if (Objects.nonNull(rating) && !rating.equals("")) {
			if (!(rating.length() < 36 && rating.length() > 0)) {
				return new Result("ERR105", "Rating must not be greater than 36 Characters", null);
			}
		}

		// Validation for Synopsis
		if (Objects.nonNull(synopsis) && !synopsis.equals("")) {
			if (!(synopsis.length() < 200 && synopsis.length() > 0)) {
				return new Result("ERR106", "Synopsis must not be greater than 200 Characters", null);
			}
		}

		// Validation for Director
		if (Objects.nonNull(director) && !director.equals("")) {
			if (!(director.length() < 36 && director.length() > 0)) {
				return new Result("ERR107", "Director must not be greater than 36 Characters", null);
			}
		} else {
			return new Result("ERR108", "Director can not be null", null);
		}

		// Validation for MovieCast
		if (Objects.nonNull(movieCast) && !movieCast.equals("")) {
			if (!(movieCast.length() < 36 && movieCast.length() > 0)) {
				return new Result("ERR109", "MovieCast must not be greater than 36 Characters", null);
			}
		} else {
			return new Result("ERR110", "MovieCast can not be null", null);
		}

		return null;
	}

	@SuppressWarnings("unused")
	private Result updateValidator(@Valid Movie movie) {

		String title = movie.getTitle();
		String genre = movie.getGenre();
		String rating = movie.getRating();
		String synopsis = movie.getSynopsis();
		String director = movie.getDirector();
		String movieCast = movie.getMovieCast();

		// Validation for title
		if (Objects.nonNull(title) && !title.equals("")) {
			if ((title.length() < 36 && title.length() > 0)) {
				if (movieRepo.findByTitle(title) != null) {
					return new Result("ERR101", "Movie title should be unique", null);
				}
			} else {
				return new Result("ERR102", "Movie title must not be greater than 36 Characters", null);
			}
		}

		// Validation for genre
		if (Objects.nonNull(genre) && !genre.equals("")) {
			if (!(genre.length() < 36 && genre.length() > 0)) {
				return new Result("ERR104", "Genre must not be greater than 36 Characters", null);
			}
		}

		// Validation for Rating
		if (Objects.nonNull(rating) && !rating.equals("")) {
			if (!(rating.length() < 36 && rating.length() > 0)) {
				return new Result("ERR105", "Rating must not be greater than 36 Characters", null);
			}
		}

		// Validation for Synopsis
		if (Objects.nonNull(synopsis) && !synopsis.equals("")) {
			if (!(synopsis.length() < 200 && synopsis.length() > 0)) {
				return new Result("ERR106", "Synopsis must not be greater than 200 Characters", null);
			}
		}

		// Validation for Director
		if (Objects.nonNull(director) && !director.equals("")) {
			if (!(director.length() < 36 && director.length() > 0)) {
				return new Result("ERR107", "Director must not be greater than 36 Characters", null);
			}
		}

		// Validation for MovieCast
		if (Objects.nonNull(movieCast) && !movieCast.equals("")) {
			if (!(movieCast.length() < 36 && movieCast.length() > 0)) {
				return new Result("ERR109", "MovieCast must not be greater than 36 Characters", null);
			}
		}

		return null;
	}

	@Override
	public Result addMovie(@Valid Movie movie) {
		Result result = validator(movie);
		if (result == null) {
			movie.setCreatedAt(dateFormat.format(date));

			SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try {
				Date oldDate = oldDateFormat.parse(movie.getReleaseDate());
				String newDateString = newDateFormat.format(oldDate);
				movie.setReleaseDate(newDateString);
			} catch (Exception e) {
				e.printStackTrace();
			}

			movieRepo.save(movie);
			MovieDTO mov = this.mapper.map(movie, MovieDTO.class);
			return new Result("OK", "Movie data is Added successfully", mov);
		} else {
			return result;
		}
	}

	@Override
	public Result getMovieById(String id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			MovieDTO mov = this.mapper.map(movie, MovieDTO.class);
			return new Result("OK", "Movie data is Fetched Successfully with Id : " + id, mov);
		} else {
			return new Result("ERR", "Movie data not found with Id : " + id, null);
		}

	}

	@Override
	public Result getAllMovies() {
		List<Movie> movies = movieRepo.findAll();

		if (movies.isEmpty()) {
			return new Result("ERR", "No Movie Found in the database", null);
		} else {
			List<MovieDTO> movDto = new ArrayList<>();
			for (Movie mov : movies) {
				movDto.add(convertToDto(mov));
			}
			return new Result("OK", "Total Movies Found are : " + movies.size(), movDto);
		}
	}

	private MovieDTO convertToDto(Movie movie) {
		return mapper.map(movie, MovieDTO.class);
	}

	@Override
	public Result updateMovieById(Movie movie, String id) {
		Optional<Movie> opt = movieRepo.findById(id);
		if (opt.isPresent()) {
			Result result = updateValidator(movie);
			if (result == null) {
				movie.setUpdatedAt(dateFormat.format(date));

				if (Objects.nonNull(movie.getTitle()) && !"".equalsIgnoreCase(movie.getTitle())) {
					opt.get().setTitle(movie.getTitle());
				}
				if (Objects.nonNull(movie.getReleaseDate())) {
					opt.get().setReleaseDate(movie.getReleaseDate());
				}
				if (Objects.nonNull(movie.getGenre()) && !"".equalsIgnoreCase(movie.getGenre())) {
					opt.get().setGenre(movie.getGenre());
				}
				if (Objects.nonNull(movie.getRunTime())) {
					opt.get().setRunTime(movie.getRunTime());
				}
				if (Objects.nonNull(movie.getRating()) && !"".equalsIgnoreCase(movie.getRating())) {
					opt.get().setRating(movie.getRating());
				}
				if (Objects.nonNull(movie.getSynopsis()) && !"".equalsIgnoreCase(movie.getSynopsis())) {
					opt.get().setSynopsis(movie.getSynopsis());
				}
				if (Objects.nonNull(movie.getDirector())) {
					opt.get().setDirector(movie.getDirector());
				}
				if (Objects.nonNull(movie.getMovieCast())) {
					opt.get().setMovieCast(movie.getMovieCast());
				}
				if (Objects.nonNull(movie.getPhoto())) {
					opt.get().setPhoto(movie.getPhoto());
				}
				if (Objects.nonNull(movie.getCreatedAt())) {
					opt.get().setCreatedAt(movie.getCreatedAt());
				}
				if (Objects.nonNull(movie.getUpdatedAt())) {
					opt.get().setUpdatedAt(movie.getUpdatedAt());
				}

				movieRepo.save(opt.get());
				MovieDTO mov = this.mapper.map(opt.get(), MovieDTO.class);
				return new Result("OK", "Movie data is Updated successfully", mov);
			} else {
				return result;
			}
		} else {
			return new Result("ERR", "Movie data not found with Id : " + id, null);
		}
	}

	@Override
	public Result deleteMovieById(String id) {
		Optional<Movie> opt = movieRepo.findById(id);
		if (opt.isPresent()) {
			MovieDTO mov = this.mapper.map(opt.get(), MovieDTO.class);
			movieRepo.delete(opt.get());
			return new Result("OK", "Movie data deleted Successfully with Id : " + id, mov);
		} else {
			return new Result("ERR", "Movie data not found with Id : " + id, null);
		}
	}

}
