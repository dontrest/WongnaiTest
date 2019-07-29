package com.wongnai.interview.movie.external;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wongnai.interview.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class MovieDataServiceImpl implements MovieDataService {
	public static final String MOVIE_DATA_URL
			= "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public MoviesResponse fetchAll() {
		//TODO:
		// Step 1 => Implement this method to download data from MOVIE_DATA_URL and fix any error you may found.
		// Please noted that you must only read data remotely and only from given source,
		// do not download and use local file or put the file anywhere else.
		try{
			//read json string from MOVIE_DATA_URL
			String json = MovieUtils.readJsonFromUrl(MOVIE_DATA_URL);
			//use jackson to parse json to MoviesResponse
			MoviesResponse response = objectMapper.readValue(json, MoviesResponse.class);
			return response;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
