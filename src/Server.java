import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 
 * @author devenbhooshan
 * Server of the Tic TAC Toe LAN Game using socket programming
 */
public class Server {
	
	static ServerThreader player1;
	static ServerThreader player2;
	public static void main(String[] args) throws IOException {
		ServerSocket serversocket=new ServerSocket(6554);
			player1=new ServerThreader(serversocket.accept(),1);
			player2=new ServerThreader(serversocket.accept(),2);
			player1.start();
			player2.start();
	}

}
class ServerThreader extends Thread{
	int playerId;
	Socket player;
	BufferedReader input;
    PrintWriter output;

	public ServerThreader(Socket player,int playerID) throws IOException{
		this.playerId=playerID;
		this.player=player;
		input=new BufferedReader(new InputStreamReader(player.getInputStream()));
		output=new PrintWriter(player.getOutputStream(),true);
		output.println("PID=1:ID="+playerID);
		
	}
	public  void run(){
		
		while(true){
			try {
				String text=input.readLine();
				
				if(playerId==1){
					System.out.println("Player 1:"+text);
					Server.player2.output.println(text);
				}else { 
					System.out.println("Player 2:"+text); 
					Server.player1.output.println(text);  
					}
			} catch (IOException e) {

				e.printStackTrace();
				break;
			}
		}
	}
	
	
}