package application;

import java.util.ArrayList;
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
	
	public String getTitle()
	{
		return title;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public int getId()
	{
		return id;
	}

	public void rentBook()
	{
		this.status = "unavailable";
		this.lentDate = new Date();
	}
	

	public void returnBook()
	{
		this.status = "available";
		this.lentDate = null;
	}
	
	public void setId(ArrayList<Book> bookCatalog)
	{
		int numOfBooks = bookCatalog.size();
		int id = numOfBooks + 1;
		this.id = id;
	}
	
	
}