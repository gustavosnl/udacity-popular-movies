package br.com.glima.popularmovies.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.business.Movie;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class MoviesDeserializer {

	public static List<Movie> deserialize(InputStream inputStream) throws JSONException, IOException {

		BufferedInputStream stream = new BufferedInputStream(inputStream);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder output = new StringBuilder(stream.available());
		String line;

		while ((line = reader.readLine()) != null) {
			output.append(line).append('\n');
		}

		JSONObject json = new JSONObject(output.toString());
		JSONArray jsonArray = json.getJSONArray("results");

		List<Movie> movies = new ArrayList<>();
		for (int i = 0; i < json.getJSONArray("results").length(); i++) {
			JSONObject jsonMovie = jsonArray.getJSONObject(i);

			movies.add(new Movie(
					jsonMovie.optString("id"),
					jsonMovie.optString("title"),
					jsonMovie.optString("poster_path"),
					jsonMovie.optString("overview"),
					jsonMovie.optString("vote_average"),
					jsonMovie.optString("release_date")));
		}
		return movies;
	}
}
