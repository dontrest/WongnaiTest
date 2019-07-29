package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import com.wongnai.interview.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		//reverseMap initialize
		Map<String,List<Long>> reverseMap = new HashMap<String , List<Long>>();
		// convert all movieData to entity Movie add to list
		for (Long index = 0L ; index< moviesResponse.size() ; index+=1) {
			MovieData movieData = moviesResponse.get(index.intValue());
			Movie movie = new Movie(movieData.getTitle());
			for(String movieWord : movieData.getTitle().split(" ")){
				// make word to first column
				// if word not exist in Map create it
				movieWord = movieWord.toLowerCase();
				if(!reverseMap.containsKey(movieWord)){
					reverseMap.put(movieWord , new ArrayList<Long>());
				}
				// add movie id to it
				reverseMap.get(movieWord).add(index);
			}
			movie.setId(index);
			movie.setActors(movieData.getCast());
			movies.add(movie);
		}
		//save into memory
		MovieUtils.setReverseMap(reverseMap);
		//save list to movieRepository
		movieRepository.saveAll(movies);
	}
}
