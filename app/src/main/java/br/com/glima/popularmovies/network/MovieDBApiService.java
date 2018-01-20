package br.com.glima.popularmovies.network;

import java.util.List;

import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.business.MovieResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public interface MovieDBApiService {

	@GET("movie/popular")
	Observable<MovieResponse> fetchPopularMovies(@Query("api_key") String apiKey);

	@GET("movie/top_rated")
	Observable<List<Movie>> fetchTopRatedMovies(@Query("api_key") String apiKey);

	@GET("movie/{movieId}?api_key={api_key}")
	Observable<Movie> fetchMovieDetails(@Path("movieId") String movieId, @Query("api_key") String apiKey);

	@GET("/movie/{id}/videos?api_key={api_key}")
	Observable<Movie> fetchMovieTrailers(@Path("movieId") String movieId, @Query("api_key") String apiKey);

	@GET("/movie/{id}/reviews?api_key={api_key}")
	Observable<Movie> fetchMovieReviews(@Path("movieId") String movieId, @Query("api_key") String apiKey);

}
