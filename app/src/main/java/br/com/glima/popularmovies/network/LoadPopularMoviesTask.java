package br.com.glima.popularmovies.network;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class LoadPopularMoviesTask extends MoviesTask {

	private static final String POPULAR_MOVIES_PATH = "movie/popular";

	public LoadPopularMoviesTask(MoviesCallBack callBack) {
		super(callBack, POPULAR_MOVIES_PATH);
	}
}
