package org.wjanaszek.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Klasa reprezentuj�ca map� i wszystkie zwi�zane z ni� elementy, jak duchy, 
 * czy �ciany.
 */
public class Level {

	private int width;
	private int height;
	private Wall[][] walls;
	private List<ScorePoint> points;
	private List<Enemy> enemies;
	
	private Model model;
	
	/**
	 * Konstruktor - w zale�no�ci od wczytanej mapy w formacie .png rozmieszcza w modelu gry �ciany,
	 * duchy i gracza. Plansza musi by� wype�niona wyszczeg�lnionymi kolorami, w przeciwnym przypadku mo�e zosta� 
	 * utworzona nieprawid�owo. 
	 * @param path - �cie�ka do pliku z obrazkiem planszy
	 * @param model - referencja do modelu
	 */
	public Level(String path, Model model){
		this.model = model;
		
		boolean isPlayer = false;
		points = new ArrayList<>();
		enemies = new ArrayList<>();
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width = map.getWidth();
			this.height = map.getHeight();
			walls = new Wall[width][height];
			int[] pixels = new int[width * height];
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)	{
				for(int yy = 0; yy < height; yy++){
					int val = pixels[xx + (yy * width)];
					if(val == 0xFF000000){
						// wall
						walls[xx][yy] = new Wall(xx*32, yy*32);
					}
					else if(val == 0xFF0000FF && !isPlayer){
						// player
						model.getPlayer().x = xx*32;
						model.getPlayer().y = yy*32;
						isPlayer = true;
					}
					else if(val == 0xFFFF0000){
						// ghosts
						enemies.add(new Enemy(xx*32, yy*32, model));
					}
					else	{
						// points
						points.add(new ScorePoint(xx*32, yy*32));
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda do aktualizacji modelu.
	 */
	public void update()	{
		for(int i = 0; i < model.getLevel().getPoints().size(); i++){
			if(model.getPlayer().intersects(model.getLevel().getPoints().get(i)))	{
				model.incScore();
				model.getLevel().getPoints().remove(i);
				break;
			}
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).move();
		}
	}
	
	/**
	 * Metoda do zwracania szeroko�ci planszy.
	 * @return szeroko�� mapy
	 */
	public int getWidth()	{
		return width;
	}
	
	/**
	 * Metoda do zwracania wysoko�ci planszy.
	 * @return wysoko�� mapy
	 */
	public int getHeight()	{
		return height;
	}
	
	/**
	 * Metoda do zwracania tablicy �cian z planszy.
	 * @return tablic� �cian znajduj�cych si� na planszy
	 */
	public Wall[][] getWalls(){
		return walls;
	}
	
	/**
	 * Metoda do zwracania listy punkt�w z planszy.
	 * @return list� punkt�w znajduj�cych si� na planszy
	 */
	public List<ScorePoint> getPoints()	{
		return points;
	}
	
	/**
	 * Metoda do zwracania listy duch�w (przeciwnik�w) z planszy.
	 * @return list� duch�w znajduj�cych si� na planszy
	 */
	public List<Enemy> getEnemies()	{
		return enemies;
	}
}
