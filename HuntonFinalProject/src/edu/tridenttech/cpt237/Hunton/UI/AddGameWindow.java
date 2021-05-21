/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the AddGameWindow class is to allow the user to add the game they selected to their order 
 * and select how many days they would like to rent the game for.
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.Game;
import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddGameWindow 
{
	//declare local class variables 
	private int orderID;
	private Store store;
	private Stage addGameStage;
	private String gameName;
	private Text gameNameText;
	private TextField daysRented = new TextField("0");
	
	//the AddGameWindow constructor creates the window to add a game to the order
	public AddGameWindow()
	{
		//declare stage, title, pane, scene
		addGameStage = new Stage();
		addGameStage.setTitle("Add Game to Rent");
		//create vBox for the order window
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 360, 350);
		addGameStage.setScene(scene);

		//create labels and add them to the pane
		Label gameLabel = new Label("Game Name:");
		gameNameText = new Text();
		Label rentalLabel = new Label("Daily Rental Rate:");
		Label rentalRate = new Label("$ 2.00 per day");
		Label daysRentedLabel = new Label("How many days to rent this game?");

		pane.add(gameLabel, 0, 0);
		pane.add(gameNameText, 1, 0);
		pane.add(rentalLabel, 0, 1);
		pane.add(rentalRate, 1, 1);
		pane.add(daysRentedLabel, 0, 2);
		pane.add(daysRented, 1, 2);

		//create buttons to add to the pane
		Button confirm = new Button("Add Game");
		Button cancel = new Button("Cancel");

		//create event handlers for buttons 
		confirm.setOnAction(e -> addToOrder());
		cancel.setOnAction(e -> addGameStage.close());
		
		//add the buttons to the pane
		pane.add(confirm, 0, 3);
		pane.add(cancel, 1, 3);

	}//end AddGameWindow constructor

	//the addToOrder method checks that a valid input has been entered and then adds the game to the order 
	private void addToOrder() 
	{
		//create new error alert
		Alert invalidFormat = new Alert(AlertType.ERROR);

		//set the text in the alert and show it
		invalidFormat.setContentText("The input you entered is not a valid number.");

		//try to add the order 
		try 
		{
			//get the days rented converted to an integer
			int input = Integer.parseInt(daysRented.getText().toString());

			//selection structure making sure the input is valid
			if(input > 0)
			{
				//add the game to the order
				store.addGameToOrder(orderID, input, gameName);
				
				//display confirmation
				Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setContentText("The game " + gameName + " has been added to order " + orderID);
				confirm.show();
				
				//close the addGame stage
				addGameStage.close();
				
			}//end input is valid so add the item
			//else the input is not valid and the error shows
			else
			{
				invalidFormat.show();
			}//end else the input is not valid
		}//end try block
		
		//catch for invalid inputs
		catch(NumberFormatException ex)
		{
			invalidFormat.show();
		}//end catch input not valid
	}//end addToOrder

	//the show method passes the game object, orderID, and Store object and displays the game window
	public void show(Game game, int orderID, Store store) 
	{
		//pass the args to class variables
		this.orderID = orderID;
		this.store = store;
		//set the game name to the text
		this.gameName = game.getGameName();
		this.gameNameText.setText(game.getGameName());

		//display the addGameStage
		addGameStage.show();

		//get the game variables (set to text) so they can be added into the text field


	}//end show method

}//end AddGameWindow class
