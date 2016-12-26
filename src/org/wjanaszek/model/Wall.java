package org.wjanaszek.model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca kwadratowa sciane na planszy w modelu.
 * @author Wojciech Janaszek
 * @category Model
 */
public class Wall extends Rectangle {
	
	private Image image;
	
	/**
	 * Konstruktor.
	 * @param x wsp�rz�dna X
	 * @param y wsp�rz�dna Y
	 */
	public Wall(int x, int y)	{
		setBounds(x, y, 32, 32);
		loadImage("/org/wjanaszek/model/textures/wall.png");
	}
	
	/**
	 * Zwraca obrazek sciany.
	 * @return obrazek sciany (tekstura)
	 * @see Image
	 */
	public Image getImage()	{
		return image;
	}
	
	/**
	 * Metoda �aduje tekstur� �ciany z pliku podanego okre�lon� �cie�k�.
	 * @param path �cie�ka do pliku z tekstur� �ciany
	 */
	private void loadImage(String path)	{
		ImageIcon ii = new ImageIcon(getClass().getResource(path));
		image = ii.getImage();
	}
}

