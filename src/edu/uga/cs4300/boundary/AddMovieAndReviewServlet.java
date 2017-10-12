package edu.uga.cs4300.boundary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.logiclayer.MovieLogicImpl;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class FreeMarkerServlet
 * 
 * @author kalkidan
 * class to add movie or review
 * 
 */
@WebServlet({ "/add" })
public class AddMovieAndReviewServlet extends BaseIMDbServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4736490422811236630L;
	
	public AddMovieAndReviewServlet(){
		super();
	}
	
	public void init() {
		super.init();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//if we have reviewMovieId then it means we are adding review
		String id = (String) request.getParameter("reviewMovieId");
		//if we have movie parameter then it means we are adding movie
		String movie = (String) request.getParameter("movie");
		RequestDispatcher rd;
		if(id != null){
			
			String review = (String) request.getParameter("review");
			if(StringUtils.isNotBlank(review)){
				MovieLogicImpl.getInstance().addReview(id, review);
			} 
			
			request.setAttribute("movie", id);
			rd = request.getRequestDispatcher("search");
			rd.forward(request, response);
			return;
			
		} else if("".equals(movie)){
			String title = (String) request.getParameter("title");
			String year = (String) request.getParameter("year");
			String rank = (String) request.getParameter("rank");
			String[] genres = request.getParameterValues("genres");
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
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
			if(rank == null){
				rank = "NULL";
			}
			int movieId = MovieLogicImpl.getInstance().addMovie(title, year, rank, genres);
			request.setAttribute("movie", String.valueOf(movieId));
			rd = request.getRequestDispatcher("search");
			rd.forward(request, response);
			return;
		}
		else {
			rd = request.getRequestDispatcher("index.html");
			rd.forward(request, response);
		}
	}

}
