package edu.uga.cs4300.boundary;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.MovieLogicImpl;
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
@WebServlet({ "/search" })
public class IMDbServlet extends BaseIMDbServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460643982644017735L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IMDbServlet() {
		super();
	}
	public void init() {
		super.init();
	}
	//method to handle post request
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//method to handle get request
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//method that determine which page we are requesting based on parameter
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//if we have titles parameters exists then it means get movies' title by genres
		String titles = (String) request.getParameter("titles");
		if(titles != null){
			String[] generes = request.getParameterValues("genres");
			renderTitles(generes, request, response);
			return;
		}
		String movies = (String) request.getParameter("movies");
		if(StringUtils.isNotBlank(movies)){
			String[] generes = request.getParameterValues("generes");
			renderTitles(generes, request, response);
			return;
		} else if("".equals(movies)){
			renderAllMovies(request, response);
		}
		//if addMovie parameter exists then render addMovie.ftl template
		String addMovie = (String) request.getParameter("addMovie");
		if(StringUtils.isNotBlank(addMovie) || "".equals(addMovie)){
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			renderTemplate(request, response, "addmovie.ftl", root);
			return;
		}
		//if movie parameter or attribute exist then render movie detail page 
		String movie = (String) request.getParameter("movie");
		if(StringUtils.isBlank(movie)){
			movie = (String) request.getAttribute("movie");
		}
		if(StringUtils.isNotBlank(movie)){
			//if a movie parameter exist then render it using this parameter which holds movie id
			renderMovieDetail(movie, request, response);
			return;
		}
	}
	//render all movies
	private void renderAllMovies(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		root.put("movies", true);
		root.put("results", MovieLogicImpl.getInstance().getAllMovies());
		renderTemplate(request, response, "allmovies.ftl", root);
	}
	//fetch movie by movie id and render it using moviedetail.ftl template
	private void renderMovieDetail(String id, HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		root.put("movies", true);
		//get movie by movie id and put it in SimpleHash table to render it using Freemarker engine 
		Movie movie = MovieLogicImpl.getInstance().getMovie(id);
		root.put("movie", movie);
		//get the movie reviews to be rendered 
		root.put("reviews", movie.getReviews());
		renderTemplate(request, response, "moviedetail.ftl", root);
	}
	//render movietitles page using genres
	private void renderTitles(String[] genres, HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		//if there is no selected genres then just render the template without result
		if(genres == null || genres.length == 0){
			renderTemplate(request, response, "movietitles.ftl", root);
			return;
		}
		
		root.put("movies", true);
		//fetch list of movies with by genres and render it 
		root.put("results", MovieLogicImpl.getInstance().getMovieWithGeneres(genres));
		request.getSession(true).setAttribute("generes", genres);
		renderTemplate(request, response, "movietitles.ftl", root);
	}


}
