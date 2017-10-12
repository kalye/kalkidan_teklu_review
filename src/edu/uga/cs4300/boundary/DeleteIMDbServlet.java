package edu.uga.cs4300.boundary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.cs4300.logiclayer.MovieLogicImpl;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

/**
 * Servlet implementation class FreeMarkerServlet
 * 
 * @author kalkidan
 * 
 */
@WebServlet({ "/delete" })
public class DeleteIMDbServlet extends BaseIMDbServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2784573336036317514L;
	
	public DeleteIMDbServlet(){
		super();
	}
	
	public void init() {
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = (String) request.getParameter("id");
		String reviewId = (String) request.getParameter("reviewId");
		RequestDispatcher rd;
		//if the parameter has id then it means delete movie by id
		//else if it has review id then delete review 
		if(id != null){
			//delete movie by movie id
			MovieLogicImpl.getInstance().deleteMovieById(id);
		} else if(reviewId != null){
			//delete review by review id
			MovieLogicImpl.getInstance().deleteReviewById(reviewId);
			//get movie id that we use to redirect to movie detail page
			id = (String) request.getParameter("movieId");
		}
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		//if there is genres the display all movies that has similar genres
		String[] generes = (String[]) request.getSession().getAttribute("generes");
		if (generes != null) {
			root.put("movies", true);
			root.put("results", MovieLogicImpl.getInstance().getMovieWithGeneres(generes));
			renderTemplate(request, response, "movietitles.ftl", root);
			return;
		}
		//redirect to home page
		rd = request.getRequestDispatcher("index.html");
		rd.forward(request, response);
		
	}
}
