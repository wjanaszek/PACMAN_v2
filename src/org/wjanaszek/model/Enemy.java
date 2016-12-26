package org.wjanaszek.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca model ducha-przeciwnika.
 * @author Wojciech Janaszek
 * @category Model
 */
public class Enemy extends MovingObject {
	
	// AI part:
	private int random = 0;
	private int state = random;
	
	private int dir;
	private int lastDir = - 1;
	private final static byte DIR_RIGHT = 0;
	private final static byte DIR_LEFT = 1;
	private final static byte DIR_UP = 2;
	private final static byte DIR_DOWN = 3;
	
	public Random r;
	
	private final int SPEED = 4;
	
	private Model model;
	
	//Look part:
	private Image image;
	
	/**
	 * Konstruktor obiektu ducha. Wywo�uje konstruktor z klasy bazowej MovingObject o zadanych wsp�rz�dnych,
	 * nast�pnie ustala losowo pierwszy kierunek ruchu ducha, a na ko�cu �aduje wy�wietlan� tekstur� obiektu.  
	 * @param x wsp�rz�dna X
	 * @param y wsp�rz�dna Y
	 * @param model referencja do modelu
	 */
	public Enemy(int x, int y, Model model)	{
		super(x, y);
		this.model = model;
		r = new Random();
		dir = r.nextInt(4);
		loadImage("/org/wjanaszek/model/textures/ghost.png");
	}
	
	/**
	 * Zwraca obrazek danego ducha.
	 * @return obrazek ducha
	 * @see Image
	 */
	public Image getImage()	{
		return image;
	}
	
	/**
	 * �aduje obrazek ducha z pliku znajduj�cego si� pod okre�lon� �cie�k�.
	 * @param path �cie�ka do pliku z obrazkiem
	 * @see ImageIcon
	 */
	private void loadImage(String path)	{
		ImageIcon ii = new ImageIcon(getClass().getResource(path));
		image = ii.getImage();
	}
	
	/**
	 * Metoda do poruszania si� duchem.
	 */
	public void move()	{
		if(state == random){
			if(dir == DIR_RIGHT)	{
				if(isMovePossibility(x + SPEED, y))	{
					x += SPEED;
				}
				else	{
					dir = r.nextInt(4);
				}
			}
			else if(dir == DIR_LEFT){
				if(isMovePossibility(x - SPEED, y))	{
					x -= SPEED;
				}
				else	{
					dir = r.nextInt(4);
				}
			}
			else if(dir == DIR_DOWN)	{
				if(isMovePossibility(x, y + SPEED))	{
					y += SPEED;
				}
				else	{
					dir = r.nextInt(4);
				}
			}
			else if(dir == DIR_UP){
				if(isMovePossibility(x, y - SPEED))	{
					y -= SPEED;
				}
				else	{
					dir = r.nextInt(4);
				}
			}	
		}
	}
	
	/**
	 * Metoda sprawdzaj�ca mo�liwo�� ruchu danego ducha. Tworzy tymczasowy prostok�t, aby skorzysta� z metody
	 * interests, sprawdzaj�c� kolizj�.
	 * @param dx wsp�rz�dna X, do kt�rej chcemy si� przemie�ci�
	 * @param dy wsp�rz�dna Y, do kt�rej chcemy si� przemie�ci�
	 * @return true, gdy jest to mo�liwe, false w p.p.
	 * @see intersects
	 */
	private boolean isMovePossibility(int dx, int dy)	{
		Rectangle rect = new Rectangle(dx, dy, 32, 32);
		Level tmpLevl = model.getLevel();
		
		for(int i = 0; i < tmpLevl.getWalls().length; i++){
			for(int j = 0; j < tmpLevl.getWalls()[0].length; j++){
				if(tmpLevl.getWalls()[i][j] != null){
					if(rect.intersects(tmpLevl.getWalls()[i][j]))	{
						return false;
					}
				}
			}
		}
		
		return true;
	}
}
