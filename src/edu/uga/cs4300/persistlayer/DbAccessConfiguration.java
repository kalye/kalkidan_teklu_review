package edu.uga.cs4300.persistlayer;

/**
 * @author kalkidan
 * Class for holding DB configuration
 *
 */
public abstract class DbAccessConfiguration {

	static final String DRIVE_NAME = "com.mysql.jdbc.Driver";

	static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/imdb2";

	static final String DB_CONNECTION_USERNAME = "demo";

	static final String DB_CONNECTION_PASSWORD = "demo";
}
