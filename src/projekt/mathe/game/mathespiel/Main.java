package projekt.mathe.game.mathespiel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Frame;
import projekt.mathe.game.engine.Popup;
import projekt.mathe.game.engine.PopupPanel;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;

public class Main {
	
	private static Frame frame;
	private static Popup loading;
	
	private static void startGame() {
		loading = new Popup(new LoadingPanel(), 190, 40, -1);
		loading.show();
		Helper.loadFont();
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.ddscale", "true");
		System.setProperty("sun.java2d.translaccel", "true");
		frame = new Frame()
				.setIcon(ResLoader.getImageByName("general/frameicon.png"))
				.setSize(Values.WINDOW_WIDTH, Values.WINDOW_HEIGHT + Values.TITLEBAR_HEIGHT);
		Maingame game = new Maingame(frame);
		frame.addGame(game);
		loading.close();
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {}
		frame.show();
	}
	
	private static class LoadingPanel extends PopupPanel {

		private static final Font font = new Font("Verdana", Font.BOLD, 20);
		private static final GradientPaint paint = new GradientPaint(10, 10, Color.GREEN, 180, 10, new Color(0, 255, 255), true);
		private static final float objectAmount = 95f;
		
		@Override
		public void onPaint(Graphics2D g2d) {
			g2d.setColor(Color.GRAY);
			g2d.fillRect(0, 0, 190, 40);
			g2d.setColor(Color.CYAN);
			g2d.fillRect(5, 5, 180, 30);
			g2d.setPaint(paint);
			g2d.fillRect(5, 5, (int) (180 * (Logger.getLoadedObjects() / objectAmount)), 30);
			g2d.setColor(Color.WHITE);
			g2d.setFont(font);
			g2d.drawString((int) ((Logger.getLoadedObjects() / objectAmount) * 100f) + "%", 80, 27);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			Saver.initialize();
			startGame();
		}catch (Exception e) {
			e.printStackTrace();
			loading.close();
			Helper.showExceptionInPane(e);
		}
	}
	
}
