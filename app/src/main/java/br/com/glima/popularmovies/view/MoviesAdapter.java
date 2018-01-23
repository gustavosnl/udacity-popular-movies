package br.com.glima.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.R;
import br.com.glima.popularmovies.business.Movie;

/**
 * Created by gustavo.lima on 22/12/17.
 */

class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder> {

	private List<Movie> movies = new ArrayList<>();
	private Context context;
	private MovieClickListener clickListener;

	public MoviesAdapter(Context context, MovieClickListener listener) {
		this.context = context;
		clickListener = listener;
	}

	@Override
	public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_movie, parent, false);
		return new MovieItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(MovieItemViewHolder holder, int position) {
		holder.attachMovie(movies.get(position));
	}

	@Override
	public int getItemCount() {
		return movies.size();
	}

	public void addItems(List<Movie> movies) {
		this.movies.addAll(movies);
		notifyDataSetChanged();
	}
	public void clear() {
		this.movies.clear();
		notifyDataSetChanged();
	}

	class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private ImageView moviePoster;
		private Movie movie;

		public MovieItemViewHolder(View view) {
			super(view);
			moviePoster = itemView.findViewById(R.id.movie_thumbnail);
			itemView.setOnClickListener(this);
		}

		public void attachMovie(Movie movie) {
			this.movie = movie;

			Picasso.with(itemView.getContext()).setLoggingEnabled(true);

			Picasso.with(itemView.getContext())
					.load(buildPosterUrl(movie))
					.placeholder(R.drawable.placeholder)
					.into(moviePoster);
		}

		private String buildPosterUrl(Movie movie) {
			return context.getString(R.string.image_api_url, movie.getPosterPath());

		}

		@Override
		public void onClick(View v) {
			clickListener.onMovieClicked(movie);
		}
	}
}