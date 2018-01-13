package br.com.glima.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.business.Movie;

import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_ID;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_TITLE;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.TABLE_NAME;

/**
 * Created by gustavo.lima on 12/01/18.
 */

public class FavoriteMoviesController {

	private FavoriteMoviesDbHelper dbHelper;
	private SQLiteDatabase database;

	public FavoriteMoviesController(Context context) {
		dbHelper = new FavoriteMoviesDbHelper(context);
	}

	public void addMovie(Movie movie) {
		ContentValues values = new ContentValues();

		values.put(COLUMN_MOVIE_ID, movie.getId());
		values.put(COLUMN_MOVIE_TITLE, movie.getTitle());

		database = dbHelper.getWritableDatabase();
		database.insert(TABLE_NAME, null, values);
		database.close();
	}

	public List<Movie> listAll() {
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

		List<Movie> movies = new ArrayList<>();

		if (cursor != null) {
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				movies.add(new Movie(
						cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID)),
						cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_TITLE))));

				cursor.moveToNext();
			}
			cursor.close();
		}
		database.close();

		return movies;
	}

	public void removeMovie(Movie movie) {
		String whereClause = COLUMN_MOVIE_ID + "=" + movie.getId();

		database = dbHelper.getWritableDatabase();
		database.delete(TABLE_NAME, whereClause, null);
		database.close();
	}

	public boolean isFavorite(Movie movie) {

		String whereClause = COLUMN_MOVIE_ID + "=" + movie.getId();

		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, whereClause, null, null, null, null);
		return cursor.moveToNext();
	}
}
