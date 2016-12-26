package org.wjanaszek.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.wjanaszek.model.Enemy;
import org.wjanaszek.model.Model;
import org.wjanaszek.model.Player;
import org.wjanaszek.model.ScorePoint;
import org.wjanaszek.model.Wall;

/**
 * Klasa reprezentuj�ca cz�� 'View' z architektury MVC.
 * @author Wojciech Janaszek
 * @category View
 */
public class View extends JPanel {
	
	private static Model model;
	
	private static JPanel panel;
	private static JFrame window;
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final String TITLE = "PAC_MAN";
	
	private BufferStrategy bs;
	
	/**
	 * Konstruktor tworz�cy widok. Najpierw wywo�uje konstruktor domy�lny dla elementu JPanel. 
	 * Nast�pnie przypisuje do pola 'model' przekazany jako parametr odno�nik na model aplikacji. 
	 * Potem ustawia rozmiar okna, oraz dodaje komponent do okna.
	 * @param model model ca�ej aplikacji
	 */
	public View(Model model)	{
		//super();
		this.panel = new JPanel();
		this.model = model;
		Dimension dimension = new Dimension(WIDTH, HEIGHT);
		
		window = new JFrame(TITLE);
		
		window.setPreferredSize(dimension);
		window.setMinimumSize(dimension);
		window.setMaximumSize(dimension);
		window.add(panel);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	/**
	 * Metoda renderowania widoku ca�ej planszy na ekranie. 
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void render(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, Model.getWidth(), Model.getHeight());
		renderTiles(model.getLevel().getWalls(), g);
		renderPoints(model.getLevel().getPoints(), g);
		renderPlayer(model.getPlayer(), g);
		renderEnemy(model.getLevel().getEnemies(), g);
		renderScore(model.getScore(), g);
		renderHighScore(model.getHighScore(), g);
	
		g.dispose();
	}

	/**
	 * Metoda do wy�wietlania obecnego wyniku gracza w prawym dolnym rogu okna.
	 * @param score wy�wietlana warto��
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderScore(int score, Graphics g)	{
		g.setColor(Color.white);
		g.drawString("WYNIK: " + score, 570, 470);
		g.drawString("Nacisnij 'q', aby wyjsc z gry, lub 'SPACE', aby wlaczyc pauze", 190, 470);
	}

	/**
	 * Metoda do wy�wietlania najlepszego wyniku w lewym dolnym rogu okna.
	 * @param highScore rekord
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderHighScore(int highScore, Graphics g){
		g.setColor(Color.white);
		g.drawString("NAJLEPSZY WYNIK: " + highScore, 20, 470);
	}

	/**
	 * Metoda do wy�wietlania nowego najlepszego wyniku po zako�czeniu rozgrywki, w przypadku osi�gni�cia
	 * nowego rekordu.
	 * @param newHighScore nowy rekord
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderNewHighScore(int newHighScore, Graphics g){
		g.setColor(Color.gray);
		g.drawRect(200, 100, 320, 80);
		g.fillRect(200, 100, 320, 80);
		g.setColor(Color.black);
		g.drawString("--> Nowy najlepszy wynik: " + model.getHighScore(), 250, 150);
	}

	/**
	 * Metoda do wy�wietlenia okna dialogowego po zako�czeniu rozgrywki, zawieraj�cego pytanie o rozpocz�cie
	 * nowej rozgrywki, lub zako�czenie dzia�ania aplikacji (wyj�cie z gry).
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderNewGameQuestion(Graphics g){
		g.setColor(Color.gray);
		g.drawRect(200, 160, 320, 100);
		g.fillRect(200, 160, 320, 100);
		g.setColor(Color.black);
		g.drawString("--> Nacisnij 'space', aby rozpoczac nowa gre", 250, Model.getHeight()/2 - 45);
		g.setColor(Color.yellow);
		g.drawString("--> Nacisnij 'q', aby wyjsc z gry", 250, Model.getHeight()/2);
	}

	/**
	 * Pomocnicza metoda prywatna do renderowania gracza (pacmana) na planszy w oknie.
	 * @param player pacman z modelu, zawieraj�cy wsp�rz�dne, w kt�rych ma zosta� namalowany obrazek gracza.
	 * @param g bufor, po kt�rym rysujemy
	 */
	private void renderPlayer(Player player, Graphics g)	{
		g.drawImage(player.getImage(player.getDirection()), player.getX(), player.getY(), this);
	}

	/**
	 * Pomocnicza metoda prywatna do renderowania przeciwnik�w (duch�w) na planszy w oknie.
	 * @param enemies lista przeciwnik�w (duch�w) ich wsp�rz�dnymi z modelu
	 * @param g bufor, po kt�rym rysujemy
	 */
	private void renderEnemy(List<Enemy> enemies, Graphics g)	{
		for(int i = 0; i < enemies.size(); i++){
			g.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), this);
		}
	}

	/**
	 * Pomocnicza metoda prywatna do renderowania �cian na planszy w oknie.
	 * @param tiles tablica kwadrat�w �cian z ich wsp�rz�dnymi z modelu
	 * @param g bufor, po kt�rym rysujemy
	 */
	private void renderTiles(Wall[][] walls, Graphics g)	{
		for(int x = 0; x < model.getLevel().getWidth(); x++){
			for(int y = 0; y < model.getLevel().getHeight(); y++){
				if(walls[x][y] != null){
					g.drawImage(walls[x][y].getImage(), walls[x][y].x, walls[x][y].y, this);
				}
			}
		}
	}

	/**
	 * Pomocnicza metoda prywatna do renderowania punkt�w na planszy w oknie.
	 * @param points lista punkt�w z ich wsp�rz�dnymi z modelu
	 * @param g bufor, po kt�rym rysujemy
	 */
	private void renderPoints(List<ScorePoint> points, Graphics g){
		for(int i = 0; i < points.size(); i++)	{
			g.drawImage(points.get(i).getImage(), points.get(i).x, points.get(i).y, this);
		}
	}
	
	/**
	 * Pomocnicza metoda do renderowania ekranu z pauza podczas gry.
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderPause(Graphics g){
		g.setColor(Color.gray);
		g.drawRect(150, 140, 320, 120);
		g.fillRect(150, 140, 320, 120);
		g.setColor(Color.black);
		g.drawString("PAUSE", 150, 170);
		g.drawString("--> Nacisnij 'space', aby wrocic do gry", 180, 200);
		g.drawString("--> Nacisnij 'q', aby wyjsc z gry", 180, 230);
	}
	
	/**
	 * Metoda do mo�liwo�ci ingerowania w widok przez controller.
	 * @return prywatne pole window typu JFrame (okno aplikacji)
	 * @see JFrame
	 */
	public JFrame getWindow()	{
		return window;
	}
}
