package br.com.glima.popularmovies.network;

import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.business.MovieListResponse;
import br.com.glima.popularmovies.business.ReviewsResponse;
import br.com.glima.popularmovies.business.VideosResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public interface MovieDBApiService {
	@GET("movie/{sort_criteria}")
	Observable<MovieListResponse> fetchMovies(@Path("sort_criteria") String criteria, @Query("api_key") String apiKey);

	@GET("movie/{movie_id}")
	Observable<Movie> fetchMovieDetails(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

	@GET("movie/{movie_id}/videos")
	Observable<VideosResponse> fetchMovieTrailers(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

	@GET("movie/{movie_id}/reviews")
	Observable<ReviewsResponse> fetchMovieReviews(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
}
