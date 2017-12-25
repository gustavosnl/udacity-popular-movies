package br.com.glima.popularmovies.network;

import java.util.List;

import br.com.glima.popularmovies.business.Movie;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public interface MoviesCallBack {

	void onFetchMoviesCompleted(List<Movie> movies);
	void onFetchMoviesStarted();
}
