package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;


public class Main extends Application {

	private TextField bookTitleTf;
	private TextField authorTf;
	private Scene mainMenuScene;
	private Stage primaryStage;
	private TableView<Book> availableBooksTableView;
	private TableView<Book> unavailableBooksTableView;
	private TableView<Book> foundTableView;
	private Scene rentBookScene;
	private Scene returnBookScene;
	private Scene searchScene;
	private TextField searchBarTf;

	@Override
	public void start(Stage primaryStage) {

		//Assigns primary stage to a variable 
		this.primaryStage = primaryStage;

		//Book catalog 
		ArrayList<Book> bookCatalog = new ArrayList<>();

		//Name for file
		String fileName = "BookCatalog.txt";

		//Reads BookCatalog.txt file and stores values into books objects that are then placed in the book catalog array list
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				int bookId = Integer.parseInt(scanner.nextLine());//Parses the string into an integer value
				String bookTitle = scanner.nextLine();
				String bookAuthor = scanner.nextLine();
				String bookStatus = scanner.nextLine();
				String date = scanner.nextLine();

				//Makes a new book object 
				Book newBook = new Book(bookId, bookTitle, bookAuthor, bookStatus);

				if (date.equals("null")) {
					newBook.setDateNull();//sets the book's date to null if the string "null" is read in 
				} else {
					String pattern = "EEE MMM dd HH:mm:ss zzz yyyy"; //defines the pattern for the date formatter 
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);// Date formatter to parse an string into a date object
					Date lendDate = sdf.parse(date); //converts string into a date object
					newBook.setDate(lendDate);// sets a book's date
				}
				bookCatalog.add(newBook);// adds book to the catalog
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file does not exist");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		/* MENU SECTION */

		VBox menuLayout = new VBox();
		menuLayout.setSpacing(35);
		BorderPane menuBorderPane = new BorderPane();
		menuBorderPane.setTop(menuLayout);
		menuLayout.setPadding(new Insets(30, 0, 0, 150));
		mainMenuScene = new Scene(menuBorderPane, 400, 300);

		
		
		/* ADD BOOK SECTION */
		
		
		HBox addBookHBox = new HBox();
		addBookHBox.setSpacing(10);
		addBookHBox.setPadding(new Insets(0, 0, 20, 200));

		BorderPane addBookBorderPane = new BorderPane();
		addBookBorderPane.setBottom(addBookHBox);
		Scene addNewBookScene = new Scene(addBookBorderPane, 400, 300);
		
		//Menu button to switch to add new book scene
		Button addButton = new Button(" Add a new book ");
		menuLayout.getChildren().add(addButton);
		addButton.setOnAction(e -> primaryStage.setScene(addNewBookScene));//Sets scene to the add new book scene

		//Switch back to menu button
		Button switchToMenuFromAddBookButton = new Button("Menu");
		switchToMenuFromAddBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));//Sets scene back to the main menu
		addBookHBox.getChildren().add(switchToMenuFromAddBookButton);
		
		//Button to add a new book to the catalog
		Button addNewBookButton = new Button("Add Book");
		addNewBookButton.setOnAction(e -> addNewBook(bookCatalog, fileName));
		addBookHBox.getChildren().add(addNewBookButton);

		//Grid layout for text fields
		GridPane addBookGridPane = new GridPane();
		addBookGridPane.setHgap(10);
		addBookGridPane.setVgap(10);

		//Labels and text fields
		Label bookTitleLabel = new Label("Title");
		Label authorLabel = new Label("Author");

		bookTitleTf = new TextField();
		authorTf = new TextField();

		//Adds labels and fields to grid
		addBookGridPane.add(bookTitleLabel, 0, 0);
		addBookGridPane.add(authorLabel, 0, 1);
		addBookGridPane.add(bookTitleTf, 1, 0);
		addBookGridPane.add(authorTf, 1, 1);

		//Adds grid to layout
		addBookBorderPane.setCenter(addBookGridPane);
		addBookGridPane.setPadding(new Insets(100, 0, 0, 90));

		
		
		/* RENT BOOK SECTION */
		
	
		HBox rentBookHBox = new HBox();
		rentBookHBox.setSpacing(10);
		rentBookHBox.setPadding(new Insets(0, 0, 10, 250));

		BorderPane rentBookBorderPane = new BorderPane();
		rentBookBorderPane.setBottom(rentBookHBox);
		rentBookScene = new Scene(rentBookBorderPane, 400, 300);
		
		//Menu button to switch to rent book scene 
		Button rentButton = new Button("     Rent a book    ");
		rentButton.setOnAction(e -> updateRentTable(bookCatalog));//sets scene to the rent book table and updates the table with the correct available books
		menuLayout.getChildren().add(rentButton);
		
		//Switch back to menu button
		Button switchToMenuFromRentBookButton = new Button("Menu");
		switchToMenuFromRentBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));//sets scene back to the main menu
		rentBookHBox.getChildren().add(switchToMenuFromRentBookButton);
		
		//Button to rent a book
		Button rentBookButton = new Button("Rent Book");
		rentBookButton.setOnAction(e -> rentBook(bookCatalog, fileName)); //rents a book and rewrites BookCatalog.txt file  
		rentBookHBox.getChildren().add(rentBookButton);
		
		//Table view for available books
		availableBooksTableView = new TableView();
		rentBookBorderPane.setCenter(availableBooksTableView);
		
		//Resizes columns based of table space gives each column an equal percentage of that space
		availableBooksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		//Adds table Columns and sets table cell values 
		TableColumn bookIdColumn = new TableColumn<Book, String>("Id");
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));// Pulls ids from book objects by calling the getId() getter method (works by capitalizing the first letter in the string and then places get before said string then calls that method)
		
		TableColumn bookTitleColumn = new TableColumn<Book, String>("Title");
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn bookAuthorColumn = new TableColumn<Book, String>("Author");
		bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		
		//Adds columns to the table
		availableBooksTableView.getColumns().add(bookIdColumn);
		availableBooksTableView.getColumns().add(bookTitleColumn);
		availableBooksTableView.getColumns().add(bookAuthorColumn);
		
		//Adds a custom message if there is no data in the table (no books available)
	    Label customMessageForRentTable = new Label("All books are currently being rented");
        availableBooksTableView.setPlaceholder(customMessageForRentTable);


		
		/* RETURN BOOK SECTION */
	
		
		HBox returnBookHBox = new HBox();
		returnBookHBox.setSpacing(10);
		returnBookHBox.setPadding(new Insets(0, 0, 10, 250));

		BorderPane returnBookBorderPane = new BorderPane();
		returnBookBorderPane.setBottom(returnBookHBox);
		returnBookScene = new Scene(returnBookBorderPane, 400, 300);
		
		//Menu button to switch to return book scene
		Button returnButton = new Button("   Return a book   ");
		returnButton.setOnAction(e -> updateReturnTable(bookCatalog));
		menuLayout.getChildren().add(returnButton);

		//Switch back to menu button
		Button switchToMenuFromReturnBookButton = new Button("Menu");
		switchToMenuFromReturnBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
		returnBookHBox.getChildren().add(switchToMenuFromReturnBookButton);
		
		//Button to return a book
		Button returnBookButton = new Button("Return Book");
		returnBookButton.setOnAction(e -> returnBook(bookCatalog, fileName));
		returnBookHBox.getChildren().add(returnBookButton);
		
		//Table view for unavailable books
		unavailableBooksTableView = new TableView();
		returnBookBorderPane.setCenter(unavailableBooksTableView);
		
		//Sets column size
		unavailableBooksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		//Table Columns
		TableColumn rentedBookIdColumn = new TableColumn<Book, String>("Id");
		rentedBookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		
		TableColumn rentedBookTitleColumn = new TableColumn<Book, String>("Title");
		rentedBookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn rentedBookAuthorColumn = new TableColumn<Book, String>("Author");
		rentedBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		TableColumn lentDateColumn = new TableColumn<Book, Date>("Lent Date");
		lentDateColumn.setCellValueFactory(new PropertyValueFactory<Book, Date>("date"));
		
		
		//Adds columns to bale
		unavailableBooksTableView.getColumns().add(rentedBookIdColumn);
		unavailableBooksTableView.getColumns().add(rentedBookTitleColumn);
		unavailableBooksTableView.getColumns().add(rentedBookAuthorColumn);
		unavailableBooksTableView.getColumns().add(lentDateColumn);

		//Adds custom message for table
        Label customMessageForReturnTable = new Label("All books have been returned");
        unavailableBooksTableView.setPlaceholder(customMessageForReturnTable);
		
		

		/* LOOKUP BOOK SECTION */
		
        HBox searchHBox = new HBox();
		searchHBox.setSpacing(10);
		searchHBox.setPadding(new Insets(0, 0, 10, 250));

		BorderPane searchBorderPane = new BorderPane();
		searchBorderPane.setBottom(searchHBox);
		searchScene = new Scene(searchBorderPane, 400, 300);
		
		
		//Menu button to switch to lookup scene
		Button searchButton = new Button("  Look up a book  ");
		searchButton.setOnAction(e -> primaryStage.setScene(searchScene));
		menuLayout.getChildren().add(searchButton);
		
		//Switch back menu button
		Button switchToMenuFromLookupBookButton = new Button("Menu");
		switchToMenuFromLookupBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
		searchHBox.getChildren().add(switchToMenuFromLookupBookButton);
		
		//Button to search for a book
		Button searchBookButton = new Button("Find Book");
		searchBookButton.setOnAction(e -> searchBook(bookCatalog));
		searchHBox.getChildren().add(searchBookButton);
		
		//Search Bar
		HBox searchBarHBox = new HBox();
		searchBarHBox.setSpacing(10);
		
		//Search bar label and text field
		Label searchBarLabel = new Label("Search Bar");
		searchBarTf = new TextField();

		//Adds search box to the horizontal box layout
		searchBarHBox.getChildren().add(searchBarLabel);
		searchBarHBox.getChildren().add(searchBarTf);
		
		//Places the search bar layout within the border pane
		searchBorderPane.setTop(searchBarHBox);
		searchBarHBox.setPadding(new Insets(10, 0, 0, 80));
		
		//Table view
		foundTableView = new TableView();
		searchBorderPane.setCenter(foundTableView);
		
		//Resizes columns
		foundTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		//Table columns
		TableColumn foundBookIdColumn = new TableColumn<Book, String>("Id");
		foundBookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		
		TableColumn foundBookTitleColumn = new TableColumn<Book, String>("Title");
		foundBookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn foundBookAuthorColumn = new TableColumn<Book, String>("Author");
		foundBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		TableColumn foundStatusColumn = new TableColumn<Book, Date>("Status");
		foundStatusColumn.setCellValueFactory(new PropertyValueFactory<Book, Date>("status"));
		
		
		//Adds columns to the table
		foundTableView.getColumns().add(foundBookIdColumn);
		foundTableView.getColumns().add(foundBookTitleColumn);
		foundTableView.getColumns().add(foundBookAuthorColumn);
		foundTableView.getColumns().add(foundStatusColumn);

		//Adds custom message 
        Label customMessageForSearchTable = new Label("No matches found");
        foundTableView.setPlaceholder(customMessageForSearchTable);
		
		
		/* PRIMARY STAGE */
		
		
		primaryStage.setScene(mainMenuScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Libaray Tracker");
		primaryStage.show();
	}
	
	
	
	//Switches scene to rent a book scene and updates the available books table 
	public void updateRentTable(ArrayList<Book> bookCatalog) {
		
	    //Clears data from table 
	    availableBooksTableView.getItems().clear();

	    int numOfBooks = bookCatalog.size();

	    //Adds all available book's data to the table 
	    for (int x = 0; x < numOfBooks; x++) {
	        if (bookCatalog.get(x).getStatus().equals("available")) {
	            availableBooksTableView.getItems().add(bookCatalog.get(x));//adds available book objects to the table 
	        }
	    }
	    
	    // Switches to rent book scene
	    primaryStage.setScene(rentBookScene);
	}

	
	//Switches scene to return a book scene and updates the unavailable books table 
	public void updateReturnTable(ArrayList<Book> bookCatalog) {
		
	    //Clears data from table
	    unavailableBooksTableView.getItems().clear();

	    int numOfBooks = bookCatalog.size();

	    //Adds all unavailable book's data to the table 
	    for (int x = 0; x < numOfBooks; x++) {
	        if (bookCatalog.get(x).getStatus().equals("unavailable")) {
	            unavailableBooksTableView.getItems().add(bookCatalog.get(x));//adds unavailable book objects to the table 
	        }
	    }

	    // Switches to return book scene
	    primaryStage.setScene(returnBookScene);
	}

	
	//Adds a new book to book catalog and writes the new book to the BookCatalog.txt file
	public ArrayList<Book> addNewBook(ArrayList<Book> bookCatalog,String fileName) {
		
		//Stores values in text fields into variables
		String bookTitle = bookTitleTf.getText();
		String author = authorTf.getText();

		//Exits if the user enters an empty value for the author or title
		if (bookTitle.equals("") || author.equals("")) {
			return bookCatalog;
		}

		//Creates a new book object
		Book newBook = new Book(bookTitle, author);
		
		//Sets the id of the book
		newBook.setId(bookCatalog);

		//Appends new book to the file
		try {
		
			//Writes to the file in append mode
			FileWriter fileWriter = new FileWriter(fileName, true); 
            PrintWriter output = new PrintWriter(fileWriter); 
            
            //Writes each book's member variable to the file
            output.println(newBook.getId());
            output.println(newBook.getTitle());
            output.println(newBook.getAuthor());
            output.println(newBook.getStatus());
            output.println("null");
            
            output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		   
		//Adds book to the book catalog
		bookCatalog.add(newBook);

		//Clears the text fields
		bookTitleTf.clear();
		authorTf.clear();

		return bookCatalog;
	}

	
	//Rents a book and rewrites the BookCatalog.txt file so that the file contains the new book status and lent date
	public ArrayList<Book> rentBook(ArrayList<Book> bookCatalog, String fileName) {
		
		try {
			Book selectedBook = availableBooksTableView.getSelectionModel().getSelectedItem(); //Gets the book object selected by the user
			int row = availableBooksTableView.getSelectionModel().getSelectedIndex();//Gets the row number selected by the user 

			//Rents the selected book
			selectedBook.rentBook();

			//Rewrites the file
			updateFile(bookCatalog, fileName);

			//Deletes the row in table 
			deleteRow(row, availableBooksTableView);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookCatalog;
	}

	
	//Returns a book and rewrites the file
	public ArrayList<Book> returnBook(ArrayList<Book> bookCatalog, String fileName) {
		
		try {
			Book selectedBook = unavailableBooksTableView.getSelectionModel().getSelectedItem();//Gets the book object selected by the user
			int row = unavailableBooksTableView.getSelectionModel().getSelectedIndex();//Gets the row number selected by the user

			//Returns selected book
			selectedBook.returnBook();
			
			//Rewrites the file
			updateFile(bookCatalog, fileName);

			//Deletes the row in table 
			deleteRow(row, unavailableBooksTableView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookCatalog;
	}
	
	//Searches for a book then displays that book in the table view
	public void searchBook(ArrayList<Book> bookCatalog) {
		
		try {
			String searchValue = searchBarTf.getText();//Stores user's search value 
			
			//Clears table data
			foundTableView.getItems().clear();

			int numOfBooks = bookCatalog.size();

			//Searches through each book in the catalog
			for (int x = 0; x < numOfBooks; x++) {
				//If the book's author or title contains the user's search value adds that book object to the table
				if (bookCatalog.get(x).getTitle().contains(searchValue) || bookCatalog.get(x).getAuthor().contains(searchValue)) {
					foundTableView.getItems().add(bookCatalog.get(x));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//Rewrites the file with the updated book catalog
	public void updateFile(ArrayList<Book> bookCatalog, String fileName) {

		File file = new File(fileName);

		
		try {
			
			//Rewrites file
			PrintWriter output = new PrintWriter(file);

			int numOfBooks = bookCatalog.size();

			//Writes all books in the book catalog to the file
			for (int x = 0; x < numOfBooks; x++) {
				output.println(bookCatalog.get(x).getId());
				output.println(bookCatalog.get(x).getTitle());
				output.println(bookCatalog.get(x).getAuthor());
				output.println(bookCatalog.get(x).getStatus());

				//Gets a books date
				Date date = bookCatalog.get(x).getDate();

				//Writes a book's lent date using the date formatter to the file or writes null if a book's lent date is null
				if (date != null) {
					String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";//pattern for the date formatter
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);//date formatter to change a date object to a string value 
					String lendDateString = sdf.format(date);//converts date object to a string value
					output.println(lendDateString);//writes date string value to file
				} else {
					output.println("null");
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	//Deletes a row in a table view
	void deleteRow(int row, TableView<Book> tableView) {
		//Checks if a row was selected
		if (row >= 0) {
			tableView.getItems().remove(row);//deletes selected row from the table view
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
