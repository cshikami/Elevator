package com.cshikami.movement;

/**
 * Movable implementation object
 * @author christopher_shikami
 *
 */
public class MovableImpl implements Movable {

	private int destination;

	public MovableImpl(int desiredFloorIn) {
		destination = desiredFloorIn;
	}

	/**
	 * set the movable object's destination
	 */
	@Override
	public void setDestination(int destination) {
		this.destination = destination;
	}

	/**
	 * get the movable object's destination
	 */
	@Override
	public int getDestination() {
		return destination;
	}

	@Override
	public void move(int milliseconds) throws InterruptedException {
		
	}
	
}
