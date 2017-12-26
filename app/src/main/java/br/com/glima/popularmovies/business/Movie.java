package br.com.glima.popularmovies.business;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class Movie implements Parcelable {

	private String title;
	private String poster;
	private String synopsis;
	private String rating;
	private String releaseDate;

	public Movie(String title, String poster, String synopsis, String rating, String releaseDate) {
		this.title = title;
		this.poster = poster;
		this.synopsis = synopsis;
		this.rating = rating;
		this.releaseDate = releaseDate;
	}

	protected Movie(Parcel in) {
		title = in.readString();
		poster = in.readString();
		synopsis = in.readString();
		rating = in.readString();
		releaseDate = in.readString();
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

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(title);
		parcel.writeString(poster);
		parcel.writeString(synopsis);
		parcel.writeString(rating);
		parcel.writeString(releaseDate);
	}
}
