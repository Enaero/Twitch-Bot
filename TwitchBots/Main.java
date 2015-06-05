package TwitchBots;

public class Main {

	public static void main(String[] args) {
		try {
			BOTFerrous bot = new BOTFerrous();
			bot.setVerbose(true);
			bot.connect("irc.twitch.tv", 6667, "oauth:rj0pa5c3eagfcxr0s72840vr8x89lf");
			bot.joinChannel("#azzydaffickal");
			
			bot.billy.lockBets();
			bot.billy.addChoice("red");
			bot.billy.addChoice("blue");
			bot.billy.unlockBets();
			bot.billy.placeBet(100, "blue", "Nick");
			bot.billy.placeBet(300, "blue", "Sucker");
			bot.billy.placeBet(1000, "red", "RichNikka");
			bot.billy.lockBets();
			bot.billy.distributeWinnings("blue");
			bot.billy.printUsers();
			int input = System.in.read();
			while (input != 'q')
			{
				input = System.in.read();
			}
			
			bot.partChannel("#azzydaffickal");
			bot.disconnect();
			bot = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
