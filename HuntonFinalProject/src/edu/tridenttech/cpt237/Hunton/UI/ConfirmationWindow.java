package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.Game;
import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfirmationWindow 
{
	private int orderID;
	private Store store;
	private Stage orderStage;
	private Stage confirmStage;
	private TextArea text;

	//the ConfirmationWindow constructor creates the window 
	public ConfirmationWindow()
	{
		//declare stage, title, pane, scene
		confirmStage = new Stage();
		confirmStage.setTitle("Order Confirmation");
		//create TextArea for the order window
		text = new TextArea();
		text.setMinWidth(550);
		text.setEditable(false);
		//set the textArea font
		text.setFont(new Font("Consolas", 12));
		FlowPane pane = new FlowPane(text);
		Scene scene = new Scene(pane, 550, 250);
		confirmStage.setScene(scene);
		//add a title to the text area
		text.appendText("Order Confirmation\n");

		//add confirmation button and cancel button
		Button confirmBtn = new Button("Confirm");
		Button cancelBtn = new Button("Cancel");

		//add button to pane
		pane.getChildren().add(confirmBtn);
		pane.getChildren().add(cancelBtn);

		//create event handlers for each button
		confirmBtn.setOnAction(e -> processOrder());
		cancelBtn.setOnAction(e -> cancelOrder());
	}//end ConfirmationWindow constructor

	//cancelOrder cancels the order, sends an alert, and closes the window
	private void cancelOrder() 
	{
		//create alert
		Alert cancelAlert = new Alert(AlertType.CONFIRMATION);

		cancelAlert.setContentText("You have cancelled the order process. The order has not been processed. Returning to the Order Menu");

		cancelAlert.show();

		//close the stage
		confirmStage.close();
	}//end cancelOrder

	//the processOrder method adds the order to the confirmed order list in the store and closes the order window
	private void processOrder() 
	{
		//place the order in the store
		store.placeOrder(orderID);

		//create alert for items added
		Alert orderProcessedAlert = new Alert(AlertType.CONFIRMATION);

		orderProcessedAlert.setContentText("The order " + orderID + " has been processed. Closing window for next order.");
		//show the alert
		orderProcessedAlert.show();

		//close the confirmation stage and the order stage
		confirmStage.close();
		orderStage.close();

	}//end processOrder method

	//the show method displays the confirmation window stage and passes the args.
	public void show(int orderID, Store store, Stage orderStage) 
	{
		//pass the args 
		this.orderID = orderID;
		this.store = store;
		this.orderStage = orderStage;

		//open the confirmation stage
		confirmStage.show();

		//use a private method to display the purchases
		displayPurchases();
	}//end show method

	//displayPurchases method cycles the order list and adds it to the text box on the screen
	private void displayPurchases() 
	{
		double grandTotal = 0.0;
		
		text.appendText(String.format("%s%d\n", "Order ID:", orderID));
		text.appendText(String.format("%s%s\n", "Reminder:", "All games have a $2.00 day rate."));
		text.appendText(String.format("\n%-40s%14s%12s%11s\n", "Game Rented", "System", "Days Rented", "Total"));
		
		//for loop cycling the games rented
		for(Game game : store.findPendingOrderByID(orderID).getOrderGameList())
		{
			//create a local variable for each item to make it cleaner
			String gameName = game.getGameName();
			String system = game.getGameType();
			int daysRented = game.getRentalDays();
			double gameCost = game.getGameCost();
			
			//add the game to the text
			text.appendText(String.format("%-40s%14s%12d%4s%7.2f\n", gameName, system, daysRented, "$", gameCost));
			
			//add to the grandTotal
			grandTotal += gameCost;
		}//end for loop cycling the game list
		
		//display the final total
		text.appendText(String.format("%-66s%4s%7.2f\n", "Order Total:", "$", grandTotal));

	}//end displayPurchases

}//end ConfirmationWindow class
