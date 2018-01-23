package br.com.glima.popularmovies.business;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class Video implements Parcelable {

	private String key;
	private String name;

	protected Video(Parcel in) {
		key = in.readString();
		name = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(key);
		dest.writeString(name);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	public static final Creator<Video> CREATOR = new Creator<Video>() {
		@Override
		public Video createFromParcel(Parcel in) {
			return new Video(in);
		}

		@Override
		public Video[] newArray(int size) {
			return new Video[size];
		}
	};
	public String getKey() {
		return key;
	}
	public String getName() {
		return name;
	}

}
