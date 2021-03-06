package projekt.mathe.game.engine.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.Scene;

public abstract class ScreenElement {

	private Scene container;
	private float x, y, w, h;
	
	public ScreenElement(Scene container, int x, int y, int w, int h) {
		this.container = container;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void addToX(float f) {
		x += f;
	}
	
	public void addToY(float f) {
		y += f;
	}
	
	public float getX() {
		return Math.round(x);
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return Math.round(y);
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return Math.round(w);
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return Math.round(h);
	}

	public void setH(float h) {
		this.h = h;
	}

	public Scene getContainer() {
		return container;
	}

	public void setContainer(Scene container) {
		this.container = container;
	}

	public abstract void onTick(float delta);
	
	//F�rs aufrufen aus der Scene
	public void onPerformacePaint(Graphics2D g2d) {
		if(container.camera.inRangeOfCamera(x, y, w, h)) {
			onPaint(g2d);
		}
	}
	
	//F�r den User zum �berschreiben
	public abstract void onPaint(Graphics2D g2d);
	
	public boolean onScreen() {
		return container.camera.inRangeOfCamera(x, y, w, h);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) Math.round(x), (int) Math.round(y), (int) w, (int) h);
	}
	
	public Point getMiddle() {
		Point point = new Point((int) (x + w/2), (int) (y + h/2));
		return point;
	}
	
	public void onMousePressed(MouseEvent e) {}
	public void onMouseReleased(MouseEvent e) {}
	public void onMouseClicked(MouseEvent e) {}
	public void onMouseDragged(MouseEvent e) {}
	public void onMouseMoved(MouseEvent e) {}
	public void onMouseWheelMoved(MouseWheelEvent e) {}
	public void onMouseExited(MouseEvent e) {}
	
}
