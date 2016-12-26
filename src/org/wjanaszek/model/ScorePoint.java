package org.wjanaszek.model;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentuj�ca model punktu, zbieranych przez gracza.
 * @author Wojciech Janaszek
 * @category Model
 */
public class ScorePoint extends Rectangle {

	private Image image;
	
	/**
	 * Konstruktor tworz�cy punkt o zadanych wsp�rz�dnych.
	 * @param x o� X
	 * @param y o� Y
	 */
	public ScorePoint(int x, int y){
		setBounds(x + 10, y + 10, 8, 8);
		loadImage("/org/wjanaszek/model/textures/point.png");
	}
	
	/**
	 * Metoda zwracajac� pole image obiektu.
	 * @return obrazek
	 * @see Image
	 */
	public Image getImage()	{
		return image;
	}
	
	/**
	 * Prywatna metoda do za�adowania obrazka pod pole image.
	 * @param path �cie�ka, gdzie znajduje si� obrazek
	 */
	private void loadImage(String path){
		ImageIcon ii = new ImageIcon(getClass().getResource(path));
		image = ii.getImage();
	}
}
