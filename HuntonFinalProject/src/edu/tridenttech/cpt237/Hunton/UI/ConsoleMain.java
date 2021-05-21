/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the ConsoleMain class is to start the UI and create the Store object. 
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.application.Application;
import javafx.stage.Stage;

public class ConsoleMain extends Application
{
	public static void main(String[] args) 
	{

		//launch the program
		launch(args);

	}//end Main method

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		//create new Store object
		Store store = new Store();
		
		//create a new welcome window and pass the 
		new WelcomeWindow(primaryStage).show(store);

	}//end start method

}//end ConsoleMain
