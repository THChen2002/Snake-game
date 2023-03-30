import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class GameFrame extends JFrame{
	//GamePanel game;
	GameFrame(){
		//game = new GamePanel();	
		//this.add(game);
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		//game.restart_jb.addActionListener(this);
	}
	/*
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == game.restart_jb) {
			game.restart_jb.setVisible(false);
			System.out.println("click");
			this.remove(game);
			game = new GamePanel();
			this.add(game);
			//SwingUtilities.updateComponentTreeUI(this);
			//game.repaint();
		}
	}
	*/
}