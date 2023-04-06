package com.zkteco.moviehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zkteco.moviehub.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{

	public Movie findByTitle(String title);
	
}
