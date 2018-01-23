package br.com.glima.popularmovies.business;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.glima.popularmovies.R;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class MovieDetail implements Parcelable {

	private Movie movie;
	private List<Review> reviews;
	private List<Video> videos;

	public MovieDetail(Movie movie, List<Review> reviews, List<Video> videos) {
		this.movie = movie;
		this.reviews = reviews;
		this.videos = videos;
	}

	protected MovieDetail(Parcel in) {
		movie = in.readParcelable(Movie.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(movie, flags);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
		@Override
		public MovieDetail createFromParcel(Parcel in) {
			return new MovieDetail(in);
		}

		@Override
		public MovieDetail[] newArray(int size) {
			return new MovieDetail[size];
		}
	};
	public Movie getMovie() {
		return movie;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public List<Video> getVideos() {
		return videos;
	}
	public String getMainTrailerKey() {
		return videos.get(0).getKey();
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
