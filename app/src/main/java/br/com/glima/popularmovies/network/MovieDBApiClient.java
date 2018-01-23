package br.com.glima.popularmovies.network;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.glima.popularmovies.BuildConfig;
import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.business.MovieDetail;
import br.com.glima.popularmovies.business.MovieListResponse;
import br.com.glima.popularmovies.business.ReviewsResponse;
import br.com.glima.popularmovies.business.VideosResponse;
import br.com.glima.popularmovies.database.FavoriteMoviesController;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class MovieDBApiClient {

	private MovieDBApiService apiService;
	private FavoriteMoviesController controller;
	private String API_KEY;

	public MovieDBApiClient(Context context) {

		controller = new FavoriteMoviesController(context);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(context.getString(R.string.movie_api_url))
				.addConverterFactory(setUpGsonConverterFactory())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(setupHttpClient())
				.build();

		apiService = retrofit.create(MovieDBApiService.class);
		API_KEY = context.getString(R.string.api_key);

	}

	public Observable<List<Movie>> fetchPopularMovies() {
		return apiService.fetchPopularMovies(API_KEY)
				.subscribeOn(Schedulers.io())
				.map(new Function<MovieListResponse, List<Movie>>() {
					@Override
					public List<Movie> apply(MovieListResponse movieResponse) throws Exception {
						return movieResponse.getResults();
					}
				});
	}

	public Observable<List<Movie>> fetchTopRatedMovies() {
		return apiService.fetchTopRatedMovies(API_KEY)
				.subscribeOn(Schedulers.io())
				.map(new Function<MovieListResponse, List<Movie>>() {
					@Override
					public List<Movie> apply(MovieListResponse movieResponse) throws Exception {
						return movieResponse.getResults();
					}
				});
	}

	public Observable<List<Movie>> fetchFavoriteMovies() {
		return Observable.just(controller.listAll());
	}

	public Observable<MovieDetail> fetchMovieDetails(String movieId) {
		return Observable.zip(fetchMovie(movieId), fetchMovieReviews(movieId), fetchMovieTrailers(movieId),
				new Function3<Movie, ReviewsResponse, VideosResponse, MovieDetail>() {
					@Override
					public MovieDetail apply(Movie movie, ReviewsResponse reviewsResponse, VideosResponse videosResponse) throws Exception {
						return new MovieDetail(movie, reviewsResponse.getResults(), videosResponse.getVideos());
					}
				}).subscribeOn(Schedulers.io());
	}

	private Observable<VideosResponse> fetchMovieTrailers(String movieId) {
		return apiService.fetchMovieTrailers(movieId, API_KEY);
	}

	private Observable<ReviewsResponse> fetchMovieReviews(String movieId) {
		return apiService.fetchMovieReviews(movieId, API_KEY);
	}

	private Observable<Movie> fetchMovie(String movieId) {
		return apiService.fetchMovieDetails(movieId, API_KEY);
	}

	private OkHttpClient setupHttpClient() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(BuildConfig.DEBUG ? BODY : NONE);

		return new OkHttpClient.Builder()
				.addInterceptor(logging)
				.build();
	}

	private GsonConverterFactory setUpGsonConverterFactory() {
		GsonBuilder gson = new GsonBuilder();
		gson.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

		return GsonConverterFactory.create(gson.create());
	}

}
