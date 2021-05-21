/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the CustOrder class is to return the information for an order and calculates the total. 
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.model;

import java.util.ArrayList;

public class CustOrder 
{
	//create class variables
	
	private static int nextOrderID = 10000;
	private int currentOrderID;
	private ArrayList<Game> orderGameList = new ArrayList<>();
	private double orderTotal;

	//the CustOrder constructor increments the order ID
	public CustOrder()
	{
		currentOrderID = nextOrderID++;
	}//end CustOrder constructor
	
	//addGame adds a game to the order 
	void addGame(Game game)
	{
		//add the game to the ArrayList
		orderGameList.add(game);
		
		//get the individual rate of the game being rented and add it to the total
		orderTotal += game.getGameCost();

	}//end addGame method
	
	//getOrderID returns the current order ID
	public int getOrderID()
	{
		return currentOrderID;
		
	}//end getOrderID
	
	//getNumberOfGamesRented returns the number of games rented
	public int getNumberOfGamesRented()
	{
		int gamesRentedQty = orderGameList.size();
		return gamesRentedQty;
	}//end getNumberOfGamesRented
	
	
	//getOrderCost returns the total cost of the order 
	public double getOrderTotal()
	{
		return orderTotal;
	}//end getOrderCost
	
	//getOrderGamesList returns the list of games that were ordered
	public ArrayList<Game> getOrderGameList()
	{
		return orderGameList;
	}//end getOrderGamesList

}//end CustOrder
