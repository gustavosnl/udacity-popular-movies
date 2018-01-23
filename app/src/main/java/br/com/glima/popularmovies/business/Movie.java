package br.com.glima.popularmovies.business;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class Movie implements Parcelable {

	private String id;
	private String title;
	private String posterPath;
	private String runtime;
	private String overview;
	private String voteAverage;
	private String releaseDate;

	public Movie(String id, String title, String posterPath, String runtime, String overview, String voteAverage, String releaseDate) {
		this.id = id;
		this.title = title;
		this.posterPath = posterPath;
		this.runtime = runtime;
		this.overview = overview;
		this.voteAverage = voteAverage;
		this.releaseDate = releaseDate;
	}

	protected Movie(Parcel in) {
		id = in.readString();
		title = in.readString();
		posterPath = in.readString();
		runtime = in.readString();
		overview = in.readString();
		voteAverage = in.readString();
		releaseDate = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(posterPath);
		dest.writeString(runtime);
		dest.writeString(overview);
		dest.writeString(voteAverage);
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

	public String getPosterPath() {
		return posterPath;
	}

	public String getOverview() {
		return overview;
	}

	public String getVoteAverage() {
		return voteAverage;
	}

	public String getReleaseDate() {
		SimpleDateFormat RAW_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM/dd/yyyy", Locale.US);

		try {
			Date rawDate = RAW_DATE_FORMAT.parse(releaseDate);
			return DATE_FORMAT.format(rawDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return releaseDate;
		}
	}

	public String getRuntime() {
		SimpleDateFormat MINUTES_DATE_FORMAT = new SimpleDateFormat("mm", Locale.US);
		SimpleDateFormat HOUR_MINUTES_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.US);

		try {
			Date minutes = MINUTES_DATE_FORMAT.parse(runtime);
			return HOUR_MINUTES_DATE_FORMAT.format(minutes);
		} catch (ParseException e) {
			e.printStackTrace();
			return runtime;
		}
	}
}
