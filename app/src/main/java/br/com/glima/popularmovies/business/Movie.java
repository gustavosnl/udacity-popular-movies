package br.com.glima.popularmovies.business;

/**
 * Created by gustavo.lima on 18/12/17.
 */

public class Movie {

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
