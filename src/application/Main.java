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
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintWriter;
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
		
		this.primaryStage = primaryStage;

		ArrayList<Book> bookCatalog = new ArrayList<>();
		
		String fileName = "BookCatalog.txt"; 


		try {
			File file = new File(fileName); 
			Scanner scanner = new Scanner(file); 

			
			while (scanner.hasNextLine()) {
				int bookId = Integer.parseInt(scanner.nextLine());
				String bookTitle = scanner.nextLine();
				String bookAuthor = scanner.nextLine();
				String bookStatus = scanner.nextLine();
				String date = scanner.nextLine();
			
				Date lendDate;
				
				if(date == "null")
				{
					lendDate = null;
				}
				else 
				{
					String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
					lendDate = sdf.parse(date);
				}
				
				
				
				Book newBook = new Book(bookId, bookTitle, bookAuthor, bookStatus, lendDate);
				
				System.out.println(newBook.getId());
				System.out.println(newBook.getTitle());
				System.out.println(newBook.getAuthor());
				System.out.println(newBook.getStatus());
				System.out.println(newBook.getDate());
				
				bookCatalog.add(newBook);
				
			}

	            scanner.close(); // Close the scanner after reading

	        } catch (FileNotFoundException e) {
	            System.out.println("The file does not exist or cannot be read: " + e.getMessage());
	        } catch (ParseException e1) {
				e1.printStackTrace();
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
		addButton.setOnAction(e -> primaryStage.setScene(addNewBookScene));

		//Switch back to menu button
		Button switchToMenuFromAddBookButton = new Button("Menu");
		switchToMenuFromAddBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
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
		rentButton.setOnAction(e -> updateRentTable(bookCatalog));
		menuLayout.getChildren().add(rentButton);
		
		//Switch back to menu button
		Button switchToMenuFromRentBookButton = new Button("Menu");
		switchToMenuFromRentBookButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));
		rentBookHBox.getChildren().add(switchToMenuFromRentBookButton);
		
		//Button to rent a book
		Button rentBookButton = new Button("Rent Book");
		rentBookButton.setOnAction(e -> rentBook(bookCatalog));
		rentBookHBox.getChildren().add(rentBookButton);
		
		//Table view for available books
		availableBooksTableView = new TableView();
		rentBookBorderPane.setCenter(availableBooksTableView);
		
		availableBooksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn bookIdColumn = new TableColumn<Book, String>("Id");
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		
		TableColumn bookTitleColumn = new TableColumn<Book, String>("Title");
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn bookAuthorColumn = new TableColumn<Book, String>("Author");
		bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		
		
		availableBooksTableView.getColumns().add(bookIdColumn);
		availableBooksTableView.getColumns().add(bookTitleColumn);
		availableBooksTableView.getColumns().add(bookAuthorColumn);
		
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
		returnBookButton.setOnAction(e -> returnBook(bookCatalog));
		returnBookHBox.getChildren().add(returnBookButton);
		
		unavailableBooksTableView = new TableView();
		returnBookBorderPane.setCenter(unavailableBooksTableView);
		
		unavailableBooksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn rentedBookIdColumn = new TableColumn<Book, String>("Id");
		rentedBookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		
		TableColumn rentedBookTitleColumn = new TableColumn<Book, String>("Title");
		rentedBookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn rentedBookAuthorColumn = new TableColumn<Book, String>("Author");
		rentedBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		TableColumn lentDateColumn = new TableColumn<Book, Date>("Lent Date");
		lentDateColumn.setCellValueFactory(new PropertyValueFactory<Book, Date>("date"));
		
		
		
		unavailableBooksTableView.getColumns().add(rentedBookIdColumn);
		unavailableBooksTableView.getColumns().add(rentedBookTitleColumn);
		unavailableBooksTableView.getColumns().add(rentedBookAuthorColumn);
		unavailableBooksTableView.getColumns().add(lentDateColumn);

		  
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
		
		Label searchBarLabel = new Label("Search Bar");
		searchBarTf = new TextField();

		searchBarHBox.getChildren().add(searchBarLabel);
		searchBarHBox.getChildren().add(searchBarTf);
		
		searchBorderPane.setTop(searchBarHBox);
		searchBarHBox.setPadding(new Insets(10, 0, 0, 80));
		
		//Table view
		foundTableView = new TableView();
		searchBorderPane.setCenter(foundTableView);
		
		foundTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn foundBookIdColumn = new TableColumn<Book, String>("Id");
		foundBookIdColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		
		TableColumn foundBookTitleColumn = new TableColumn<Book, String>("Title");
		foundBookTitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		
		TableColumn foundBookAuthorColumn = new TableColumn<Book, String>("Author");
		foundBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		
		TableColumn foundStatusColumn = new TableColumn<Book, Date>("Status");
		foundStatusColumn.setCellValueFactory(new PropertyValueFactory<Book, Date>("status"));
		
		
		
		foundTableView.getColumns().add(foundBookIdColumn);
		foundTableView.getColumns().add(foundBookTitleColumn);
		foundTableView.getColumns().add(foundBookAuthorColumn);
		foundTableView.getColumns().add(foundStatusColumn);

		  
        Label customMessageForSearchTable = new Label("No matches found");
        foundTableView.setPlaceholder(customMessageForSearchTable);
		
		
		/* PRIMARY STAGE */
		
		
		primaryStage.setScene(mainMenuScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Libaray Tracker");
		primaryStage.show();
	}
	
	public void updateRentTable(ArrayList<Book> bookCatalog) {
		// Update the TableView data
		availableBooksTableView.getItems().clear();

		int numOfBooks = bookCatalog.size();

		for (int x = 0; x < numOfBooks; x++) {
			if (bookCatalog.get(x).getStatus() == "available") {
				availableBooksTableView.getItems().add(bookCatalog.get(x));
			}
		}

		// Switches to rent book scene
		primaryStage.setScene(rentBookScene);
	}

	public void updateReturnTable(ArrayList<Book> bookCatalog) {
		// Update the TableView data
		unavailableBooksTableView.getItems().clear();

		int numOfBooks = bookCatalog.size();

		for (int x = 0; x < numOfBooks; x++) {
			if (bookCatalog.get(x).getStatus() == "unavailable") {
				unavailableBooksTableView.getItems().add(bookCatalog.get(x));
			}
		}

		// Switches to return book scene
		primaryStage.setScene(returnBookScene);
	}

	public ArrayList<Book> addNewBook(ArrayList<Book> bookCatalog, String fileName) {
		String bookTitle = bookTitleTf.getText();
		String author = authorTf.getText();

		if (bookTitle == "" || author == "") {
			return bookCatalog;
		}

		Book newBook = new Book(bookTitle, author);
		
		// Sets the id of the book
		newBook.setId(bookCatalog);

		try {
			File file = new File(fileName); 

			PrintWriter fileWriter = new PrintWriter(file);
			fileWriter.write(newBook.getId());
			fileWriter.write(newBook.getTitle());
			fileWriter.write(newBook.getAuthor());
			fileWriter.write(newBook.getStatus());
			fileWriter.write("null");
			fileWriter.close();
		} catch (IOException e) {
			
		}
		   
		

		bookCatalog.add(newBook);

		bookTitleTf.clear();
		authorTf.clear();

		return bookCatalog;
	}

	public ArrayList<Book> rentBook(ArrayList<Book> bookCatalog) {
		try {
			Book selectedBook = availableBooksTableView.getSelectionModel().getSelectedItem();
			int row = availableBooksTableView.getSelectionModel().getSelectedIndex();

			selectedBook.rentBook();

			deleteRow(row, availableBooksTableView);
		} catch (Exception e) {
		}

		return bookCatalog;
	}

	public ArrayList<Book> returnBook(ArrayList<Book> bookCatalog) {
		try {
			Book selectedBook = unavailableBooksTableView.getSelectionModel().getSelectedItem();
			int row = unavailableBooksTableView.getSelectionModel().getSelectedIndex();

			selectedBook.returnBook();

			deleteRow(row, unavailableBooksTableView);
		} catch (Exception e) {
		}

		return bookCatalog;
	}

	public void searchBook(ArrayList<Book> bookCatalog) {
		try {
			String searchValue = searchBarTf.getText();
			
			foundTableView.getItems().clear();

			int numOfBooks = bookCatalog.size();

			for (int x = 0; x < numOfBooks; x++) {
				if (bookCatalog.get(x).getTitle().contains(searchValue) || bookCatalog.get(x).getAuthor().contains(searchValue)) {
					foundTableView.getItems().add(bookCatalog.get(x));
				}
			}
		} catch (Exception e) {
		}

	}

	void deleteRow(int row, TableView<Book> tableView) {
		if (row >= 0) {
			tableView.getItems().remove(row);
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
