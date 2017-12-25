package br.com.glima.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;

import static br.com.glima.popularmovies.network.PathBuilder.buildImageURL;

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

	public static Intent newIntent(Context origin, Movie movie) {
		Intent intent = new Intent(origin, MovieDetailActivity.class);
		intent.putExtra(INTENT_EXTRA_MOVIE, movie);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);

		mTitle = findViewById(R.id.movie_title);
		mSynopsis = findViewById(R.id.synopsis);
		mReleaseDate = findViewById(R.id.release_date);
		mRating = findViewById(R.id.rating);
		mMoviePoster = findViewById(R.id.movie_thumbnail);

		Movie movie = (Movie) getIntent().getSerializableExtra(INTENT_EXTRA_MOVIE);
		init(movie);


	}

	private void init(Movie movie) {
		mTitle.setText(movie.getTitle());
		mSynopsis.setText(movie.getSynopsis());
		mRating.setText(movie.getRating());
		mReleaseDate.setText(movie.getReleaseDate());

		Picasso.with(this)
				.load(buildImageURL(movie.getPoster()))
				.into(mMoviePoster);
	}
}
