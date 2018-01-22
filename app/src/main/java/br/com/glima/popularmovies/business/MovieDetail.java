package br.com.glima.popularmovies.business;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.glima.popularmovies.R;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class MovieDetail {

	private Movie movie;
	private List<Review> reviews;
	private List<Video> videos;

	public MovieDetail(Movie movie, List<Review> reviews, List<Video> videos) {
		this.movie = movie;
		this.reviews = reviews;
		this.videos = videos;
	}

	public Movie getMovie() {
		return movie;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public String getImageUrl() {
		return movie.getPosterPath();
	}

	@BindingAdapter({"bind:imageUrl"})
	public static void loadImage(ImageView view, String imageUrl) {
		Context context = view.getContext();

		Picasso.with(context)
				.load(context.getString(R.string.image_api_url, imageUrl))
				.placeholder(R.drawable.placeholder)
				.into(view);
	}
}
