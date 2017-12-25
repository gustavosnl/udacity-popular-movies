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

import java.util.List;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;
import br.com.glima.popularmovies.network.LoadPopularMoviesTask;
import br.com.glima.popularmovies.network.LoadTopRatedMoviesTask;
import br.com.glima.popularmovies.network.MoviesCallBack;

public class MainActivity extends AppCompatActivity implements MoviesCallBack, MovieClickListener {

	private RecyclerView mList;
	private ProgressBar mProgress;
	private MoviesAdapter mMoviesAdapter = new MoviesAdapter(this);

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mProgress = findViewById(R.id.progress_bar);
		mList = findViewById(R.id.movies_list);
		mList.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.column_span_count)));
		mList.setAdapter(mMoviesAdapter);

		doRequest();
	}

	private void doPopularMoviesRequest() {
		new LoadPopularMoviesTask(this).execute();
	}

	private void doTopRatedMoviesRequest() {
		new LoadTopRatedMoviesTask(this).execute();
	}

	@Override
	public void onFetchMoviesCompleted(List<Movie> movies) {
		mProgress.setVisibility(View.GONE);
		mMoviesAdapter.addItems(movies);
		mList.setVisibility(View.VISIBLE);
	}
	@Override
	public void onFetchMoviesStarted() {
		mMoviesAdapter.clear();
		mProgress.setVisibility(View.VISIBLE);
		mList.setVisibility(View.GONE);
	}

	private void doRequest() {
		doPopularMoviesRequest();
	}

	@Override
	public void onMovieClicked(Movie movie) {
		startActivity(MovieDetailActivity.newIntent(this, movie));
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
		}
		return super.onOptionsItemSelected(item);
	}
}
