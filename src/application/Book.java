package application;

import java.util.Date;

public class Book {

	 String title;
	 String author;
	 String status;
	 Date lentDate;
	 int id;
	
	
	//Constructor for adding a new book
	Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.status = "available";
	}
	

	
	
	
	
}