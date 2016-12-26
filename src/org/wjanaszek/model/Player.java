package org.wjanaszek.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentuj�ca model gracza (pacmana)
 * @author Wojciech Janaszek
 * @category Model
 */
public class Player extends MovingObject {
	
	private static final long serialVersionUID = 1L;
	
	public boolean right, left, up, down;
	
	private int dir;
	private int lastDir = -1;
	private final static byte DIR_RIGHT = 0;
	private final static byte DIR_LEFT = 1;
	private final static byte DIR_UP = 2;
	private final static byte DIR_DOWN = 3;
	
	private Random r;
	private final byte SPEED = 4;
	
	private static Image imageRight;
	private static Image imageLeft;
	private static Image imageUp;
	private static Image imageDown;
	
	private static Model model;
	
	/**
	 * Konstruktor obiektu gracza. W pierwszej kolejno�ci wywo�uje konstruktor z klasy bazowej z zadanymi wsp�rz�dnymi
	 * x i y. Nast�pnie �aduje obrazki dla modelu z plik�w, kt�re b�d� zmienia� si� w zale�no�ci od kierunku ruchu gracza.
	 * @param x wsp�rz�dna X
	 * @param y wsp�rz�dna Y
	 * @param model odno�nik do modelu ca�ej gry
	 */
	public Player(int x, int y, Model model){
		super(x, y);
		this.model = model;
		r = new Random();
		right = false;
		left = false;
		up = false;
		down = false;
		dir = r.nextInt(4);
		loadImage("/org/wjanaszek/model/textures/player_right.png", DIR_RIGHT);
		loadImage("/org/wjanaszek/model/textures/player_left.png", DIR_LEFT);
		loadImage("/org/wjanaszek/model/textures/player_up.png", DIR_UP);
		loadImage("/org/wjanaszek/model/textures/player_down.png", DIR_DOWN);
	}
	
	/**
	 * Metoda obs�uguj�ca aktualizacj� modelu w zale�no�ci od zmian wywo�anych przez kontroler.
	 * Jest to model ruchu gracza w zale�no�ci od naci�ni�tego klawisza, oraz podczas braku akcji
	 * ze strony u�ytkownika.
	 */
	public void move()	{
		int pom = 0;
		if(right || left || up || down)	{
			if(right) moveRight();
			if(left) moveLeft();
			if(up) moveUp();
			if(down) moveDown();
		}
		else	{
			moveWithoutAction(lastDir);
		}
	}
	
	/**
	 * Prywatna metoda pomocnicza do ruchu w prawo
	 */
	private void moveRight()	{
		if(right && isMovePossibility(x + SPEED, y)){
			x += SPEED;
			dir = 0;
		}
		else if(right && !isMovePossibility(x + SPEED, y)){
			//dir = DIR_RIGHT;
			dir = 0;
		}
	}
	
	/**
	 * Prywatna metoda pomocnicza do ruchu w lewo
	 */
	private void moveLeft()	{
		if(left && isMovePossibility(x - SPEED, y))	{
			x -= SPEED;
			dir = 1;
		}
		else if(left && !isMovePossibility(x - SPEED, y)){
			//dir = DIR_LEFT;
			dir = 1;
		}
	}
	
	/**
	 * Prywatna metoda pomocnicza do ruchu do g�ry
	 */
	private void moveUp()	{
		if(up && isMovePossibility(x, y - SPEED))	{
			y -= SPEED;
			dir = 2;
		}
		else if(up && !isMovePossibility(x, y - SPEED)){
			//dir = DIR_UP;
			dir = 2;
		}
	}
	
	/**
	 * Prywatna metoda pomocnicza do ruchu w d�
	 */
	private void moveDown()	{
		if(down && isMovePossibility(x, y + SPEED))	{
			y += SPEED;
			dir = 3;
		}
		else if(down && !isMovePossibility(x, y + SPEED)){
			//dir = DIR_DOWN;
			dir = 3;
		}
	}
	
	/**
	 * Prywatna metoda do ruchu gracza wed�ug ostatnio zadanego kierunku (podczas braku akcji - chodzi o ruch
	 * ci�g�y gracza).
	 * @param lastDir ostatnio zadany kierunek
	 */
	private void moveWithoutAction(int lastDir)	{
		if(lastDir == DIR_RIGHT)	{
			if(isMovePossibility(x + SPEED, y))	{
				x += SPEED;
			}
		}
		if(lastDir == DIR_LEFT)	{
			if(isMovePossibility(x - SPEED, y))	{
				x -= SPEED;
			}
		}
		if(lastDir == DIR_UP)	{
			if(isMovePossibility(x, y - SPEED))	{
				y -= SPEED;
			}
		}
		if(lastDir == DIR_DOWN)	{
			if(isMovePossibility(x, y + SPEED))	{
				y += SPEED;
			}
		}
	}
	
	private int distanceToMove(int dir, int lastDir){
		int i = x;
		int j = y;
		int distance = 0;
		if(lastDir == DIR_RIGHT)	{
			if(dir == DIR_LEFT){
				distance = distanceToLeft(dir);
			}
			if(dir == DIR_UP){
				distance = distanceToUp(dir);
			}
			if(dir == DIR_DOWN){
				distance = distanceToDown(dir);
			}
		}
		else if(lastDir == DIR_LEFT){
			if(dir == DIR_RIGHT){
				distance = distanceToRight(dir);
			}
			if(dir == DIR_UP){
				distance = distanceToUp(dir);
			}
			if(dir == DIR_DOWN){
				distance = distanceToDown(dir);
			}
		}
		else if(lastDir == DIR_UP){
			if(dir == DIR_LEFT){
				distance = distanceToLeft(dir);
			}
			if(dir == DIR_RIGHT){
				distance = distanceToRight(dir);
			}
			if(dir == DIR_LEFT){
				distance = distanceToLeft(dir);
			}
			if(dir == DIR_DOWN){
				distance = distanceToDown(dir);
			}
		}
		else if(lastDir == DIR_DOWN){
			if(dir == DIR_RIGHT)	{
				distance = distanceToRight(dir);
			}
			if(dir == DIR_LEFT){
				distance = distanceToLeft(dir);
			}
			if(dir == DIR_UP){
				distance = distanceToUp(dir);
			}
		}
		return distance;
	}
	
	private int distanceToRight(int wantedDir){
		int d = 0;
		int i = x;
		int j = y;
		while(isMovePossibility(i + SPEED, j))	{
			//if(wantedDir == DIR)
		}
		return 0;
	}
	
	private int distanceToLeft(int wantedDir)	{
		int d = 0;
		int i = x;
		int j = y;
		return 0;
	}
	
	private int distanceToUp(int wantedDir)	{
		int d = 0;
		int i = x;
		int j = y;
		return 0;
	}
	
	private int distanceToDown(int wantedDir)	{
		int d = 0;
		int i = x;
		int j = y;
		return 0;
	}
	
	/**
	 * Metoda do odczytania obrazka dla danego kierunku ruchu gracza
	 * @param direction kierunek gracza
	 * @return obrazek z graczem skierowanym w odpowiedni� stron�
	 * @see Image
	 */
	public Image getImage(int direction)	{
		if(direction == DIR_RIGHT){
			return imageRight;
		}
		else if(direction == DIR_LEFT){
			return imageLeft;
		}
		else if(direction == DIR_UP){
			return imageUp;
		}
		else if(direction == DIR_DOWN){
			return imageDown;
		}
		return imageRight;
	}
	
	/**
	 * Ustaw zadany kierunek gracza
	 * @param lastDir zadany kierunek
	 */
	public void setDirection(int lastDir)	{
		this.lastDir = lastDir;
	}
	
	/**
	 * Metoda do zwracania warto�ci kierunku gracza
	 * @return kierunek gracza w danej chwili
	 */
	public int getDirection()	{
		return dir;
	}

	/**
	 * Prywatna metoda do za�adowania obrazka dla gracza
	 * @param path �cie�ka do pliku
	 * @param direction kierunek gracza
	 * @see ImageIcon
	 */
	private void loadImage(String path, int direction){
		ImageIcon ii = new ImageIcon(getClass().getResource(path));
		if(direction == DIR_RIGHT){
			imageRight = ii.getImage();
		}
		else if(direction == DIR_LEFT){
			imageLeft = ii.getImage();
		}
		else if(direction == DIR_UP){
			imageUp = ii.getImage();
		}
		else if(direction == DIR_DOWN){
			imageDown = ii.getImage();
		}
	}
	
	/**
	 * Prywatna metoda pomocnicza sprawdzaj�ca mo�liwo�� ruchu (czy u�ytkownik "nie wejdzie"
	 * w �cian�). Tworzy pomocniczy prostok�t, aby skorzysta� z metody intersects (wykrywanie kolizji).
	 * @param dx wsp�rz�dna X, do kt�rej chcemy si� przemie�ci�
	 * @param dy wsp�rz�dna Y, do kt�rej chcemy si� przemie�ci�
	 * @return true, gdy mo�na si� ruszy� do danego miejsca, false w p.p.
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
	
	/**
	 * Metoda do wykrywania kolizji z punktami na planszy. Tworzy tymczasowy prostok�t, 
	 * aby m�c skorzysta� z metody intersects
	 * @param scorePoint dany punkt, z kt�rym sprawdzamy kolizj�
	 * @return true, je�li mo�na si� ruszy� do danego miejsca, false w p.p.
	 * @see intersects
	 */
	public boolean intersects(ScorePoint scorePoint) {
		Rectangle rect = new Rectangle(x, y, 32, 32);
		if(rect.intersects(scorePoint)){
			return true;
		}
		return false;
	}

	/**
	 * Metoda do wykrywania kolizji z punktami na planszy. Tworzy tymczasowy prostok�t, 
	 * aby m�c skorzysta� z metody intersects.
	 * @param enemy dany przeciwnik, z kt�rym sprawdzamy kolizj�
	 * @return true, je�li mo�na si� ruszy� do danego miejsca, false w p.p.
	 * @see intersects
	 */
	public boolean intersects(Enemy enemy) {
		Rectangle rect = new Rectangle(x, y, 32, 32);
		Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 32, 32);
		if(rect.intersects(enemyRect)){
			return true;
		}
		return false;
	}
}
