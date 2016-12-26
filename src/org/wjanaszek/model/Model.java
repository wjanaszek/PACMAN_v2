package org.wjanaszek.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Klasa reprezentuj�ca cz�� 'Model' z architektury MVC. Zawiera pola:
 * - wynik zmieniaj�cy si� podczas danej rozgrywki
 * - najwy�szy wynik osi�gni�ty podczas wielu gier (przetrzymywany w pliku tekstowym)
 * - gracz (pacman)
 * - poziom (plansza)
 * @author Wojciech Janaszek
 * @category Model
 */
public class Model {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private boolean pause = false;
	
	private int score;
	private int highScore;
	private static Player PLAYER;
	private static Level LEVEL;
	
	/**
	 * Konstruktor modelu. Ustawia obecny wynik na 0, oraz wczytuje z pliku tekstowego najwi�ksz� dot�d osi�gni�t� przez
	 * gracza warto��.
	 */
	public Model() {
		score = 0;
		try {
			highScore = readHighScore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PLAYER = new Player(WIDTH/2, HEIGHT/2, this);
		LEVEL = new Level("/org/wjanaszek/model/textures/map.png", this);
	}
	
	/**
	 * Metoda do zapisu warto�ci wyniku do pliku, je�li zosta� pobity nowy rekord. 
	 * @param highScore wartosc rekordu do zapisania w pliku
	 * @throws FileNotFoundException gdy plik nie zosta� znaleziony 
	 */
	public static void writeHighScore(int highScore) throws FileNotFoundException	{
		FileWriter pw = null;
		try {
			pw = new FileWriter(new File("data.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally	{
			try {
				pw.write(Integer.toString(highScore));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Metoda do ustawienia warto�ci pola pause
	 * @param b ustawiana warto��
	 */
	public void setPause(boolean b){
		pause = b;
	}
	
	/**
	 * Metoda do zwracania warto�ci pola pause
	 * @return true, je�li pause == true, false w p.p.
	 */
	public boolean getPauseVal(){
		return pause;
	}
	
	/**
	 * Metoda do odczytu najlepszego wyniku z pliku. Wywo�ywana tylko przy tworzeniu modelu.
	 * @return rekord
	 * @throws FileNotFoundException gdy plik nie zosta� znaleziony
	 * @see File, Scanner
	 */
	private static int readHighScore() throws FileNotFoundException	{
		Scanner s = new Scanner(new File("data.txt"));
		return Integer.valueOf(s.nextLine());
	}
	
	/**
	 * Metoda do ustawienia najlepszego wyniku.
	 * @param score wynik do ustawienia
	 */
	public void setHighScore(int score){
		highScore = score;
	}
	
	/**
	 * Metoda do resetowania wyniku gracza.
	 */
	public void resetScore()	{
		score = 0;
	}
	
	/**
	 * Metoda do zwi�kszenia wyniku gracza o jeden.
	 */
	public void incScore()	{
		score++;
	}
	
	/**
	 * Metoda do zwracania warto�ci rekordu.
	 * @return najlepszy wynik
	 */
	public int getHighScore()	{
		return highScore;
	}
	
	/**
	 * Metoda do zwracania warto�ci obecnego wyniku.
	 * @return obecny wynik gracza
	 */
	public int getScore()	{
		return score;
	}
	
	/**
	 * Metoda do zwracania referencji na pole player
	 * @return referencj� do gracza
	 */
	public Player getPlayer()	{
		return PLAYER;
	}
	
	/**
	 * Metoda do zwracania referencji na pole level
	 * @return referencj� do poziomu (planszy)
	 */
	public Level getLevel()	{
		return LEVEL;
	}
	
	/**
	 * Ustawia warto�� pola gracz dla modelu.
	 * @param player obiekt gracza do ustawienia w modelu
	 */
	public void setPlayer(Player player){
		this.PLAYER = player;
	}
	
	/**
	 * Ustawia warto�� pola level dla modelu.
	 * @param level obiekt planszy do ustawienia w modelu
	 */
	public void setLevel(Level level){
		this.LEVEL = level;
	}
	
	/**
	 * Metoda do zwracania szeroko�ci planszy gry
	 * @return szeroko�� gry
	 */
	public static int getWidth()	{
		return WIDTH;
	}
	
	/**
	 * Metoda do zwracania wysoko�ci planszy gry
	 * @return wysoko�� gry
	 */
	public static int getHeight()	{
		return HEIGHT;
	}
}
