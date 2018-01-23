package br.com.glima.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by gustavo.lima on 11/01/18.
 */

public interface FavoriteMoviesContract extends BaseColumns {

	String TABLE_NAME = "favorite_movies";
	String COLUMN_MOVIE_ID = "movie_id";
	String COLUMN_MOVIE_TITLE = "movie_name";
	String COLUMN_MOVIE_SYNOPSIS = "movie_synopsis";
	String COLUMN_MOVIE_RATING = "movie_rating";
	String COLUMN_MOVIE_RELEASE = "movie_release";
	String COLUMN_MOVIE_LENGTH = "movie_length";
	String COLUMN_MOVIE_POSTER = "movie_poster";
}
