package TwitchBots;
import org.jibble.pircbot.*;
import java.sql.*;

public class BOTFerrous extends PircBot 
{
	//protected Bookie billy;
	public Bookie billy;
	private Connection conn;
	public BOTFerrous()
	{
		billy = new Bookie();
		this.setName("BOTFerrous");
		
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/Documents/java se workspace/BOTFerrous/database",
					"BOTFerrous", "robutt");
			Statement stmnt = conn.createStatement();
			stmnt.execute("CREATE TABLE IF NOT EXISTS Users" +
					"(" +
					"uname varchar(255) NOT NULL," +
					"money float NOT NULL," +
					"PRIMARY KEY (uname) " +
					")");
			stmnt.close();
			if (conn != null)
				conn.close();
			conn = null;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void onDisconnect()
	{
		try {
			if (conn != null)
				conn.close();
			conn = null;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		if (message.equalsIgnoreCase("stop it billy"))
		{
			sendMessage(channel, "NO YOU STOP IT " + sender);
		}
	}
}
