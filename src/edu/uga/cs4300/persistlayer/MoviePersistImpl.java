package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uga.cs4300.objectlayer.Genre;
import edu.uga.cs4300.objectlayer.Movie;
import edu.uga.cs4300.objectlayer.Review;

/**
 * @author Kalkidan
 * Class to create query and update/delete/create rows into tables
 * this class facilitate interaction between logic and persistent layer or entry point to persistent layer
 *
 */
public class MoviePersistImpl {

	public static final String SELECT_MOVIE_WITH_GENERE = "Select * from movies where id in (select movie_id from movies_genres where genre in (#params) ) ;";
	public static final String SELECT_MOVIE = "select * from movies ";
	public static final String SELECT_MOVIE_REVIEW_WITH_MOVIE_ID = "select * from reviews ";
	public static final String INSERT_MOVIE_REVIEW_WITH_MOVIE_ID = "insert into reviews (id, movie_id, review) values  ";
	public static final String INSERT_MOVIE = "insert into movies (id, name, year, rank) values  ";
	public static final String UPDATE_REVIEW = "update reviews  ";
	public static final String UPDATE_MOVIE = "update movies  ";
	DbAccessImpl dbAccessImpl = DbAccessImpl.getInstance();
	private static MoviePersistImpl moviePersistImpl;

	private MoviePersistImpl() {

	}

	//get movies that has any of the genres
	public List<Movie> getMovieWithGeneres(String[] genres) {

		String query = SELECT_MOVIE_WITH_GENERE;
		query = query.replace("#params", singleQuoteAndComma(genres));
		Connection connection = dbAccessImpl.connect();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		List<Movie> movies = getMoviesWithoutReview(resultSet);
		dbAccessImpl.disconnect(connection);
		return movies;

	}
	//get all movies 
	public List<Movie> getAllMovies(){
		String query = SELECT_MOVIE;
		Connection connection = dbAccessImpl.connect();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		//convert resultSet to List of Movies
		List<Movie> movies = getMoviesWithoutReview(resultSet);
		dbAccessImpl.disconnect(connection);
		return movies;
	}
	//get movie by movie id
	public Movie getMovie(String id){
		String query = SELECT_MOVIE + "where id = " + id + ";";
		Connection connection = dbAccessImpl.connect();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		Movie movie = null;
		try {
			//if resultSet has some result convert it to Movie object
			if(resultSet.next()){
				movie = getMovieEntity(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//select reviews for the movie and update the movie object
		query = SELECT_MOVIE_REVIEW_WITH_MOVIE_ID + "where movie_id = " + id + ";";
		resultSet = dbAccessImpl.retrieve(connection, query);
		//convert resultSet to list of Reviews and set it to movie review list
		movie.setReviews(getReviews(resultSet));
		dbAccessImpl.disconnect(connection);
		return movie;
	}

	//convert resultSet to list of movies
	private List<Movie> getMoviesWithoutReview(ResultSet resultSet) {
		List<Movie> movies = new ArrayList<>();
		if (resultSet != null) {
			try {
				//loop through resultSet and get movie entity and add it to the list
				while (resultSet.next()) {
					movies.add(getMovieEntity(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return movies;
	}
	//get review list from resultSet
	private List<Review> getReviews(ResultSet resultSet) {
		List<Review> reviews = new ArrayList<>();
		if (resultSet != null) {
			try {
				//loop through resultSet and get Movie entity and add it to the list
				while (resultSet.next()) {
					reviews.add(getReviewEntity(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reviews;
	}

	//convert resultSet to movie object/entity
	private Movie getMovieEntity(ResultSet resultSet) {
		Movie movie = new Movie();
		try {
			//make movie to update itself from resultSet
			movie.setFields(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}

	//convert resultSet to Review object/entity
	private Review getReviewEntity(ResultSet resultSet) {
		Review review = new Review();
		try {
			//make review to update itself from resultSet
			review.setFields(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return review;
	}
	//method to convert array parameters to comma separated string for SQL query
	private String singleQuoteAndComma(String[] strArray) {
		String in = "";
		if (strArray == null || strArray.length == 0) {
			return in;
		}
		for (int i = 0; i < strArray.length; i++) {
			in += "'" + strArray[i] + "'";
			if (i != strArray.length - 1) {
				in += ",";
			}
		}
		return in;
	}

	//method to return single instance of this object
	public static MoviePersistImpl getInstance() {
		if (moviePersistImpl != null) {
			return moviePersistImpl;
		}
		synchronized (MoviePersistImpl.class) {
			if (moviePersistImpl == null) {
				synchronized (MoviePersistImpl.class) {
					moviePersistImpl = new MoviePersistImpl();
				}
			}
		}
		return moviePersistImpl;
	}

	//delete movie by id
	public int deleteMovieById(String id) {
		Connection connection = dbAccessImpl.connect();
		String query = "delete from movies where id ='" + id + "';";
		int result = dbAccessImpl.delete(connection, query);
		dbAccessImpl.disconnect(connection);
		return result;
	}

	//Delete review by id
	public int deleteReviewById(String id) {
		Connection connection = dbAccessImpl.connect();
		String query = "delete from reviews where id ='" + id + "';";
		int result = dbAccessImpl.delete(connection, query);
		dbAccessImpl.disconnect(connection);
		return result;
	}
	
	//add review for move with movie_id
	public void addReview(String movieId, String review) {
		Connection connection = dbAccessImpl.connect();
		String query = INSERT_MOVIE_REVIEW_WITH_MOVIE_ID + "('" + getLastId("reviews") +  "','" + movieId 
				+ "','" + review.replace("'", "\\'") + "')" ;
		dbAccessImpl.create(connection, query);
		dbAccessImpl.disconnect(connection);
	}

	//add movie and its genres to movies and genres tables respectively
	public int addMovie(String name, String year, String rank, String[] genres) {
		Connection connection = dbAccessImpl.connect();
		//get the last row id of the  movies table
		int id = getLastId("movies");
		String query = INSERT_MOVIE + "('" + id +  "','" + name.replace("'", "\\'") 
				+ "','" + year + "'";
		//change rank from '' to "NULL" so that we inserted NULL to the column instead of ''
		if("".equals(rank)){
			rank = "NULL";
		}
		
		//if the string is NULL don't add single quoted value ('NULL') in the values instead just add NULL
		if (rank.equals("NULL")) {
			query = query + "," + rank + "";
		} else {
			query = query + ",'" + rank + "'";
		}
			
		query = query + ");";
		int success = dbAccessImpl.create(connection, query);
		//if we successfully inserted the new movie then loop through genres and insert it into movies_genres table
		if(success > 0){
			if(genres != null && genres.length > 0){
				for (String genre : genres) {
					query = "insert into movies_genres (movie_id, genre) values(" + id + ",'" + genre + "');";
					dbAccessImpl.create(connection, query);
				}
			}
		}
		dbAccessImpl.disconnect(connection);
		//if movie creation is successful return the id
		return success > 0 ? id : 0;
	}
	//get the last row id of a table in order to insert new row. Since our table DDL doesn't use auto increment we need this query
	private int getLastId(String tableName) {
		Connection connection = dbAccessImpl.connect();
		//select the top id of the table row
		String query = "select id from " + tableName + " order by id desc limit 1;";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		int id = 1;
		try {
			//if we have some result return the result after incrementing it by one
			if(resultSet.next()){
				id = resultSet.getInt(1);
				id++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	//method to update review
	public void updateReview(String reviewId, String review) {
		Connection connection = dbAccessImpl.connect();
		String query = UPDATE_REVIEW + "set review = '" + review + "' where id = " + reviewId + ";";
		dbAccessImpl.update(connection, query);
		dbAccessImpl.disconnect(connection);
	}

	//get movies genres by movie id
	public Set<Genre> getMovieGeneres(String id) {
		Set<Genre> genres = new HashSet<>();
		Connection connection = dbAccessImpl.connect();
		String query =  "select genre, movie_id from movies_genres where movie_id = " + id + ";";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		genres = getGeneres(resultSet);
		return genres;
	}

	//convert resultSet to Genres set
	private Set<Genre> getGeneres(ResultSet resultSet) {
		Set<Genre> genres = new HashSet<>();
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					genres.add(getGenreEntity(resultSet));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return genres;
	}

	//convert resultSet to Genre object
	private Genre getGenreEntity(ResultSet resultSet) throws SQLException {
		Genre genre = new Genre();
		genre.setFields(resultSet);
		return genre;
	}

	//update movie, insert new genres and removed genres that are unselected
	public void updateMovie(String id, String title, String year, String rank, Set<Genre> newGenres,
			Set<Genre> genresToRemove) {
		Connection connection = dbAccessImpl.connect();
		String query = UPDATE_MOVIE + "set name = '" + title.replace("'", "\\'")  + "', year = " + Integer.parseInt(year);
		if(!"".equals(rank) && rank != null){
			if(!rank.equals("NULL")){
				query  = query +  ", rank = '" + rank + "'";
			}
			
		}
		query = query + " where id = " + id + ";";
		dbAccessImpl.update(connection, query);
		//loop through and insert new genres
		if(newGenres != null && !newGenres.isEmpty()){
			for(Genre genre: newGenres){
				query = "insert into movies_genres (movie_id, genre)  values(" + id + ", '" + genre.getGenre() + "');";
				dbAccessImpl.create(connection, query);
			}
		}
		//loop through and delete unselected genres
		if(genresToRemove != null && !genresToRemove.isEmpty()){
			for(Genre genre: genresToRemove){
				query = "delete from movies_genres where genre = '" + genre.getGenre() + "' and movie_id = " + id + ";";
				dbAccessImpl.delete(connection, query);
			}
		}
		dbAccessImpl.disconnect(connection);
		
	}

}
