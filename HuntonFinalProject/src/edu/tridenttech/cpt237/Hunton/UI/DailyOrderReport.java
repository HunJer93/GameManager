/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the DailyOrderReport class is to display the daily report with all the sales for the day.
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.UI;

import edu.tridenttech.cpt237.Hunton.model.CustOrder;
import edu.tridenttech.cpt237.Hunton.model.Game;
import edu.tridenttech.cpt237.Hunton.model.Store;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DailyOrderReport 
{
	private Store store;
	private Stage reportStage;
	private TextArea text;
	private double orderGrandTotal;

	//the DailyOrderReport constructor creates the window to display the order report
	public DailyOrderReport()
	{
		//declare stage, title, pane, scene
		reportStage = new Stage();
		reportStage.setTitle("Daily Rental Report");
		//create TextArea for the order window
		text = new TextArea();
		text.setMinWidth(550);
		text.setEditable(false);
		//set the textArea font
		text.setFont(new Font("Consolas", 12));
		FlowPane pane = new FlowPane(text);
		Scene scene = new Scene(pane, 550, 350);
		reportStage.setScene(scene);
		//add a title to the text area
		text.appendText("Daily Rental Report\n");

		//add buttons to quit, daily report, sort by the game system
		Button dailyReport = new Button("Default Daily Report");
		pane.getChildren().add(dailyReport);
		Button sortByType = new Button("Sort By Game System");
		pane.getChildren().add(sortByType);
		Button quitBtn = new Button("Close");
		pane.getChildren().add(quitBtn);

		//event handlers for each button
		dailyReport.setOnAction(e -> defaultReport());
		sortByType.setOnAction(e-> sortByType());
		quitBtn.setOnAction(e-> reportStage.close());

	}//end DailyOrderReport constructor

	//defaultReport appends the default report to the text
	private void defaultReport() 
	{
		//use a for loop to display the orders within the store
		for(CustOrder order : store.getOrderList())
		{
			//assign local variables for the order id
			int orderID = order.getOrderID();

			//display the order ID 
			text.appendText(String.format("\n%s%d\n", "Order ID: ", orderID));
			text.appendText(String.format("%-38s%10s%12s%11s\n", "Game Name", "System", "Days Rented", "Total"));

			//for loop cycling the games list in each order
			for(Game game : order.getOrderGameList())
			{
				//print the info for the single game
				text.appendText(String.format("%-38s%10s%12s%4s%7.2f\n", game.getGameName(), game.getGameType(), game.getRentalDays(), "$", game.getGameCost()));
			}//end for loop cycling the games list 

			//display the grand total of the order at the bottom of the list
			text.appendText(String.format("\n%-38s%26s%7.2f\n", "Grand Total:", "$", order.getOrderTotal()));

			//increment the grand total 
			orderGrandTotal+= order.getOrderTotal();
		}//end for loop cycling the store orders

		//end the report with the grand total
		text.appendText(String.format("\n%-38s%26s%7.2f\n", "Total Daily Sales:", "$", orderGrandTotal));
	}//end defaultReport

	//sortByType changes the text to display the report sorted by each game type sold
	private void sortByType() 
	{
		//declare local variables
		double nintendoSales = 0.0;
		int nintendoDaysRented = 0;
		double ps4Sales = 0.0;
		int ps4DaysRented = 0;
		double xboxSales = 0.0;
		int xboxDaysRented = 0;
		double dailySales = 0.0;
		int dailyRentals = 0;
		//title of menu
		text.appendText(String.format("\n%s\n", "Daily Sales By Format"));
		text.appendText(String.format("%-17s%12s%18s\n", "Game Type", "Total Sales", "Total Days Rented"));
		
		//for loop cycling all the orders
		for(CustOrder order : store.getOrderList())
		{
			//for loop cycling each game in the order
			for(Game game: order.getOrderGameList())
			{
				//selection structure getting the game type and assigning it to a total
				if(game.getGameType().equalsIgnoreCase("nintendo"))
				{
					//accumulate the totals and days rented
					nintendoSales += game.getGameCost();
					nintendoDaysRented += game.getRentalDays();
					dailySales += game.getGameCost();
					dailyRentals += game.getRentalDays();
				}//end case nintendo
				else if(game.getGameType().equalsIgnoreCase("ps4"))
				{
					//accumulate the totals and days rented
					ps4Sales += game.getGameCost();
					ps4DaysRented = game.getRentalDays();
					dailySales += game.getGameCost();
					dailyRentals += game.getRentalDays();
				}//end case ps4
				else
				{
					//accumulate the totals and days rented
					xboxSales += game.getGameCost();
					xboxDaysRented += game.getRentalDays();
					dailySales += game.getGameCost();
					dailyRentals += game.getRentalDays();
				}//end else the case is an xbox
			}//end for loop cycling each game in the order
		}//end for loop cycling all orders
		
		//display each total
		text.appendText(String.format("%-17s%2s%10.2f%18d\n", "Nintendo Sales: ", "$", nintendoSales, nintendoDaysRented ));
		text.appendText(String.format("%-17s%2s%10.2f%18d\n", "PS4 Sales: ", "$", ps4Sales, ps4DaysRented ));
		text.appendText(String.format("%-17s%2s%10.2f%18d\n", "Xbox Sales: ", "$", xboxSales, xboxDaysRented ));
		text.appendText(String.format("%-17s%2s%10.2f\n", "Daily Totals: ", "$", dailySales, dailyRentals));
		
	}//end sortByType

	//the show method displays the report stage and passes the store obj
	public void show(Store store) 
	{
		//pass the store object
		this.store = store;
		
		//display the report stage
		reportStage.show();

		//display the default report
		defaultReport();
	}//end show method

}//end DailyOrderReport
