package br.com.glima.popularmovies.network;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.business.Movie;

/**
 * Created by gustavo.lima on 24/12/17.
 */

public abstract class MoviesTask extends AsyncTask<String, Void, List<Movie>> {

	private final String requestPath;

	private HttpURLConnection mUrlConnection;
	private final MoviesCallBack mCallBack;

	MoviesTask(MoviesCallBack mCallBack, String requestPath) {
		this.mCallBack = mCallBack;
		this.requestPath = requestPath;
	}

	@Override
	protected void onPreExecute() {
		mCallBack.onFetchMoviesStarted();
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
	private URL buildUrl(String... params) throws MalformedURLException {
		return PathBuilder.buildRequestURL(requestPath);
	}
	@Override
	protected void onPostExecute(List<Movie> movies) {
		mCallBack.onFetchMoviesCompleted(movies);
	}

}
