package ircposter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.jibble.pircbot.PircBot;

/**
 * IrcPoster
 * 
 * Starts a server socket and listens for clients
 * that wants to post something to the channel this
 * bot is connected to
 * 
 * @author Vegard Løkken
 */
public class IrcPoster {
	
	private IRC irc;
	
	public IrcPoster(int listeningPort, String server, int port, String nick,
					String channel, String password)
					throws Exception {
		
		ServerSocket socket = new ServerSocket(listeningPort);
		
		irc = new IRC(nick);
		irc.connect(server, port);
		
		if (password != null) {
			irc.joinChannel(channel, password);
		} else {
			irc.joinChannel(channel);
		}
		
		while (true) {
			Socket client = socket.accept();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String msg = br.readLine();
			
			irc.sendNotice(channel, msg);
			
			br.close();
		}
		
	}

	public static void main(String[] args) throws Exception {
		if (args.length >= 5) {
			
			int listeningPort = Integer.parseInt(args[0]);
			String server = args[1];
			int port = Integer.parseInt(args[2]);
			String nick = args[3];
			String channel = args[4];
			String password = args.length >= 6 ? args[5] : null;
			
			new IrcPoster(listeningPort, server, port, nick, channel, password);
			
		} else {
			System.out.println("Usage: java -jar IrcPoster.jar "
							+ "<listening port> <server> <port> <nick> <channel> [password]");
			System.exit(1);
		}
	}
	
	private class IRC extends PircBot {
		public IRC(String nick) {
			setName(nick);
			setLogin(nick);
		}
	}
}
