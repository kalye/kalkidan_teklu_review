package edu.uga.cs4300.objectlayer;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3866922167795803369L;
	private int id;
	private String name;
	private int year;
	private String rank;
	private List<Review> reviews;
	private Set<Genre> genres;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	//method to populate fields from ResultSet after fetching from DB
	public void setFields(ResultSet rs) throws SQLException{
		this.id = rs.getInt(1);
		this.name = rs.getString(2);
		this.year = rs.getInt(3);
		this.rank = rs.getString(4);
	}
}
