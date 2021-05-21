/**
 * The purpose of the FinalProject is to develop an order processing system for a video game rental store that
 * allows the user to enter in basic orders, prints receipts for each order, and maintains the inventory count. 
 * The purpose of the Game class is to return the information for a single game object. 
 * @author Jeremy Hunton
 */
package edu.tridenttech.cpt237.Hunton.model;

public class Game 
{
	//declare class variables
	private final static int ONE_GAME = 1;
	private final double RENTAL_RATE = 2.00;
	private int qty;
	private String gameType;
	private String gameName;
	private int daysRented;
	
	//the Game constructor passes the args for the qty, type of game, and name of the game
	public Game(int qty, String gameType, String gameName)
	{
		//assign to class variables
		this.qty = qty;
		this.gameType = gameType;
		this.gameName = gameName;
		
	}//end Game constructor
	
	//the setDaysRented method assigns the days a game is rented
	void setDaysRented(int daysRented) 
	{
		this.daysRented = daysRented;
		
	}//end setDays Rented
	
	//the gameRented method subtracts one game from the game inventory
	void gameRented()
	{
		qty -= ONE_GAME;
	}//end gameRented method
	
	//the getQty method returns the quantity in stock of the game
	public int getQty()
	{
		return qty;
	}//end getQty
	
	//the getGameType returns the game type
	public String getGameType()
	{
		return gameType;
	}//end getGameType
	
	//the getGameName returns the game name
	public String getGameName()
	{
		return gameName;
	}//end getGameName
	
	//the getRentalDays returns the number of rental days
	public int getRentalDays()
	{
		return daysRented;
	}//end getRentalDays
	
	//the getGameCost returns the rental rate times by the days rented
	public double getGameCost()
	{
		return RENTAL_RATE * daysRented;
	}//end getGameCost
}//end Game class
