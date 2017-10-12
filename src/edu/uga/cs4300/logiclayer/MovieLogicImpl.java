package edu.uga.cs4300.logiclayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uga.cs4300.objectlayer.Genre;
import edu.uga.cs4300.objectlayer.Movie;
import edu.uga.cs4300.persistlayer.MoviePersistImpl;

/**
 * @author kalkidan
 *
 */
public class MovieLogicImpl {

	private MoviePersistImpl moviePersistImpl = MoviePersistImpl.getInstance();
	
	private static MovieLogicImpl movieLogicImpl;
	
	private MovieLogicImpl(){
		
	}
	public Movie getMovie(String id){
		return moviePersistImpl.getMovie(id);
	}
	public List<Movie> getAllMovies(){
		return moviePersistImpl.getAllMovies();
	}
	public List<Movie> getMovieWithGeneres(String[] generes){
		if(generes == null || generes.length == 0){
			return new ArrayList<Movie>();
		}
		return moviePersistImpl.getMovieWithGeneres(generes);
	}
	
	public int deleteMovieById(String id){
		return moviePersistImpl.deleteMovieById(id);
	}
	public int deleteReviewById(String id){
		return moviePersistImpl.deleteReviewById(id);
	}
	public void addReview(String movieId, String review){
		moviePersistImpl.addReview(movieId, review);
	}
	
	public void updateReview(String reviewId, String review){
		moviePersistImpl.updateReview(reviewId, review);
	}
	public int addMovie(String title, String year, String rank, String[] genres){
		return moviePersistImpl.addMovie(title, year, rank, genres);
	}
	//method to return single instance of this class
	public static MovieLogicImpl getInstance(){
		if(movieLogicImpl != null){
			return movieLogicImpl;
		}
		synchronized (MovieLogicImpl.class) {
			if(movieLogicImpl == null){
				synchronized (MovieLogicImpl.class) {
					movieLogicImpl = new MovieLogicImpl();
				}
			}
		}
		return movieLogicImpl;
	}
	//method to update movies. This method receive currently selected genres, fetch already existing one and create
	//list of unselected genres for deletion 
	public void updateMovie(String id, String title, String year, String rank, String[] genres) {
		//get existing genres
		Set<Genre> oldGenres = moviePersistImpl.getMovieGeneres(id);
		if(oldGenres == null){
			oldGenres = new HashSet<>();
		}
		//set to hold newly selected reviews
		Set<Genre> newGenres = new HashSet<>();
		//set to hold unselected reviews
		Set<Genre> genresToRemove = new HashSet<>();
		//if there was no genres previously selected then add the new ones to the new genres set
		if(oldGenres.isEmpty()){
			if(genres != null && genres.length > 0){
				for(String genre: genres){
					newGenres.add(new Genre(genre, id));
				}
			}
		} else {
			//at this point we already have previously selected genres
			//if currently there is no selected genres then put previously selected genres to remove genres set
			if(genres == null || genres.length == 0){
				genresToRemove.addAll(oldGenres);
			} else {
				//first loop check if the newly selected genre is in previous selection
				//if the new selected genre is not in the previously selected set then add it to the newly selected genres to be added to 
				// the table
				for(String genre: genres){
					boolean found = false;
					for(Genre oldGenre: oldGenres){
						if(genre.equalsIgnoreCase(oldGenre.getGenre())){
							found = true;
						}
					}
					if(!found){
						newGenres.add(new Genre(genre, id));
					}
				}
				//loop through the previously selected genres and check if it is also in the newly selected set
				//if it is not in the newly selected set than it means it's unselected and need to be removed
				for(Genre oldGenre: oldGenres){
					boolean found = false;
					for(String genre: genres){
						if(genre.equalsIgnoreCase(oldGenre.getGenre())){
							found = true;
						}
					}
					if(!found){
						genresToRemove.add(oldGenre);
					}
				}
			}
		}
		moviePersistImpl.updateMovie(id, title, year, rank, newGenres, genresToRemove);
	}
	
	public Set<Genre> getMovieGeneres(String id) throws SQLException {
		return moviePersistImpl.getMovieGeneres(id);
	}
		
}
