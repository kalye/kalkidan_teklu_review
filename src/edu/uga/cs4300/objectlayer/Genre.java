package edu.uga.cs4300.objectlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kalkidan
 * Class for movie genre
 *
 */
public class Genre {

	//static variable to hold all genres
	public static final Set<Genre> genres;
	
	static {
		genres = new HashSet<Genre>();
		genres.add(new Genre("action", "1"));
		genres.add(new Genre("adventure", "2"));
		genres.add(new Genre("animation", "3"));
		genres.add(new Genre("biography", "4"));
		genres.add(new Genre("comedy", "5"));
		genres.add(new Genre("crime", "6"));
		genres.add(new Genre("documentary", "7"));
		genres.add(new Genre("drama", "8"));
		genres.add(new Genre("family", "9"));
		genres.add(new Genre("fantasy", "10"));
		genres.add(new Genre("film_noir", "11"));
		genres.add(new Genre("game_show", "12"));
		genres.add(new Genre("history", "13"));
		genres.add(new Genre("horror", "14"));
		genres.add(new Genre("music", "15"));
		genres.add(new Genre("musical", "16"));
		genres.add(new Genre("mystery", "17"));
		genres.add(new Genre("news", "18"));
		genres.add(new Genre("reality_tv", "19"));
		genres.add(new Genre("romance", "20"));
		genres.add(new Genre("sci_fi", "21"));
		genres.add(new Genre("sport", "22"));
		genres.add(new Genre("talk_show", "23"));
		genres.add(new Genre("thriller", "24"));
		genres.add(new Genre("war", "25"));
		genres.add(new Genre("western", "26"));
		
	}
	private String genre;
	private boolean checked;
	private String id;
	
	public Genre(){
		
	}

	public Genre(Genre genre) {
		this.checked = genre.checked;
		this.genre = genre.genre;
		this.id = genre.id;
	}
	public Genre(String genre, String id) {
		this.genre = genre;
		this.id = id;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	//method to populate fields from ResultSet after fetching from DB
	public void setFields(ResultSet rs) throws SQLException{
		this.genre = rs.getString(1);
		this.id = rs.getString(2);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String movie_id) {
		this.id = movie_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.toLowerCase().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equalsIgnoreCase(other.genre))
			return false;
		return true;
	}
	
	
}
