package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import com.wongnai.interview.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

import com.wongnai.interview.utils.MovieUtils.*;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		MoviesResponse moviesResponse =  movieDataService.fetchAll();
		List<Movie> movies = new ArrayList<>();
		//if movie list is not empty
		if(moviesResponse != null && moviesResponse.size()!=0){
			// fetch all movie and add Movie that title contain queryText to MovieList
			for (MovieData movieData : moviesResponse) {
				if (MovieUtils.SearchWithWholeWord(movieData.getTitle(),queryText)) {
					Movie movie = new Movie(movieData.getTitle());
					movie.setActors(movieData.getCast());
					movies.add(movie);
				}
			}
		}
		return movies;
	}
}
