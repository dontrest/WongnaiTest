package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		//Fetch All movie from url using service MovieDataService.fectchAll()
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<Movie> movies = new ArrayList<>();
		// convert all movieData to entity Movie add to list
		for (Long index = 0L ; index< moviesResponse.size() ; index+=1) {
			MovieData movieData = moviesResponse.get(index.intValue());
			Movie movie = new Movie(movieData.getTitle());
			movie.setId(index);
			movie.setActors(movieData.getCast());
			movies.add(movie);
		}
		//save list to movieRepository
		movieRepository.saveAll(movies);
	}
}
