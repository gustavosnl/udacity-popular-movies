package br.com.glima.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
				FavoriteMoviesContract.TABLE_NAME + " (" +
				FavoriteMoviesContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				FavoriteMoviesContract.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
				FavoriteMoviesContract.COLUMN_MOVIE_TITLE + " TEXT NOT NULL " + ");";

		sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.TABLE_NAME);
		onCreate(sqLiteDatabase);
	}
}
