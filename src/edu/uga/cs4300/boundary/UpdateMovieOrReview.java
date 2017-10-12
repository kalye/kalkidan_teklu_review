package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.MovieLogicImpl;
import edu.uga.cs4300.objectlayer.Genre;
import edu.uga.cs4300.objectlayer.Movie;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class FreeMarkerServlet
 * 
 * @author kalkidan
 * 
 */
@WebServlet({ "/update" })
public class UpdateMovieOrReview extends BaseIMDbServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136416906522631109L;
	
	public UpdateMovieOrReview(){
		super();
	}

	public void init() {
		super.init();
	}
	//get method that render the update movie page
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String movieId = (String) request.getParameter("id");
		if(StringUtils.isNotBlank(movieId)){
			try {
				renderMovieUpdatePage(movieId, request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	//post method for updating movie or review
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//request handler method
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//render movie update page first by fetching the movie from DB and list of previously selected genres
	private void renderMovieUpdatePage(String id, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		root.put("movies", true);
		//fetch movie from DB
		Movie movie = MovieLogicImpl.getInstance().getMovie(id);
		//put the data to simple hash object
		root.put("id", movie.getId());
		root.put("title", movie.getName());
		root.put("year", movie.getYear());
		root.put("rank", (movie.getRank() == null || "".equals(movie.getRank().trim())) ? "" : movie.getRank());
		//get movie genres by movie id
		Set<Genre> genres = MovieLogicImpl.getInstance().getMovieGeneres(id);
		//clone set of all genres and update those which was previously selected
		Set<Genre> allGenreSets = Genre.genres.stream().map(genre -> new Genre(genre)).collect(Collectors.toSet());
		List<Genre> allGenres = new ArrayList<>();
		allGenres.addAll(allGenreSets);
		//loop through previously selected genres and update all list of genres to show which one is selected
		if(genres != null && !genres.isEmpty()){
			for(Genre genre: allGenres){
				if(genres.contains(genre)){
					genre.setChecked(true);
				}
			}
		}
		root.put("allGenres", allGenres);
		renderTemplate(request, response, "updatemovie.ftl", root);
	}
	//method to process the request
	//this method is used to tell whether we are updating movie or review and take appropriate action
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get review id and movie id
		String reviewId = (String) request.getParameter("reviewId");
		String movieId = (String) request.getParameter("movieId");
		RequestDispatcher rd;
		//if review id is not null then it means we are updating review
		if(reviewId != null){
			//get actual review text
			String review = (String) request.getParameter("review");
			//if review text is not empty then update the table
			if(StringUtils.isNotBlank(review)){
				MovieLogicImpl.getInstance().updateReview(reviewId, review);
			} 
			//redirect the page to movie detail page
			request.setAttribute("movie", movieId);
			rd = request.getRequestDispatcher("search");
			rd.forward(request, response);
			return;
			
		} 
		//get movie id which comes as movie parameter
		String id = (String) request.getParameter("movie");
		if(id != null){
			//get movie fields
			String title = (String) request.getParameter("title");
			String year = (String) request.getParameter("year");
			String rank = (String) request.getParameter("rank");
			String[] genres = request.getParameterValues("genres");
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			//validate required fields like title, year and at least one genre
			//if this required field missing then return the user to same page with error
			SimpleHash root = new SimpleHash(df.build());
			if("".equals(title) || title == null){
				root.put("error", true);
				root.put("message", "Title is required.");
				renderTemplate(request, response, "addmovie.ftl", root);
				return;
			} else if("".equals(year) || year == null){
				root.put("error", true);
				root.put("message", "Year is required.");
				renderTemplate(request, response, "addmovie.ftl", root);
				return;
			} else if(genres == null || genres.length == 0){
				root.put("error", true);
				root.put("message", "genres is required.");
				renderTemplate(request, response, "addmovie.ftl", root);
				return;
			}
			//set rank to null so that it will be inserted as NULL than actual string value 'NULL'
			if(rank == null || "".equals(rank)){
				rank = "NULL";
			}
			//update movie
			MovieLogicImpl.getInstance().updateMovie(id, title, year, rank, genres);
			request.setAttribute("movie", String.valueOf(id));
			//redirect the page to movie detail page
			rd = request.getRequestDispatcher("search");
			rd.forward(request, response);
			return;
		} else {
			rd = request.getRequestDispatcher("index.html");
			rd.forward(request, response);
		}
		
	}
}
