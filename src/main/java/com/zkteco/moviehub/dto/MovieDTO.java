package com.zkteco.moviehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

	private String id;
	private String title;
	private String releaseDate;
	private String genre;
	private String runTime;
	private String rating;
	private String synopsis;
	private String director;
	private String movieCast;
	private String photo;
	private String createdAt;
	private String updatedAt;
}
