/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the WelcomeWindow class is to display the opening window that allows the user to start an order
 * display all orders, or quit.  
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class WelcomeWindow 
{
	//the startOrder inner class creates a new OrderWindow (if one hasn't been created) and passes the Cafe object. 
	private class startOrder implements EventHandler<ActionEvent>
	{
		//create OrderWindow object and a pending order 
		private OrderWindow orderWindow = new OrderWindow(); 

		@Override
		public void handle(ActionEvent event)
		{
			//if the window is not showing, create a new one
			if(!orderWindow.isShowing())
			{
				//start a new order in the cafe and assign the ID of the order to a local variable
				int orderID = store.startNewOrder();

				//display the order window
				orderWindow.show(store, orderID);
			}//end the order
			//else the menu is displayed and should be brought to the front
			else
			{
				orderWindow.toFront();
			}//end else bring order menu to the front

		}//end action event method
	}//end startOrder inner class
	//declare class variables 
	private Stage mainStage;
	private Store store;

	//the WelcomeWindow constructor creates the opening window
	public WelcomeWindow(Stage mainStage)
	{
		this.mainStage = mainStage;
		mainStage.setTitle("Game Emporium Game Rental");
		FlowPane pane = new FlowPane();
		Scene scene = new Scene(pane, 250, 250);
		mainStage.setScene(scene);

		//create buttons for New Order, Display Orders, and Quit
		Button rentBtn = new Button("New Rental");
		Button displayBtn = new Button("Display Orders Report");
		Button quitBtn = new Button("Quit");

		//add buttons to the pane 
		pane.getChildren().add(rentBtn);
		pane.getChildren().add(displayBtn);
		pane.getChildren().add(quitBtn);

		//create event handlers for buttons 
		//the New Order button creates an OrderWindow
		rentBtn.setOnAction( new startOrder());

		//create DisplayOrders event handler
		displayBtn.setOnAction(e -> displayOrders());

		//create quit button event handler
		quitBtn.setOnAction(e-> mainStage.close());

	}//end WelcomeWindow constructor

	//the displayOrders method calls the displayOrders window
	private void displayOrders()
	{
		//create a new DailyOrderReport object and display it
		DailyOrderReport report = new DailyOrderReport();
		report.show(store);
	}//end displayOrders

	//the show method displays the welcome window and passes the store
	public void show(Store store) 
	{
		//assign the args and display the stage
		this.store = store;
		mainStage.show();
	}//end show

}//end WelcomeWindow class
