package br.com.glima.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.database.FavoriteMoviesController;

/**
 * Created by gustavo.lima on 24/12/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

	private static final String INTENT_EXTRA_MOVIE = "intent_extra_movie";

	private TextView mTitle;
	private TextView mSynopsis;
	private TextView mReleaseDate;
	private TextView mRating;
	private ImageView mMoviePoster;

	private FavoriteMoviesController favoriteMoviesController;

	public static Intent newIntent(Context origin, Movie movie) {
		Intent intent = new Intent(origin, MovieDetailActivity.class);
		intent.putExtra(INTENT_EXTRA_MOVIE, movie);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		favoriteMoviesController = new FavoriteMoviesController(this);

		mTitle = findViewById(R.id.movie_title);
		mSynopsis = findViewById(R.id.synopsis);
		mReleaseDate = findViewById(R.id.release_date);
		mRating = findViewById(R.id.rating);
		mMoviePoster = findViewById(R.id.movie_thumbnail);

		if (hasMovieIntentExtra()) {
			init(getMovieFromIntent());
		}
	}
	private boolean hasMovieIntentExtra() {
		return getIntent() != null && getIntent().hasExtra(INTENT_EXTRA_MOVIE);
	}

	private void init(Movie movie) {
		mTitle.setText(movie.getTitle());
		mSynopsis.setText(movie.getOverview());
		mRating.setText(movie.getVoteAverage());
		mReleaseDate.setText(movie.getReleaseDate());

//		Picasso.with(this)
//				.load(buildImageURL(movie.getPosterPath()))
//				.into(mMoviePoster);
	}

	private Movie getMovieFromIntent() {
		return getIntent().getParcelableExtra(INTENT_EXTRA_MOVIE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.movie_detail_menu, menu);
		setUpFavoriteMenuItem(menu.findItem(R.id.favorite));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.favorite:
				UpdateFavoriteMenuItem(item);
				break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpFavoriteMenuItem(MenuItem favoriteItem) {
		if (hasMovieIntentExtra()) {
			if (favoriteMoviesController.isFavorite(getMovieFromIntent())) {
				favoriteItem.setIcon(R.drawable.ic_favorite_filled);
			} else {
				favoriteItem.setIcon(R.drawable.ic_favorite_stroke);
			}
		}
	}

	private void UpdateFavoriteMenuItem(MenuItem favoriteItem) {
		if (hasMovieIntentExtra()) {
			if (favoriteMoviesController.isFavorite(getMovieFromIntent())) {
				favoriteMoviesController.removeMovie(getMovieFromIntent());
				favoriteItem.setIcon(R.drawable.ic_favorite_stroke);
			} else {
				favoriteMoviesController.addMovie(getMovieFromIntent());
				favoriteItem.setIcon(R.drawable.ic_favorite_filled);
			}
		}
	}
}