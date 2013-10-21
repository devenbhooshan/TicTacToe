import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * 
 * @author devenbhooshan
 * Client of the Tic TAC Toe LAN Game using socket programming
 */
public class Client extends JFrame implements MouseListener{
	static JLabel message=new JLabel("Message Box");
	static JLabel game_move[]=new JLabel[10]; 
		public Client(){
			JPanel panel=new JPanel(new GridLayout(3, 3));
			
			for (int i = 1; i < game_panel.length; i++) {
				game_move[i]=new JLabel();
				game_panel[i]=new JPanel();
				game_panel[i].setPreferredSize(new Dimension(100, 100));
				panel.add(game_panel[i]);
				game_panel[i].setBorder(BorderFactory.createLineBorder(Color.black));
				game_panel[i].setName(Integer.toString(i));
				game_panel[i].addMouseListener(this);
				game_panel[i].add(game_move[i],"Center");
			}
			add(panel);
			add(message,"South");
			setTitle("Tic Tac Toe-"+playerId);
			setSize(300,300);
			setResizable(false);
			setVisible(true);
		}
	JPanel game_panel[]=new JPanel[10];
	static boolean chance=false;
	static Socket s;
	static BufferedReader in;
	static int  game[]=new int[10];
	static PrintWriter output;
	static int playerId;
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		s=new Socket("localhost", 6554);
		in=new BufferedReader(new InputStreamReader(s.getInputStream()));
		output=new PrintWriter(s.getOutputStream(),true);
		String text=in.readLine();
		System.out.println(text);
		playerId=Integer.parseInt(text.split(":")[1].split("=")[1]);
		new Client();
		
		int move=1;
		if(playerId==1){
			chance=true;
			message.setText("Your Move");
		}else message.setText("Your opponent move");
		while(true){
			System.out.println(chance);
			if(chance==false){
				text=in.readLine();
				System.out.println("Opponent Move:"+text);
				move=Integer.parseInt(text.split(":")[1].split("=")[1]);
				game[move]=playerId==1?2:1;
				chance=true;
				
				game_move[move].setText(playerId==1?"O":"X");
				if(checkgame(game)==0) message.setText("Your move");
				else if(checkgame(game)==-1){ message.setText("Game Drawn"); break;}
				else { 
					if(checkgame(game)==playerId) message.setText("You won") ; 
					else message.setText("You Loose") ;
					break;
					}
			}	
		}
		System.out.println("Dead end");
		in.close();
		output.close();
		s.close();

	}
	private static int  checkgame(int game2[]){
		if(game2[1]!=0 && game2[1]==game2[2] && game2[2] ==game2[3])
			 return game2[1];
		if(game2[4]!=0 && game2[4]==game2[5] && game2[5] ==game2[6])
			 return game2[4];
		if(game2[7]!=0 && game2[7]==game2[8] && game2[8] ==game2[9])
			 return game2[7];
		if(game2[1]!=0 && game2[1]==game2[4] && game2[4] ==game2[7])
			 return game2[1];
		if(game2[1]!=0 && game2[1]==game2[5] && game2[5] ==game2[9])
			 return game2[1];
		if(game2[2]!=0 && game2[2]==game2[5] && game2[5] ==game2[8])
			 return game2[2];
		if(game2[3]!=0 && game2[3]==game2[5] && game2[5] ==game2[7])
			 return game2[3];
		if(game2[3]!=0 && game2[3]==game2[6] && game2[6] ==game2[9])
			 return game2[3];
		for (int i = 1; i < game2.length; i++) {
			if(game2[i]==0) return 0; // game left
		}
		return -1;  // game draw;
		
	}
	@Override
	public synchronized void mouseClicked(MouseEvent ev) {
		if(chance){
			int move=Integer.parseInt(ev.getComponent().getName());
			if(game[move]==0){
				//System.out.println(move);
				chance=false;
				game[move]=playerId;
				output.println("PID=3:MOVE="+move);
				System.out.println("Your move: PID=3:MOVE="+move);
				game_move[move].setText(playerId==1?"X":"O");
				if(checkgame(game)==0) message.setText("Your opponent move");
				else if(checkgame(game)==-1){ message.setText("Game Drawn");}
				else { 
					if(checkgame(game)==playerId) message.setText("You won") ; 
					else message.setText("You Loose") ;
				}
			}
		}
		
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
