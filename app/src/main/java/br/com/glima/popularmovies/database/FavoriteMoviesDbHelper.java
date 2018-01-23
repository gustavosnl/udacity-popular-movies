package br.com.glima.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_ID;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_LENGTH;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_POSTER;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_RATING;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_RELEASE;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_SYNOPSIS;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_TITLE;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.TABLE_NAME;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract._ID;

/**
 * Created by gustavo.lima on 11/01/18.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "favorite_movies.db";
	private static final int DATABASE_VERSION = 1;

	public FavoriteMoviesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_FAVORITE_MOVIES_TABLE = " CREATE TABLE " +
				TABLE_NAME + " (" +
				_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
				COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
				COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
				COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
				COLUMN_MOVIE_LENGTH + " TEXT NOT NULL, " +
				COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
				COLUMN_MOVIE_RELEASE + " TEXT NOT NULL " + ");";

		sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(sqLiteDatabase);
	}
}
