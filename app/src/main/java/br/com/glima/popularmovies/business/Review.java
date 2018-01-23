package br.com.glima.popularmovies.business;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class Review implements Parcelable {

	private String author;
	private String content;


	protected Review(Parcel in) {
		author = in.readString();
		content = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(author);
		dest.writeString(content);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	public static final Creator<Review> CREATOR = new Creator<Review>() {
		@Override
		public Review createFromParcel(Parcel in) {
			return new Review(in);
		}

		@Override
		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
	public String getAuthor() {
		return author;
	}
	public String getContent() {
		return content;
	}

}
