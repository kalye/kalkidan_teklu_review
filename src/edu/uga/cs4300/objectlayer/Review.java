package edu.uga.cs4300.objectlayer;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7051612421906744640L;
	
	private int id;
	
	private String review;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	//method to populate fields from ResultSet after fetching from DB
	public void setFields(ResultSet rs) throws SQLException{
		this.id = rs.getInt(1);
		this.review = rs.getString(3);
	}

}
