/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the Store class is to manage the game objects, order creation, and the store inventory.
 * @author Jeremy Hunton
 */

package edu.tridenttech.cpt237.Hunton.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

	//declare class constants and variables
	private final static String INVENTORY_FILE_NAME = "game_inventory.txt";
	private ArrayList<Game> inventoryList = new ArrayList<>();
	private ArrayList<CustOrder> pendingOrderList = new ArrayList<>();
	private ArrayList<CustOrder> processedOrderList = new ArrayList<>();

	//the Store constructor creates the Store object and loads the inventory file
	public Store()
	{
		loadInventory();
	}//end Store constructor

	//start of void methods 

	//the loadInventory method loads the inventory using the inventory file name
	private void loadInventory()
	{
		//create record count for line error reports
		int recordCount = 1;
		//try block trying to open the file
		try
		{
			//declare variables for each part of the file
			int qty;
			String gameType;
			String gameName;
			
			//scan for file
			Scanner infile = new Scanner(new FileInputStream(INVENTORY_FILE_NAME));
			//while loop cycling the lines in the file
			while(infile.hasNext())
			{
				//create a String array for the line split at a comma
				String[] incomingLine = infile.nextLine().split(",");

				//selection structure checking that there aren't too many data inputs 
				if(incomingLine.length != 3)
				{
					//display error for line
					System.err.printf("%s\n", "Error on line " + recordCount + ". Incorrect number of data points per line. Line skipped.");
					
				}//end error catching wrong number of data points on line
			
				//selection structure checking for invalid inputs on the first entry
				if(Integer.parseInt(incomingLine[0])> 0)
				{
					//break up the pieces to variables (converted, of course)
					qty =  Integer.parseInt(incomingLine[0]);
					gameName = incomingLine[2];
					int gameID = Integer.parseInt(incomingLine[1]);
					//selection statement to make the gameType a little cleaner (name instead of number)
					if(gameID == 1)
					{
						gameType = "Nintendo";
					}//end game is a Nintendo game 
					else if(gameID == 2)
					{
						gameType = "PS4";
					}//end game is a PS4 game
					else if(gameID == 3)
					{
						gameType = "Xbox";
					}//end game is an xbox game
					else
					{
						gameType = null;
					}//else the game is null

					//create a new game object with the variables 
					Game game = new Game(qty, gameType, gameName);
					//load the game into the inventory list
					inventoryList.add(game);

				}//end the quantity greater than 0 and valid
				
				//else display error
				else
				{
					System.err.printf("%s\n", "Error on line " + recordCount + ". Invalid quantity. Line skipped.");
				}//end else display error
				
				//increment the record count for the line error
				recordCount++;
				
			}//end while loop cycling the file 

			//close the infile 
			infile.close();
		}//end try block

		//catch if the file is not found
		catch(IOException e)
		{
			//call e object and display message
			e.printStackTrace();

		}//end catch clause
		//catch if there is a number format exception
		catch(NumberFormatException e)
		{
			//print system error
			System.err.printf("%s\n", "Error on line " + recordCount +". Illegal format. Check the file for correct data points.");
		}
	}//end loadInventory method

	//the findGame method searches the inventory list and returns the game being searched for
	public Game findGame(String gameName)
	{
		//for loop searching the inventory list for the game
		for(Game game : inventoryList)
		{
			//selection structure checking if the game name matches any in the inventory
			if(gameName.equalsIgnoreCase(game.getGameName()))
			{
				return game;
			}//end game name found, so return the game object
		}//end for loop searching for the game 

		//game not found so return null
		return null;
	}//end findGame method

	//the startNewOrder method creates a new order, loads it into the pending order list, and returns the order ID
	public int startNewOrder()
	{
		//create a new order object
		CustOrder order = new CustOrder();
		//add the order to the pending order list
		pendingOrderList.add(order);

		//return the order ID number
		return order.getOrderID();
	}//end startNewOrder method

	//addGameToOrder method adds a game to the current pending order 
	public void addGameToOrder(int orderID, int daysRented, String gameName)
	{
		//find the order by the ID
		CustOrder pendingOrder = findPendingOrderByID(orderID);

		//search the game inventory for the game by the name (using findGame method)
		Game game = findGame(gameName);

		//add the game to the order (if the order exists and the game is available)
		if(pendingOrder != null && game != null)
		{
			//set the number of days rented to the game object
			game.setDaysRented(daysRented);
			//add the game to the order
			pendingOrder.addGame(game);

		}//end game is available
	}//end addGameToOrder method

	//placeOrder method takes the pending order and adds it to the processedOrderList
	public void placeOrder(int orderID)
	{
		//declare a local variable to hold the names of the rented games
		ArrayList<String> rentedGamesNames = new ArrayList<String>();

		//search the pending order list for the ID 
		CustOrder confirmedOrder = findPendingOrderByID(orderID);

		//add the confirmed order to the processed order list (if it exists)
		if(confirmedOrder != null)
		{
			//place the order in the processed order list
			processedOrderList.add(confirmedOrder);

			//get the names of every game in the order and add it to the rentedGamesNames list
			for (Game rentedGame : confirmedOrder.getOrderGameList())
			{
				rentedGamesNames.add(rentedGame.getGameName());
			}//end for loop cycling the games rented to get the names

			//account for the game being removed in the inventory
			//cycle the inventory list
			for (Game inventoryGame: inventoryList)
			{
				//for loop cycling the rentedGamesNames list
				for(String gameName : rentedGamesNames)
				{
					//for every game name that matches in the inventory, remove the game
					if(inventoryGame.getGameName().equalsIgnoreCase(gameName))
					{
						//pull the game from the inventory
						inventoryGame.gameRented();
					}//end the game is pulled from the inventory
				}//end for loop cycling the rentedGamesNames
			}//end for loop cycling the inventory list to account for a game being rented
		}//end order exists, so the order is placed
	}//end placeOrder method

	//cancelPendingOrder method deletes the pending order based upon its orderID
	public void cancelPendingOrder(int orderID)
	{
		//search for the pending order in the pending order list
		CustOrder cancelledOrder = findPendingOrderByID(orderID);

		//if the order exists, remove it from the list
		if(cancelledOrder != null)
		{
			//remove the item from the list
			pendingOrderList.remove(cancelledOrder);

		}//end order exists and is removed
	}//end cancelPendingOrder

	//the findPendingOrderByID searches the pending order list and returns the order (if found)
	public CustOrder findPendingOrderByID(int orderID) {

		//for loop searching the pending order list 
		for(CustOrder pendingOrder : pendingOrderList)
		{
			//selection structure checking if the order matches the ID
			if(orderID == pendingOrder.getOrderID())
			{
				return pendingOrder;
			}//end order has been found
		}//end 

		//return not found
		return null;
	}//end findPendingOrderByID

	//the findOrderByID searches the processed order list and returns the order (if found)
	public CustOrder findOrderByID(int orderID) 
	{
		//for loop searching the pending order list 
		for(CustOrder order : processedOrderList)
		{
			//selection structure checking if the order matches the ID
			if(orderID == order.getOrderID())
			{
				return order;
			}//end order has been found
		}//end 

		//return not found
		return null;
	}//end findOrderByID

	//the getInventoryList returns the inventoryList
	public ArrayList<Game> getInventoryList()
	{
		return inventoryList;
	}//end getInventoryList

	//the getInventoryList overloaded method returns the inventoryList based upon the type
	public ArrayList<Game> getInventoryList(String gameType)
	{
		//declare local list to store the items found
		ArrayList<Game> sortedGamesList = new ArrayList<>();

		//for loop cycling the inventory list
		for(Game game : inventoryList)
		{
			//selection structure checking for if the game type matches the game type
			if(gameType.equalsIgnoreCase(game.getGameType()))
			{
				sortedGamesList.add(game);
			}//end they match, so add to the sorted list
		}//end loop cycling the inventory list

		//return the sorted games list
		return sortedGamesList;
	}//end getInventoryList

	//getGameByName returns the game from the inventory list based upon its name
	public Game getGameByName(String gameName)
	{
		return findGame(gameName);
	}//end getGameByName

	//getOrderList returns the processed orders
	public ArrayList<CustOrder> getOrderList()
	{
		return processedOrderList;
	}//end getOrderList
}//end Store class
