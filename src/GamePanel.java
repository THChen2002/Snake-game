import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.Random;
import java.util.TimerTask;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 750;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	int delay = 175;
	static final int LEVEL_INCREASE = 5;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int level = 1;
	int levelUpCondition = LEVEL_INCREASE;
	int applesEaten;
	int appleX;
	int appleY;
	int bombX[] = new int[UNIT_SIZE*UNIT_SIZE];
	int bombY[] = new int[UNIT_SIZE*UNIT_SIZE];
	char direction = 'R';
	boolean running = false;
	boolean bombHint = false;
	String apple_path = "images/apple.png";
	ImageIcon appleImage;
	String bomb_path = "images/bomb.png";
	ImageIcon bombImage;
	String bombHint_path = "images/bombHint.png";
	ImageIcon bombHintImage;
	JLabel appleImage_jl, bombImage_jl[], bombHintImage_jl[];
	JButton restart_jb;
	Timer timer;
	Random random;
	
	GamePanel(){
		initImage();
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void initImage() {
		appleImage = new ImageIcon(apple_path);
		appleImage_jl = new JLabel(appleImage);
		appleImage_jl.setOpaque(false);
        appleImage_jl.setVisible(false);
        this.add(appleImage_jl);
        
        bombImage = new ImageIcon(bomb_path);
        bombImage_jl = new JLabel[UNIT_SIZE*UNIT_SIZE];
		for(int i = 0; i < (UNIT_SIZE*UNIT_SIZE); i++) {
			bombImage_jl[i] = new JLabel(bombImage);
			bombImage_jl[i].setOpaque(false);
			bombImage_jl[i].setVisible(false);
			this.add(bombImage_jl[i]);
		}
		bombHintImage = new ImageIcon(bombHint_path);
        bombHintImage_jl = new JLabel[UNIT_SIZE*UNIT_SIZE];
		for(int i = 0; i < (UNIT_SIZE*UNIT_SIZE); i++) {
			bombHintImage_jl[i] = new JLabel(bombHintImage);
			bombHintImage_jl[i].setOpaque(false);
			bombHintImage_jl[i].setVisible(false);
			this.add(bombHintImage_jl[i]);
		}
		
        String restartbt_path = "images/restart_button.png"; 
    	ImageIcon restartbtImage = new ImageIcon(restartbt_path);
        restart_jb = new JButton(restartbtImage);
        restart_jb.setBounds(600, 350, 100, 50);
        restart_jb.addActionListener(this);         
        restart_jb.setVisible(false);
        this.add(restart_jb);
        
	}
	public void startGame() {
		newApple();
		newBomb();
		running = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			//g.setColor(Color.red);
			//g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			appleImage_jl.setBounds(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			bombHintImage_jl[applesEaten].setBounds(bombX[applesEaten], bombY[applesEaten], UNIT_SIZE, UNIT_SIZE);
			for(int i = 0; i <= applesEaten; i++) {
				bombHintImage_jl[i].setBounds(bombX[i], bombY[i], UNIT_SIZE, UNIT_SIZE);
				bombImage_jl[i].setBounds(bombX[i], bombY[i], UNIT_SIZE, UNIT_SIZE);
			}

			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten + "     Level: " + level, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten + "     Level: " + level))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void newApple(){
		do {
			appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
			appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		}while(!checkApplesPosition(appleX, appleY));
		appleImage_jl.setBounds(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        appleImage_jl.setVisible(true);
	}
	public boolean checkApplesPosition(int appleX, int appleY) {
		for(int i = 0; i <= applesEaten; i++) {
			if(appleX == bombX[i] && appleY == bombY[i]) {
				return false;
			}
		}
		return true;
	}
	public void newBomb(){
		do {
			bombX[applesEaten] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
			bombY[applesEaten] = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		} while(bombX[applesEaten] == appleX && bombY[applesEaten] == appleY);
		bombHintImage_jl[applesEaten].setBounds(bombX[applesEaten], bombY[applesEaten], UNIT_SIZE, UNIT_SIZE);
        bombHintImage_jl[applesEaten].setVisible(true);
        Timer t = new Timer(1500, new ActionListener() {       	  
        	  public void actionPerformed(ActionEvent e) {
        		  bombHintImage_jl[applesEaten].setVisible(false);
        		  bombImage_jl[applesEaten].setVisible(true);     		  
        	  }
        });
        t.setRepeats(false);
        t.start();
	}
	
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			if(applesEaten >= levelUpCondition) {
				levelUp();
			}
			newApple();
			newBomb();
		}
	}
	
	public void checkBomb() {
		for(int i = 0; i <= applesEaten; i++) {
			if((x[0] == bombX[i]) && (y[0] == bombY[i]) && bombImage_jl[i].isVisible()) {
				running = false;
			}
		}
	}
	public void levelUp() {
		level ++;
		levelUpCondition += LEVEL_INCREASE;
		delay -= 10;
		timer.stop();
		timer = new Timer(delay,this);
		timer.start();
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		
		restart_jb.setVisible(true);
		restart_jb.setBounds(600, 400, 100, 50);
		appleImage_jl.setVisible(false);
		for(int i = 0; i <= applesEaten; i++) {
			bombImage_jl[i].setVisible(false);
			bombHintImage_jl[i].setVisible(false);
		}
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten + "     Level: " + level, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten + "     Level: " + level))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkBomb();
			checkCollisions();
		}
		repaint();
		
		if(e.getSource() == restart_jb) {
			restart_jb.setVisible(false);
			new GameFrame();
		}
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}

		}
	}
}