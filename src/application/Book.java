package application;

import java.util.ArrayList;
import java.util.Date;

public class Book {

	private String title;
	private String author;
	private String status;
	private Date lentDate;
	private int id;

	// Constructor for adding a new book
	Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.status = "available";
		this.lentDate = null;
	}
	
	Book(int bookId, String title, String author, String status, Date lendDate) {
		this.id = bookId;
		this.title = title;
		this.author = author;
		this.status = "available";
		this.lentDate = lendDate;
	}

	public Date getDate() {
		return lentDate;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	public void rentBook() {
		this.status = "unavailable";
		this.lentDate = new Date();
	}

	public void returnBook() {
		this.status = "available";
		this.lentDate = null;
	}

	public void setId(ArrayList<Book> bookCatalog) {
		int numOfBooks = bookCatalog.size();
		int id = numOfBooks + 1;
		this.id = id;
	}

}