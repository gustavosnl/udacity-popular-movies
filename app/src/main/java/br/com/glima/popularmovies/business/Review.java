package br.com.glima.popularmovies.business;

import android.os.Parcel;

/**
 * Created by gustavo.lima on 20/01/18.
 */

public class Review {

	private String author;
	private String content;


	protected Review(Parcel in) {
		author = in.readString();
		content = in.readString();
	}

	public String getAuthor() {
		return author;
	}
	public String getContent() {
		return content;
	}

}
