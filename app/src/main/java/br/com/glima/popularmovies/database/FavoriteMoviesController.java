package br.com.glima.popularmovies.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.business.Movie;

import static br.com.glima.popularmovies.database.FavoriteMoviesContentProviderContract.CONTENT_URI;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_ID;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_LENGTH;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_POSTER;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_RATING;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_RELEASE;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_SYNOPSIS;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_TITLE;

/**
 * Created by gustavo.lima on 12/01/18.
 */

public class FavoriteMoviesController {

	private Context context;

	public FavoriteMoviesController(Context context) {
		this.context = context;
	}

	public void addMovie(Movie movie) {
		ContentValues values = new ContentValues();

		values.put(COLUMN_MOVIE_ID, movie.getId());
		values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
		values.put(COLUMN_MOVIE_SYNOPSIS, movie.getOverview());
		values.put(COLUMN_MOVIE_RATING, movie.getVoteAverage());
		values.put(COLUMN_MOVIE_RELEASE, movie.getReleaseDate());
		values.put(COLUMN_MOVIE_LENGTH, movie.getRuntime());
		values.put(COLUMN_MOVIE_POSTER, movie.getPosterPath());

		context.getContentResolver().insert(CONTENT_URI, values);
	}

	@SuppressLint("StaticFieldLeak")
	public List<Movie> listAll() {

		List<Movie> movies = new ArrayList<>();

		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				movies.add(buildMovieFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return movies;
	}

	@NonNull
	private Movie buildMovieFromCursor(Cursor cursor) {
		return new Movie(
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_TITLE)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_POSTER)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_LENGTH)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_SYNOPSIS)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_RATING)),
				cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_RELEASE))
		);
	}

	public void removeMovie(String movieId) {
		Uri DELETE_URI = CONTENT_URI.buildUpon().appendPath(movieId).build();

		context.getContentResolver().delete(DELETE_URI, null, null);
	}

	public boolean isFavorite(String movieId) {
		Uri IS_FAVORITE_URI = CONTENT_URI.buildUpon().appendPath(movieId).build();

		Cursor cursor = context.getContentResolver().query(IS_FAVORITE_URI, null, null, null, null);
		return cursor.moveToNext();
	}
}
