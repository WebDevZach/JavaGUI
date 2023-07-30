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
import java.util.ArrayList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class Main extends Application {

	private TextField bookTitleTf;
	private TextField authorTf;
	private Scene scene1;
	private Stage primaryStage;
	private TableView<Book> availableBooksTableView;
	private Scene rentBookScene;

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;

		ArrayList<Book> bookCatalog = new ArrayList<>();
		
		Book meep = new Book("jimmy", "johns");
		meep.setId(bookCatalog);
		bookCatalog.add(meep);
		
		
		Book zeep = new Book("big", "chuck");
		zeep.setId(bookCatalog);
		bookCatalog.add(zeep);
		
		
		/* MENU SECTION */

		VBox menuLayout = new VBox();
		menuLayout.setSpacing(35);
		BorderPane menuBorderPane = new BorderPane();
		menuBorderPane.setTop(menuLayout);
		menuLayout.setPadding(new Insets(30, 0, 0, 150));
		scene1 = new Scene(menuBorderPane, 400, 300);

		
		
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

		//Switch back menu button
		Button switchToMenuFromAddBookButton = new Button("Menu");
		switchToMenuFromAddBookButton.setOnAction(e -> primaryStage.setScene(scene1));
		addBookHBox.getChildren().add(switchToMenuFromAddBookButton);
		
		//Button to add a new book to the catalog
		Button addNewBookButton = new Button("Add Book");
		addNewBookButton.setOnAction(e -> addNewBook(bookCatalog));
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
		rentButton.setOnAction(e -> updateTable(bookCatalog));
		menuLayout.getChildren().add(rentButton);
		
		//Switch back menu button
		Button switchToMenuFromRentBookButton = new Button("Menu");
		switchToMenuFromRentBookButton.setOnAction(e -> primaryStage.setScene(scene1));
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

		
		int numOfBooks = bookCatalog.size();
		
		for(int x = 0; x < numOfBooks; x++)
		{
			availableBooksTableView.getItems().add(bookCatalog.get(x));
		}
		
	
	

		
		/* RETURN BOOK SECTION */
	
		
		VBox returnBookLayout = new VBox();
		Scene returnBookScene = new Scene(returnBookLayout, 400, 300);
		
		//Menu button to switch to return book scene
		Button returnButton = new Button("   Return a book   ");
		returnButton.setOnAction(e -> primaryStage.setScene(returnBookScene));
		menuLayout.getChildren().add(returnButton);

		//Switch back menu button
		Button switchToMenuFromReturnBookButton = new Button("Menu");
		switchToMenuFromReturnBookButton.setOnAction(e -> primaryStage.setScene(scene1));
		returnBookLayout.getChildren().add(switchToMenuFromReturnBookButton);

		
		
		/* LOOKUP BOOK SECTION */
		
		
		VBox lookupBook = new VBox();
		Scene lookupBookScene = new Scene(lookupBook, 400, 300);
		
		//Menu button to switch to lookup scene
		Button searchButton = new Button("  Look up a book  ");
		searchButton.setOnAction(e -> primaryStage.setScene(lookupBookScene));
		menuLayout.getChildren().add(searchButton);
		
		//Switch back menu button
		Button switchToMenuFromLookupBookButton = new Button("Menu");
		switchToMenuFromLookupBookButton.setOnAction(e -> primaryStage.setScene(scene1));
		lookupBook.getChildren().add(switchToMenuFromLookupBookButton);
		


		/* PRIMARY STAGE */
		
		
		primaryStage.setScene(scene1);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Libaray Tracker");
		primaryStage.show();
	}

	public void updateTable(ArrayList<Book> bookCatalog)
	{
		// Update the TableView data
	    availableBooksTableView.getItems().clear();
	    
	    int numOfBooks = bookCatalog.size();
	    
	    for(int x = 0; x < numOfBooks; x++)
	    {
	    availableBooksTableView.getItems().add(bookCatalog.get(x));
	    }
	    
	    // Switches to rent book scene
	    primaryStage.setScene(rentBookScene);
	}
	
	
	
	public ArrayList<Book> addNewBook(ArrayList<Book> bookCatalog) {
		String bookTitle = bookTitleTf.getText();
		String author = authorTf.getText();

		Book newBook = new Book(bookTitle, author);
		

		// Sets the id of the book
		newBook.setId(bookCatalog);

		bookCatalog.add(newBook);


		bookTitleTf.clear();
		authorTf.clear();
		
		return bookCatalog;
	}

	public ArrayList<Book> rentBook(ArrayList<Book> bookCatalog)
	{
		Book meep = availableBooksTableView.getSelectionModel().getSelectedItem();
		
		int numOfBooks = bookCatalog.size(); 
		
		System.out.println(meep.title);
		
		meep.rentBook();
		
//		System.out.println(row);
//		System.out.println(bookCatalog.get(row).title);
//		
//		
//		bookCatalog.get(row).rentBook();
//		
//		if(row >= 0)
//		{
//			availableBooksTableView.getItems().remove(row);
//		}
		
		for (int y = 0; y < numOfBooks; y++)
		{
			System.out.println(bookCatalog.get(y).getTitle());
			System.out.println(bookCatalog.get(y).getStatus());
			
		}
		
		return bookCatalog;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
