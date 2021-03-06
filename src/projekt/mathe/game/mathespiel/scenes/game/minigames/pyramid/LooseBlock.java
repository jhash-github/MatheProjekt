package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.UUID;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;

public class LooseBlock extends Dragable{

	private BlockHolder blockHolder;
	private boolean lastTickGrabbed;
	private UUID occupiedPlaceID;
	private float startX, startY;
	private Number number;
	private static Image brickImage1 = ResLoader.getImageByName("game/minigames/pyramid/brick1.png");
	private static Image brickImage2 = ResLoader.getImageByName("game/minigames/pyramid/brick2.png");
	private Image brickImage;
	private static final int KONTUR = 3;
	private boolean moveable;
	
	public LooseBlock(Scene container, int x, int y, int w, int h, BlockHolder blockHolder, int n1, int n2) {
		super(container, x, y, w, h);
		this.blockHolder = blockHolder;
		number = new Number(n1, n2, this);
		moveable = true;
		if(Math.random() < .55f) {
			brickImage = brickImage1;
		}else {
			brickImage = brickImage2;
		}
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
		startX = x;
		startY = y;
	}
	
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public Number getNumber() {
		return number;
	}
	
	public boolean inPlace() {
		if(occupiedPlaceID == null) {
			return false;
		}
		return true;
	}
	
	public boolean inRightPlace() {
		if(occupiedPlaceID == null) {
			return false;
		}
		Place place = blockHolder.getPlaceByID(occupiedPlaceID);
		return place.getNumber().getN1() == number.getN1() && place.getNumber().getN2() == number.getN2();
	}
	
	public void setOccupiedPlaceID(UUID occupiedPlaceID) {
		this.occupiedPlaceID = occupiedPlaceID;
	}
	
	@Override
	public void onTick(float delta) {
		if(lastTickGrabbed) {
			lastTickGrabbed = false;
			Place place = blockHolder.getBoundsOfNearestPlace(this);
			if(place != null && !place.isOccupied()) {
				setX(place.getX());
				setY(place.getY());
				place.setOccupied(true);
				occupiedPlaceID = place.getID();
			}else {
				setX(startX);
				setY(startY);
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect((int) (getX() - KONTUR), (int) (getY() - KONTUR), (int) (getW() + KONTUR * 2), (int) (getH() + KONTUR * 2));
		g2d.drawImage(brickImage, (int) getX(), (int) getY(), (int) getW(), (int) getH(), null); 
		number.onPaint(g2d);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		if(!moveable) {
			return;
		}
		if(!blockHolder.grabbed()) {
			if(getBounds().contains(e.getPoint())) {
				if(occupiedPlaceID != null) {
					blockHolder.setPlaceOccupied(occupiedPlaceID, false);
					occupiedPlaceID = null;
				}
			}
			super.onMousePressed(e);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!moveable) {
			return;
		}
		lastTickGrabbed = wasGrabbed();
		super.onMouseReleased(e);
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		onMouseReleased(e);
	}
	
}
