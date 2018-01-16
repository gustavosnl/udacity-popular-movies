package br.com.glima.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static br.com.glima.popularmovies.database.FavoriteMoviesContentProviderContract.AUTHORITY;
import static br.com.glima.popularmovies.database.FavoriteMoviesContentProviderContract.CONTENT_URI;
import static br.com.glima.popularmovies.database.FavoriteMoviesContentProviderContract.PATH_MOVIES;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.COLUMN_MOVIE_ID;
import static br.com.glima.popularmovies.database.FavoriteMoviesContract.TABLE_NAME;

/**
 * Created by gustavo.lima on 15/01/18.
 */

public class FavoriteMoviesContentProvider extends ContentProvider {

	public static final int MOVIES = 100;
	public static final int BY_ID = 101;

	public static final UriMatcher sUriMatcher = buildUriMatcher();

	private FavoriteMoviesDbHelper dbHelper;


	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(AUTHORITY, PATH_MOVIES, MOVIES);
		matcher.addURI(AUTHORITY, PATH_MOVIES.concat("/#"), BY_ID);

		return matcher;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new FavoriteMoviesDbHelper(getContext());
		return true;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

		switch (sUriMatcher.match(uri)) {
			case MOVIES:

				SQLiteDatabase database = dbHelper.getWritableDatabase();
				long id = database.insert(TABLE_NAME, null, contentValues);
				database.close();
				if (id != -1) {
					Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
					getContext().getContentResolver().notifyChange(uri, null);

					return returnUri;
				} else {
					throw new android.database.SQLException("Failed to insert row into " + uri);
				}

			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		Cursor resultCursor;
		switch (sUriMatcher.match(uri)) {
			case MOVIES:
				resultCursor = database.query(TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder);
				break;

			case BY_ID:
				String id = uri.getPathSegments().get(1);

				String mSelection = COLUMN_MOVIE_ID.concat("=?");
				String[] mSelectionArgs = new String[]{id};
				resultCursor = database.query(TABLE_NAME,
						projection,
						mSelection,
						mSelectionArgs,
						null,
						null,
						sortOrder);
				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		resultCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return resultCursor;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
		return 0;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] whereArgs) {
		switch (sUriMatcher.match(uri)) {
			case BY_ID:
				SQLiteDatabase database = dbHelper.getWritableDatabase();

				String id = uri.getPathSegments().get(1);
				int deletedRows = database.delete(TABLE_NAME, COLUMN_MOVIE_ID.concat("=?"), new String[]{id});
				getContext().getContentResolver().notifyChange(uri, null);
				return deletedRows;

			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}
}
