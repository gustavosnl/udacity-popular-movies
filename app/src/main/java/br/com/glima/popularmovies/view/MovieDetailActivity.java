package br.com.glima.popularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.MovieDetail;
import br.com.glima.popularmovies.database.FavoriteMoviesController;
import br.com.glima.popularmovies.databinding.ActivityMovieDetailBinding;
import br.com.glima.popularmovies.network.MovieDBApiClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by gustavo.lima on 24/12/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements Observer<MovieDetail>, OnVideoClickListener {

	private static final String INTENT_EXTRA_MOVIE_ID = "intent_extra_movie_id";

	private FavoriteMoviesController favoriteMoviesController;
	private MovieDBApiClient movieDBApiService;
	private ActivityMovieDetailBinding binding;

	public static Intent newIntent(Context origin, String movieId) {
		Intent intent = new Intent(origin, MovieDetailActivity.class);
		intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

		movieDBApiService = new MovieDBApiClient(this);
		favoriteMoviesController = new FavoriteMoviesController(this);

		binding.reviewsList.setLayoutManager(new LinearLayoutManager(this));
		binding.reviewsList.addItemDecoration(new DividerItemDecoration(this));

		binding.videosList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		binding.videosList.addItemDecoration(new DividerItemDecoration(this));

		movieDBApiService.fetchMovieDetails(getMovieFromIntent()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
	}

	private boolean hasMovieIntentExtra() {
		return getIntent() != null && getIntent().hasExtra(INTENT_EXTRA_MOVIE_ID);
	}

	private String getMovieFromIntent() {
		return getIntent().getStringExtra(INTENT_EXTRA_MOVIE_ID);
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
//				favoriteMoviesController.addMovie(getMovieFromIntent());
				favoriteItem.setIcon(R.drawable.ic_favorite_filled);
			}
		}
	}

	@Override
	public void onSubscribe(Disposable d) {

	}
	@Override
	public void onNext(MovieDetail movieDetail) {

		binding.setMovie(movieDetail);

		binding.reviewsList.setAdapter(new ReviewAdapter(movieDetail.getReviews()));
		binding.videosList.setAdapter(new VideosAdapter(movieDetail.getVideos(), this));
	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onComplete() {

	}
	@Override
	public void onVideoClicked(String key) {

		Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube, key)));
		if (youtubeIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(youtubeIntent);
		}

	}
}