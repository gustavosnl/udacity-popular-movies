package br.com.glima.popularmovies.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.network.MovieDBApiClient;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class MainActivity extends AppCompatActivity implements Observer<List<Movie>>, MovieClickListener {

	private RecyclerView mList;
	private ProgressBar mProgress;
	private MoviesAdapter mMoviesAdapter = new MoviesAdapter(this, this);
	private MovieDBApiClient apiClient;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		apiClient = new MovieDBApiClient(this);

		mProgress = findViewById(R.id.progress_bar);
		mList = findViewById(R.id.movies_list);
		mList.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.column_span_count)));
		mList.setAdapter(mMoviesAdapter);

		doRequest();
	}

	private void doPopularMoviesRequest() {
		apiClient.fetchPopularMovies()
				.observeOn(mainThread())
				.subscribe(this);
	}

	private void doTopRatedMoviesRequest() {
		apiClient.fetchTopRatedMovies()
				.observeOn(mainThread())
				.subscribe(this);
	}

	private void doFavoriteMoviesRequest() {
		apiClient.fetchFavoriteMovies()
				.observeOn(mainThread())
				.subscribe(this);
	}

	private void doRequest() {
		doPopularMoviesRequest();
	}

	@Override
	public void onMovieClicked(Movie movie) {
		startActivity(MovieDetailActivity.newIntent(this, movie.getId()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.movies_sort_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.sort_popular:
				doPopularMoviesRequest();
				break;
			case R.id.sort_top_rated:
				doTopRatedMoviesRequest();
				break;
			case R.id.sort_favorites:
				doFavoriteMoviesRequest();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSubscribe(Disposable d) {
		mMoviesAdapter.clear();
		mProgress.setVisibility(View.VISIBLE);
		mList.setVisibility(View.GONE);
	}

	@Override
	public void onNext(List<Movie> movies) {
		mMoviesAdapter.addItems(movies);
	}

	@Override
	public void onError(Throwable e) {
		Toast.makeText(this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onComplete() {
		mProgress.setVisibility(View.GONE);
		mList.setVisibility(View.VISIBLE);
	}
}
