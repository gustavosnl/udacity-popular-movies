package br.com.glima.popularmovies.business;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class Movie implements Parcelable {

	private String id;
	private String title;
	private String poster;
	private String synopsis;
	private String rating;
	private String releaseDate;

	public Movie(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public Movie(String id, String title, String poster, String synopsis, String rating, String releaseDate) {
		this.id = id;
		this.title = title;
		this.poster = poster;
		this.synopsis = synopsis;
		this.rating = rating;
		this.releaseDate = releaseDate;
	}

	protected Movie(Parcel in) {
		id = in.readString();
		title = in.readString();
		poster = in.readString();
		synopsis = in.readString();
		rating = in.readString();
		releaseDate = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(poster);
		dest.writeString(synopsis);
		dest.writeString(rating);
		dest.writeString(releaseDate);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPoster() {
		return poster;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getRating() {
		return rating;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

}
