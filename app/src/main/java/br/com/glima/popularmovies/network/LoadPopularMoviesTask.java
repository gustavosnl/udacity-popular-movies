package br.com.glima.popularmovies.network;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.BuildConfig;
import br.com.glima.popularmovies.business.Movie;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class LoadPopularMoviesTask extends AsyncTask<String, Void, List<Movie>> {

	private final String API_KEY = "?api_key=".concat(BuildConfig.API_KEY);
	private final String PAGE_QUERY = "&page=";
	private final String POPULAR_MOVIES_PATH = "discover/movie";
	private final String SORT_DESC_QUERY = "&sort_by=popularity.desc";


	private HttpURLConnection mUrlConnection;
	private final MoviesCallBack mCallBack;

	public LoadPopularMoviesTask(MoviesCallBack callBack) {
		mCallBack = callBack;
	}

	@Override
	protected void onPreExecute() {
		mCallBack.showProgress();
	}

	@Override
	protected List<Movie> doInBackground(String... params) {

		try {
			mUrlConnection = (HttpURLConnection) buildUrl(params).openConnection();
			mUrlConnection.setRequestMethod("GET");
			mUrlConnection.connect();

			return MoviesDeserializer.deserialize(mUrlConnection.getInputStream());

		} catch (IOException | JSONException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	protected void onPostExecute(List<Movie> movies) {
		mCallBack.onFetchMoviesCompleted(movies);
	}

	URL buildUrl(String... params) throws MalformedURLException {
		return new URL(BuildConfig.API_URL
				.concat(POPULAR_MOVIES_PATH)
				.concat(API_KEY)
				.concat(PAGE_QUERY.concat(params[0]))
				.concat(SORT_DESC_QUERY));
	}
}
