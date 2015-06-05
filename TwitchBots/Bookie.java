package TwitchBots;

import java.util.Hashtable;
import java.util.Set;

public class Bookie
{
	protected boolean lock;
	protected double pot;
	protected double startingAmount;
	protected double growthRate;
	protected Hashtable<String, Double> users;
	protected Hashtable<String, Hashtable<String, Double> > bets;
	
	public Bookie()
	{
		lock = true;
		pot = 0;
		startingAmount = 100;
		growthRate = 1;
		users = new Hashtable<String, Double>();
		bets = new Hashtable<String, Hashtable<String, Double> >();
	}
	
	public void unlockBets()
	{
		pot = 0;
		lock = false;
	}
	
	public void lockBets()
	{
		lock = true;
	}
	
	public void printUsers()
	{
		for (String key : users.keySet())
		{
			System.out.println(key + " has " + users.get(key).toString());
		}
	}
	public void distributeWinnings(String winningBet) throws Exception
	{
		if (!lock)
			throw new Exception("Attempted to payout when bets were not locked.");
		if (winningBet == null || !bets.containsKey(winningBet))
			throw new Exception("'" + winningBet + "'" + " is not a valid bet, cannot distribute winnings");
		
		double winningPot = 0;
		Hashtable<String, Double> winners = bets.get(winningBet);
		Set<String> winnersNames = winners.keySet();
		
		for (String key : winnersNames)
		{
			winningPot += winners.get(key);
		}
		
		if (winningPot == 0)
		{
			Set<String> betKeys = bets.keySet();
			int numUsers = 0;
			for (String betKey : betKeys)
			{
				numUsers += bets.get(betKey).size();
			}
			
			if (numUsers == 0)
				return;
			
			double payOut = pot/numUsers;
			for (String betKey : betKeys)
			{
				winnersNames = bets.get(betKey).keySet();
				for (String key : winnersNames)
				{
					Double prevValue = users.get(key);
					if (prevValue != null)
						users.put(key, payOut + prevValue);
				}
			}			
			return;
		}
		
		for (String key : winnersNames)
		{
			double odds = (pot - winningPot)/winningPot;
			Double prevValue = users.get(key);
			if (prevValue != null)
			{
				users.put(key, prevValue + (1 + odds)*winners.get(key));
			}
		}
	}
	
	public void addChoice(String choiceName) throws Exception
	{
		if (!lock)
		{
			throw new Exception("Bets need to be locked to add a new choice.");
		}
		
		bets.put(choiceName, new Hashtable<String, Double>());
	}
	
	public void clearChoices() throws Exception
	{
		if (!lock)
		{
			throw new Exception("Bets need to be locked to remove all choices.");			
		}
		bets.clear();
	}
	
	public void removeChoice(String choiceName) throws Exception
	{
		if (!lock)
		{
			throw new Exception("Bets need to be locked to remove a choices.");			
		}
		bets.remove(choiceName);
	}
	
	public void placeBet(double amount, String betChoice, String userName) throws Exception
	{
		if (lock)
		{
			throw new Exception("Bets are locked, cannot place a bet");
		}
		
		if (amount == 0)
		{
			throw new Exception("Betting amount needs to be over 0.");
		}
		
		if (!bets.containsKey(betChoice))
		{
			throw new Exception(betChoice + " is not a valid option.");
		}
		
		Hashtable<String, Double> choiceUsers = bets.get(betChoice);
		choiceUsers.put(userName, amount);
		
		if (users.containsKey(userName))
		{
			Double prevValue = users.get(userName);
			users.put(userName, prevValue - amount);
		}
		
		else
		{
			users.put(userName, startingAmount - amount);
		}
		pot = pot + amount;
	}
}
