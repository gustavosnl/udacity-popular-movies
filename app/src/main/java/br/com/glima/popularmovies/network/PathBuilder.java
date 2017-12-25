package br.com.glima.popularmovies.network;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.glima.popularmovies.BuildConfig;

/**
 * Created by gustavo.lima on 25/12/17.
 */

public class PathBuilder {
	private static final String API_KEY = "?api_key=".concat(BuildConfig.API_KEY);

	public static String buildImageURL(String imagePath) {
		return BuildConfig.API_IMG_URL
				.concat(imagePath)
				.concat(API_KEY);
	}

	public static URL buildRequestURL(String requestPath) throws MalformedURLException {
		return new URL(BuildConfig.API_URL
				.concat(requestPath)
				.concat(API_KEY));
	}
}