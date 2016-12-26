package org.wjanaszek.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

import org.wjanaszek.model.Model;
import org.wjanaszek.model.Level;
import org.wjanaszek.model.Player;
import org.wjanaszek.model.Enemy;
import org.wjanaszek.view.View;

/**
 * Klasa reprezentuj�ca cz�� 'Controller' z architektury MVC.
 * @author Wojciech Janaszek
 * @category Controller
 */
public class Controller implements Runnable {

	private final Model model;
	private final View view;
	
	private Thread thread;
	
	private boolean inGame = false;
	private boolean isRunning = false;
	private boolean newHighScore = false;
	private boolean inGameAgain = false;
	
	private BufferStrategy bs;
	
	/**
	 * Konstruktor klasy. Przypisuje odpowienie referencjie, dodaje "s�uchacza" zdarze� z widoku do kontrolera,
	 * a nast�pnie wywo�uje metod� start w�tku.
	 * @param model referencja do modelu - aby wywo�ywa� update modelu
	 * @param view referencja do widoku - aby wywo�ywa� renderowanie zmienionego modelu
	 */
	public Controller(Model model, View view){
		this.model = model;
		this.view = view;
		addKeyListener(this);
		start();
	}

	/**
	 * Metoda dodaj�ca "s�uchacza" zdarze� z widoku (okna) do controllera.
	 * @param controller odbiorca zdarze� z okna
	 * @see KeyListener
	 */
	private void addKeyListener(Controller controller) {
		view.getWindow().addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					if (inGame) {
						if (model.getPauseVal()) {
							model.setPause(false);
						} else {
							model.setPause(true);
						} 
					}
					else	{
						inGame = true;
					}
				}
				if(e.getKeyChar() == 'q')	{
					System.exit(1);
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					model.getPlayer().right = true;
					model.getPlayer().setDirection(0);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT)	{
					model.getPlayer().left = true;
					model.getPlayer().setDirection(1);
				}
				if(e.getKeyCode() == KeyEvent.VK_UP)	{
					model.getPlayer().up = true;
					model.getPlayer().setDirection(2);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					model.getPlayer().down = true;
					model.getPlayer().setDirection(3);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					model.getPlayer().right = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT)	{
					model.getPlayer().left = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP)	{
					model.getPlayer().up = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					model.getPlayer().down = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
	}

	/**
	 * Metoda od aktualizowania stanu modelu i zarz�dzania gr�.
	 */
	private void updateModel()	{
		model.getPlayer().move();
		model.getLevel().update();
		if(model.getLevel().getPoints().size() == 0)	{
			//end of current game
			inGame = false;
			return;
		}
		
		for(int i = 0; i < model.getLevel().getEnemies().size(); i++){
			if(model.getPlayer().intersects(model.getLevel().getEnemies().get(i)))	{
				if(model.getScore() > model.getHighScore()){
					model.setHighScore(model.getScore());
					newHighScore = true;
				}
				
				inGame = false;
				break;
			}
		}
	}
	
	/**
	 * Metoda do renderowania widoku w danym momencie.
	 */
	private void updateView()	{
		BufferStrategy bs = view.getWindow().getBufferStrategy();
		if(bs == null)	{
			view.getWindow().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		view.render(g);
		bs.show();
	}
	
	/**
	 * Metoda do restartu gry.
	 */
	private void restartGame()	{
		model.setPlayer(new Player(Model.getWidth()/2, Model.getHeight()/2, model));
		model.setLevel(new Level("/org/wjanaszek/model/textures/map.png", model));
		model.resetScore();
		newHighScore = false;
		inGameAgain = false;
		inGame = true;
	}
	
	/**
	 * Rozpocz�cie w�tku gry.
	 */
	public synchronized void start()	{
		if(isRunning == true)	{
			return;
		}
		else	{
			isRunning = true;
			inGame = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * Zatrzymanie dzia�ania w�tku dla gry.
	 */
	private synchronized void stop()	{
		if(isRunning == true){
			isRunning = false;
			inGame = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else	{
			return;
		}
	}
	
	@Override
	/**
	 * G��wna p�tla programu (gry).
	 */
	public void run() {
		view.getWindow().requestFocus();
		int fps = 0;
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double targetTick = 60.0;
		double ns = 1000000000/targetTick;
		double timer = System.currentTimeMillis();
		boolean firstTime = true;
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			
			while(delta >= 1){
				if(inGame){
					if(!model.getPauseVal()){
						updateModel();
						updateView();
					}
					else	{
						BufferStrategy bs = view.getWindow().getBufferStrategy();
						if(bs == null)	{
							view.getWindow().createBufferStrategy(3);
							return;
						}
						Graphics g = bs.getDrawGraphics();
						view.renderPause(g);
						g.dispose();
						bs.show();
					}
				}
				else	{
					BufferStrategy bs = view.getWindow().getBufferStrategy();
					if(bs == null)	{
						view.getWindow().createBufferStrategy(3);
						return;
					}
					Graphics g = bs.getDrawGraphics();
					if(newHighScore){
						view.renderNewHighScore(model.getHighScore(), g);
						try {
							model.writeHighScore(model.getScore());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					view.renderNewGameQuestion(g);
					bs.show();
					g.dispose();
					
					final boolean kPress = false;
					
					view.getWindow().addKeyListener(new KeyListener(){
						
						@Override
						public void keyPressed(KeyEvent e) {
							if(e.getKeyCode() == KeyEvent.VK_SPACE && !kPress)	{
								//kPress = true;
								inGameAgain = true;
							}
							if(e.getKeyChar() == 'q' && !kPress){
								//kPress = true;
								isRunning = false;
								System.exit(1);
							}
						}
						
						@Override
						public void keyReleased(KeyEvent e) {}

						@Override
						public void keyTyped(KeyEvent e) {}
						
					});
				
					if(inGameAgain){
						restartGame();
					}
					
					delta = 0;
					break;
				}
				fps++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000)	{
				System.out.println(fps);
				fps = 0;
				timer += 1000;
			}
		}
		stop();
	}
}
