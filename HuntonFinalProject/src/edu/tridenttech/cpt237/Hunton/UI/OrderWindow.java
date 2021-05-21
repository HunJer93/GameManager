/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the OrderWindow class is to allow the user to pick out what type of game they would like to purchase
 * to begin the order process.
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.Game;
import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderWindow 
{
	//declare class variables
	private Store store;
	private int orderID;
	//declare class variables for the stage and right list view
	private Stage orderStage = new Stage();
	private ListView<String> rightView = new ListView<String>();
	//declare lists for each game type
	private ObservableList<String> nintendoList = FXCollections.observableArrayList();
	private ObservableList<String> ps4List = FXCollections.observableArrayList();
	private ObservableList<String> xboxList = FXCollections.observableArrayList();
	//declare radio buttons for each game type and a toggle group for the buttons
	private RadioButton nintendoBtn = new RadioButton("Nintendo Game List");
	private RadioButton ps4Btn = new RadioButton("PS4 Game List");
	private RadioButton xboxBtn = new RadioButton("Xbox Game List");
	private ToggleGroup buttonGroup = new ToggleGroup();

	//the OrderWindow constructor creates the order window
	public OrderWindow()
	{
		//declare stage, title, pane, scene
		orderStage = new Stage();
		orderStage.setTitle("Rental Window");
		//create BorderPane
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 600, 300);
		orderStage.setScene(scene);
		
		//create labels for each pane
		Label typesLabel = new Label("Game Types");
		Label gameListLabel = new Label("Game List");

		//create a left pane to hold the radio buttons
		VBox leftPane = new VBox();

		//add the label and radio buttons group into the leftPane
		leftPane.getChildren().add(typesLabel);
		//set the buttons into the group
		nintendoBtn.setToggleGroup(buttonGroup);
		ps4Btn.setToggleGroup(buttonGroup);
		xboxBtn.setToggleGroup(buttonGroup);
		//set the nintendo button to start
		buttonGroup.selectToggle(nintendoBtn);

		//add all of the buttons to the vbox
		leftPane.getChildren().add(nintendoBtn);
		leftPane.getChildren().add(ps4Btn);
		leftPane.getChildren().add(xboxBtn);
		//add the left pane to the left side of the BorderPane
		pane.setLeft(leftPane);

		//create a Vbox for the right pane
		VBox rightPane = new VBox();
		//add the right view to the Vbox
		rightPane.getChildren().add(gameListLabel);
		rightPane.getChildren().add(rightView);
		//add the right pane to the BorderPane
		rightView.setItems(nintendoList);
		//add the right pane to the right side of the BorderPane
		pane.setRight(rightPane);

		//create a Hbox for the buttons 
		HBox buttonBar = new HBox();
		//create buttons for Add Selected Game, Place Order, and Cancel Order
		Button addGameBtn = new Button("Add Selected Game");
		Button placeOrderBtn = new Button("Place Order");
		Button cancelBtn = new Button("Cancel Order");
		//add buttons to the panel
		buttonBar.getChildren().add(addGameBtn);
		buttonBar.getChildren().add(placeOrderBtn);
		buttonBar.getChildren().add(cancelBtn); 
		//add the HBox to the bottom of the BorderPane
		pane.setBottom(buttonBar);

		//create actions to set the list view based upon the radio button selected
		nintendoBtn.setOnAction(e -> rightView.setItems(nintendoList));
		ps4Btn.setOnAction(e -> rightView.setItems(ps4List));
		xboxBtn.setOnAction(e -> rightView.setItems(xboxList));

		//create actions for the HBox buttons 
		addGameBtn.setOnAction(e -> addGame());
		placeOrderBtn.setOnAction(e -> placeOrder());
		cancelBtn.setOnAction(e -> cancelOrder());
	}//end OrderWindow class constructor



	//the placeOrder method turns the pending order into an order and processes it
	private void placeOrder() 
	{
		//if/else structure checking if the order has any items
		if(store.findPendingOrderByID(orderID).getOrderGameList().isEmpty() == true)
		{
			//create alert for error
			Alert noItemsAlert = new Alert(AlertType.ERROR);

			noItemsAlert.setContentText("The order entered does not have any items and cannot be processed");

			noItemsAlert.show();
		}//end there are no items added
		//else there are items and the order is processed
		else
		{
			//create a new confirmation window
			ConfirmationWindow receipt = new ConfirmationWindow();

			//pass the orderID, the Store object, and the current stage
			receipt.show(orderID, store, orderStage);
		}//end else the order is processed  
	}//end placeOrder method


	//the cancelOrder method cancels the order and alerts the user that the order has been canceled if they had items in the order
	private void cancelOrder() 
	{
		//check if the user has a pending order and alert them
		if(store.findPendingOrderByID(orderID).getOrderGameList().isEmpty() == false)
		{
			//create alert message for order being canceled 
			Alert cancelAlert = new Alert(AlertType.INFORMATION);
			cancelAlert.setContentText("Your order has been cancelled");
			cancelAlert.show();
			//cancel the order in the store
			store.cancelPendingOrder(orderID);
		}//end alert the user

		//close the stage
		orderStage.close();	
	}//end cancelOrder


	//addGame creates a new order if an order hasn't been created and adds a game to it
	private void addGame()
	{
		//get the selected item from the right window
		String gameSelected = rightView.getSelectionModel().getSelectedItem();

		//selection structure that a game is selected
		if(gameSelected == null)
		{
			//create an alert that no game is selected
			Alert noGameSelected = new Alert(AlertType.ERROR);
			noGameSelected.setContentText("Error. No game was selected. Please select a game.");
			noGameSelected.show();
		}//end a game is not selected so show error

		//selection structure if the game is out of stock
		else if(gameSelected.contains("OUT OF STOCK"))
		{
			//create an alert that the game is out of stock
			Alert outOfStock = new Alert(AlertType.ERROR);
			outOfStock.setContentText("Error. This item is out of stock and cannot be added.");
			outOfStock.show();

		}//end the game is out of stock, so display error
		//else the game is in stock and can be added to the order. 
		else
		{
			//selection structure making sure that the game is not already in the pending order
			if(store.findPendingOrderByID(orderID).getOrderGameList().contains(store.findGame(gameSelected)))
			{
				//alert that the game is already in the order
				Alert duplicateGame = new Alert(AlertType.WARNING);
				duplicateGame.setContentText("This game has already been added to the order and cannot be added again.");
				duplicateGame.show();
			}//end the game is already in the order

			//else the game is not a duplicate and can be added to the pending order
			else
			{
				//create a new addGameWindow 
				AddGameWindow addGameWindow = new AddGameWindow();

				//find the game based upon the name from the inventory list
				Game game = store.findGame(gameSelected); 
				
				//call the show method and pass the game, orderID, and store
				addGameWindow.show(game, orderID, store);
			}//end else the game is added to the order
		}//end else game is available
	}//end addGame


	//the isShowing method returns if the orderStage is showing or not.
	public boolean isShowing() 
	{
		return orderStage.isShowing();
	}//end is showing


	//the toFront method brings the order stage to the front
	public void toFront() 
	{
		orderStage.toFront();
		//set the icon
		orderStage.setIconified(false);

	}//end toFront method 

	//the show method displays the orderStage and passes args for the store object and the current orderID


	public void show(Store store, int orderID) 
	{
		//pass the args to the class variables 
		this.store = store;
		this.orderID = orderID;

		//clear the list every time it is opened
		nintendoList.clear();
		ps4List.clear();
		xboxList.clear();
		//selection structure checking that the game lists are empty
		if(nintendoList.isEmpty() || ps4List.isEmpty() || xboxList.isEmpty())
		{
			//for loop cycling the store object to get the list of games and assign them to each list
			for(Game game : store.getInventoryList())
			{
				//selection structure checking the game types
				if(game.getGameType().equalsIgnoreCase("Nintendo"))
				{																						
					//selection structure checking if the game is in stock
					if(game.getQty() <= 0)
					{
						//add the game to the list but state that it is out of stock
						nintendoList.add(game.getGameName() + " : OUT OF STOCK!");
						
					}//end selection structure listing game out of stock

					//else the game is in stock
					else
					{
						nintendoList.add(game.getGameName());
					}//end else the game is in stock
				}//end game is a nintendo game

				//else if the game is a ps4
				else if(game.getGameType().equalsIgnoreCase("ps4"))
				{
					//selection structure checking if the game is in stock
					if(game.getQty() <= 0)
					{
						//add the game to the list but state that it is out of stock
						ps4List.add(game.getGameName() + " : OUT OF STOCK!");
						
					}//end selection structure listing game out of stock

					//else the game is in stock
					else
					{
						//assign the game to the list
						ps4List.add(game.getGameName());
					}//end else the game is in stock
				}//end the game is a ps4 game

				//else the game is an xbox game 
				else
				{
					//selection structure checking if the game is in stock
					if(game.getQty() <= 0)
					{
						//add the game to the list but state that it is out of stock
						xboxList.add(game.getGameName() + " : OUT OF STOCK!");
						
					}//end selection structure listing game out of stock

					//else the game is in stock
					else
					{
						//assign the game to the list
						xboxList.add(game.getGameName());
					}//end else the game is in stock
				}//end else the game is an xbox game

			}//end for loop cycling the entire game list
		}//end the lists are empty so populate them

		//display the order stage 
		orderStage.show();
	}//end show method

}//end OrderWindow
