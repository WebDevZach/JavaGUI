package application;

import java.util.ArrayList;
import java.util.Date;

public class Book {

	private String title;
	private String author;
	private String status;
	private Date lentDate;
	private int id;

	//Constructor for a book object
	Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.status = "available";
		this.lentDate = null;
	}
	
	//Constructor for a book object
	Book(int bookId, String title, String author, String status) {
		this.id = bookId;
		this.title = title;
		this.author = author;
		this.status = status;
	}

	//Getter for date
	public Date getDate() {
		return lentDate;
	}
	
	//Sets a book's date to null
	public void setDateNull()
	{
		this.lentDate = null;
	}

	//Setter for date
	public void setDate(Date lentDate)
	{
		this.lentDate = lentDate;
	}
	
	//Getter for title
	public String getTitle() {
		return title;
	}

	//Getter for author
	public String getAuthor() {
		return author;
	}

	//Getter for status
	public String getStatus() {
		return status;
	}

	//Getter for id
	public int getId() {
		return id;
	}

	//Rents a book
	public void rentBook() {
		this.status = "unavailable";//sets book to unavailable
		this.lentDate = new Date();//sets lent date to the current time
	}

	//Returns a book
	public void returnBook() {
		this.status = "available";//sets book to available
		this.lentDate = null;//sets lent date to null
	}

	//Sets a books id
	public void setId(ArrayList<Book> bookCatalog) {
		int numOfBooks = bookCatalog.size();
		int id = numOfBooks + 1;
		this.id = id;
	}

}