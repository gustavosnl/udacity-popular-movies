package br.com.glima.popularmovies.network;

/**
 * Created by gustavo.lima on 24/12/17.
 */

public class LoadTopRatedMoviesTask extends MoviesTask {

	private static final String TOP_RATED_MOVIES_PATH = "movie/top_rated";

	public LoadTopRatedMoviesTask(MoviesCallBack callBack) {
		super(callBack, TOP_RATED_MOVIES_PATH);
	}
}
