package br.com.glima.popularmovies.network;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.glima.popularmovies.BuildConfig;
import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.business.MovieResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
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
	private String API_KEY;

	public MovieDBApiClient(Context context) {

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
				.map(new Function<MovieResponse, List<Movie>>() {
					@Override
					public List<Movie> apply(MovieResponse movieResponse) throws Exception {
						return movieResponse.getResults();
					}
				});
	}
	public Observable<List<Movie>> fetchTopRatedMovies() {
		return null;
	}
	public Observable<Movie> fetchMovieDetails(String movieId) {
		return null;
	}
	public Observable<Movie> fetchMovieTrailers(String movieId) {
		return null;
	}
	public Observable<Movie> fetchMovieReviews(String movieId) {
		return null;
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
