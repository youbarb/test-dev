package fr.orsys.banque.util;

import java.sql.SQLException;
import java.util.Scanner;

import org.h2.tools.Server;

public class H2Server {
	static Server server = null;
	static String state ;
	
	static {
		try {
			//the default port of TCP Server : 9092) 
			// URL --> jdbc:h2:tcp://localhost:9092/DBNAME
			server = Server.createTcpServer(new String[] {"-tcpAllowOthers"});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	static public void main(String[] args) {
		
		Scanner clavier = new Scanner(System.in);		
		start();
		while(true){
			String oppositeState = "start".equals(state)?"stop":"start";
			System.out.print("Enter action server ["+oppositeState+ "-quit] : ");
			state=clavier.next();
			if("start".equals(state))
				start();
			else if("stop".equals(state))
				stop();
			else if("quit".equals(state))
				quit();
		}
	
	}
	
	private static void quit() {
		if("start".equals(state))
			stop();
		System.exit(1);
		
	}

	static private void start() {
		try {
			server.start();
			state="start";
			System.out.println("Serveur H2 d�marrer url=" + server.getURL());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static private void stop() {
			server.stop();
			state="stop";
			System.out.println("Serveur H2 arret� url=" + server.getURL());
	}

}
