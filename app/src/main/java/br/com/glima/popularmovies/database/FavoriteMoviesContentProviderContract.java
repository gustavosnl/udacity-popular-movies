package br.com.glima.popularmovies.database;

import android.net.Uri;

/**
 * Created by gustavo.lima on 15/01/18.
 */

public interface FavoriteMoviesContentProviderContract {

	String AUTHORITY = "br.com.glima.popularmovies";
	Uri BASE_CONTENT_URI = Uri.parse("content://".concat(AUTHORITY));
	String PATH_MOVIES = "movies";

	Uri CONTENT_URI =
			BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


}
