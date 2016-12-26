package org.wjanaszek.model;

/**
 * Klasa bazowa dla modelu gracza i przeciwnika (duch�w).
 * @author Wojciech Janaszek
 * @category Model
 */
public class MovingObject {
	protected int x;
	protected int y;
	
	/**
	 * Konstruktor obiektu. Tworzy punkt o zadanych wsp�rz�dnych
	 * @param x wsp�rz�dna X
	 * @param y wsp�rz�dna Y
	 */
	public MovingObject(int x, int y){
		this.x = x; 
		this.y = y;
	}
	
	/**
	 * Metoda do ustawienia wsp�rz�dnej X
	 * @param x ustawiana warto��
	 */
	public void setX(int x)	{
		this.x = x;
	}
	
	/**
	 * Metoda do ustawienia wsp�rz�dnej Y
	 * @param y ustawiana warto��
	 */
	public void setY(int y)	{
		this.y = y;
	}
	
	/**
	 * Metoda do ustawienia obu wsp�rz�dnych
	 * @param x nowa warto�� X
	 * @param y nowa warto�� Y
	 */
	public void setXY(int x, int y)	{
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Metoda do zwracania wsp�rz�dnej X
	 * @return wsp�rz�dna X punktu
	 */
	public int getX()	{
		return x;
	}
	
	/**
	 * Metoda do zwracania wsp�rz�dnej Y
	 * @return wsp�rz�dna Y punktu
	 */
	public int getY()	{
		return y;
	}
}
