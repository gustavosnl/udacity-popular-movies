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

	@GET("movie/popular")
	Observable<MovieListResponse> fetchPopularMovies(@Query("api_key") String apiKey);

	@GET("movie/top_rated")
	Observable<MovieListResponse> fetchTopRatedMovies(@Query("api_key") String apiKey);

	@GET("movie/{movieId}")
	Observable<Movie> fetchMovieDetails(@Path("movieId") String movieId, @Query("api_key") String apiKey);

	@GET("movie/{movieId}/videos")
	Observable<VideosResponse> fetchMovieTrailers(@Path("movieId") String movieId, @Query("api_key") String apiKey);

	@GET("movie/{movieId}/reviews")
	Observable<ReviewsResponse> fetchMovieReviews(@Path("movieId") String movieId, @Query("api_key") String apiKey);
}
