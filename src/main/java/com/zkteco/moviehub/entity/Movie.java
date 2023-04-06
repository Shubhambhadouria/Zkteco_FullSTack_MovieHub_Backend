package com.zkteco.moviehub.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column( length = 36, unique = true)
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
