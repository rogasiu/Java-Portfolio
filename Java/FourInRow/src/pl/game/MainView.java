package pl.game;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.SpringLayout;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import javax.swing.JLabel;
import java.awt.Font;

public class MainView {

	private JFrame frame;

	class GamePanel extends JPanel{
		private static final long serialVersionUID = -6988748393665066294L;
		class Position{
			int x;
			int y;
			public Position(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			public int getX() {
				return x;
			}
			public void setX(int x) {
				this.x = x;
			}
			public int getY() {
				return y;
			}
			public void setY(int y) {
				this.y = y;
			}
			
		}
		final int xSize=7;
		final int ySize=5;
		int circleSize = 40;
		int circleBetweenSpace = 50;
		int arrowPosition = 0;
		AtomicBoolean stillPlaying = new AtomicBoolean(true);
		GameField winner = GameField.NONE;
		GameField player = GameField.RED;
		
		private PublishSubject<Position> playerMoveSubject = PublishSubject.create();
		
		void setStillPlaying(boolean b)
		{
			stillPlaying.set(b);
			this.repaint();
		}
		boolean getStillPlaying()
		{
			return stillPlaying.get();
		}
		
		GameField playZone[][] = new GameField[7][5];

		boolean checkVerticalWin(Position pos)
		{
			GameField playerWhoMoves = playZone[pos.getX()][pos.getY()];
			int fillCounter=1;//1 bo ten srodkowy juz mamy i go nie liczymy
			//sprawdz pionowo
			for(int y=pos.getY()-1; y>=0; y--)
			{
				if(playZone[pos.getX()][y] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			for(int y=pos.getY()+1; y<ySize; y++)
			{
				if(playZone[pos.getX()][y] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			return fillCounter>=4;
		}
		boolean checkHorizontalWin(Position pos)
		{
			GameField playerWhoMoves = playZone[pos.getX()][pos.getY()];
			int fillCounter=1;
			for(int x=pos.getX()-1; x>=0; x--)
			{
				if(playZone[x][pos.getY()] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			for(int x=pos.getX()+1; x<xSize; x++)
			{
				if(playZone[x][pos.getY()] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			return fillCounter>=4;
		}
		boolean checkRightSlashWin(Position pos)// -> /
		{
			GameField playerWhoMoves = playZone[pos.getX()][pos.getY()];
			int fillCounter=1;
			for(int czynnik=1; pos.getX()+czynnik <xSize && pos.getY()-czynnik>=0; czynnik++)//dodawanie czynnika
			{
				if(playZone[pos.getX()+czynnik][pos.getY()-czynnik] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			for(int czynnik=1; pos.getX()-czynnik >=0 && pos.getY()+czynnik<ySize; czynnik++)//odejmiwane czynnika
			{
				if(playZone[pos.getX()-czynnik][pos.getY()+czynnik] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			return fillCounter>=4;
		}
		boolean checkLeftSlashWin(Position pos)// -> \
		{
			GameField playerWhoMoves = playZone[pos.getX()][pos.getY()];
			int fillCounter=1;
			for(int czynnik=1; pos.getX()-czynnik >=0 && pos.getY()-czynnik>=0; czynnik++)//odejmiwane czynnika
			{
				if(playZone[pos.getX()-czynnik][pos.getY()-czynnik] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			for(int czynnik=1; pos.getX()+czynnik <xSize && pos.getY()+czynnik<ySize; czynnik++)//odejmiwane czynnika
			{
				if(playZone[pos.getX()+czynnik][pos.getY()+czynnik] == playerWhoMoves)
				{
					fillCounter++;
				}else {
					break;
				}
			}
			return fillCounter>=4;
		}
		GamePanel(Observable<KeyEvent> frameKeyListener){
			
			Random r = new Random();
			if(r.nextInt(2)==0)
			{
				player = GameField.RED;
			}else {
				player = GameField.BLUE;
			}
			
			
			for(int x=0; x<xSize; x++)
			{
				for(int y=0; y<ySize; y++)
				{
					playZone[x][y] = GameField.NONE;
				}
			}

			frameKeyListener
			.takeWhile(x-> stillPlaying.get()==true)
			.subscribe(o -> {
				if (o.getKeyCode() == KeyEvent.VK_RIGHT) {
					for(int i=arrowPosition+1; i<xSize; i++)
					{
						if(hasColumnFreeSpace(i))
						{
							arrowPosition=i;
							break;
						}
					}
		        }else if (o.getKeyCode() == KeyEvent.VK_LEFT)
		        {
		        	for(int i=arrowPosition-1; i>=0; i--)
					{
						if(hasColumnFreeSpace(i))
						{
							arrowPosition=i;
							break;
						}
					}
		        }else if (o.getKeyCode() == KeyEvent.VK_ENTER)
		        {
		        	addPlayerMove();
		        }
				checkDraw();
				this.repaint();
			});
			
			playerMoveSubject.subscribe(pos->{
				//System.out.println("Nowa pozycja "+pos.getX()+":"+pos.getY());				
				if(checkVerticalWin(pos) || checkHorizontalWin(pos) || checkRightSlashWin(pos) || checkLeftSlashWin(pos))
				{
					winner = playZone[pos.getX()][pos.getY()];
					stillPlaying.set(false);
				}
			});
		}
		
		boolean hasColumnFreeSpace(int x)
		{
			if(x>=xSize && x<0)
				return false;
			for(int i=ySize-1; i>=0; i--)
			{
				if(playZone[x][i] == GameField.NONE)
					return true;
			}
			return false;
		}
		void changePlayer()
		{
			if(player == GameField.RED)
				player = GameField.BLUE;
			else
				player = GameField.RED;
		}
		void addPlayerMove()
		{
			for(int i=ySize-1; i>=0; i--)
			{
				if(playZone[arrowPosition][i] == GameField.NONE)
				{
					playZone[arrowPosition][i] = player;
					playerMoveSubject.onNext(new Position(arrowPosition,i));
					changePlayer();
					break;
				}
			}
		}
		void checkDraw() {
			int check = 0;
			for (int i = 0; i < xSize; i++) {
				for(int j = 0; j < ySize; j++) {
					if(playZone[i][j] == GameField.NONE) {
						check++;
					}
				}
			}
			if(check == 0) {
				stillPlaying.set(false);
			}
		}
		void drawArrow(Graphics g, Color color, int x, int y)
		{
			
			Polygon p = new Polygon();
			int first = 5;
			int second = 15;
			int third = 30;
			p.addPoint(x, y);//tu wskazuje strzalka
			p.addPoint(x+second, y-second);
			p.addPoint(x+first, y-second);
			p.addPoint(x+first, y-third);
			p.addPoint(x-first, y-third);
			p.addPoint(x-first, y-second);
			p.addPoint(x-second, y-second);
			g.setColor(color);
			g.drawPolygon(p);
		    g.fillPolygon(p);
			
		}
		
		@Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //drawArrow(g,Color.RED, 100,100);
            
            
        	g.setColor(Color.BLACK);
            g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);//ramka
            int startX = (int) (this.getWidth()/2 - 3.5*circleSize - 0.8*circleBetweenSpace  ) ;
            int startY = this.getHeight()-250;
            
            Color arrowColor;
            if(player == GameField.RED)
            	arrowColor = Color.RED;
            else
            	arrowColor = Color.BLUE;
            
            drawArrow(g,arrowColor,startX+arrowPosition*circleBetweenSpace+circleSize/2, startY-10);
            for(int x=0; x<xSize; x++)
			{
				for(int y=0; y<ySize; y++)
				{
					switch(playZone[x][y])
					{
					case RED: 
						g.setColor(Color.RED);
			            g.fillOval(startX+x*circleBetweenSpace, startY+y*circleBetweenSpace, circleSize, circleSize);
						break;
					case BLUE: 
						g.setColor(Color.BLUE);
			            g.fillOval(startX+x*circleBetweenSpace, startY+y*circleBetweenSpace, circleSize, circleSize);
						break;
					default:
						g.setColor(Color.GRAY);
			            g.drawOval(startX+x*circleBetweenSpace, startY+y*circleBetweenSpace, circleSize, circleSize);
						break;
						
					}
				}
			}
            if(!stillPlaying.get()) {
            	g.setColor(Color.BLACK);
            	String koniecStr = "Koniec Gry! ";
            	switch(winner)
				{
				case RED: 
					koniecStr+="Czerwony wygrał!";
					break;
				case BLUE: 
					koniecStr+="Niebieski wygrał!";
					break;
				default:
					koniecStr+="Nikt nie wygrał!";
					break;
					
				}
            	g.drawString(koniecStr, 100, 50);
            }
			
        }
		
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	@SuppressWarnings("deprecation")
	Observable<KeyEvent> observerFromFrame(JFrame f)
	{
		return Observable.create(emitter -> {
			f.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					emitter.onNext(arg0);
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		});
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//System.out.println("ABC ABC ABC");
		frame = new JFrame();
		frame.setBounds(100, 100, 646, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Observable<KeyEvent> frameKeyListener = observerFromFrame(frame);
				
		
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		GamePanel gamePanel = new GamePanel(frameKeyListener);
		sl_panel.putConstraint(SpringLayout.SOUTH, gamePanel, -36, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, gamePanel, -44, SpringLayout.EAST, panel);
		panel.add(gamePanel);
		
		JLabel lblNewLabel = new JLabel("Cztery w rzędzie");
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 50, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, gamePanel, 13, SpringLayout.SOUTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, gamePanel, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 32, SpringLayout.NORTH, panel);
		panel.add(lblNewLabel);
		
		JLabel timerLabel = new JLabel("New label");
		sl_panel.putConstraint(SpringLayout.NORTH, timerLabel, -7, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.EAST, timerLabel, -42, SpringLayout.EAST, panel);
		timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(timerLabel);
		
		
		long maxTime = 3 * 60;
		
		Observable<Long> timerObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
		.subscribeOn(Schedulers.newThread())
		.takeWhile(pred -> gamePanel.getStillPlaying())
		.takeWhile(pred -> maxTime-pred>=0)
		.doOnUnsubscribe(()->{
			System.out.println("Koniec");
			gamePanel.setStillPlaying(false);
		});
		
		timerObservable.subscribe(x -> {
			long timeLeft = maxTime-x;
			long min = timeLeft/60;
			long sec = timeLeft % 60;
			timerLabel.setText(String.format("%02d" , min)+":"+String.format("%02d" , sec));
		});
	}
}
