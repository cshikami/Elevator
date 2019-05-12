package com.cshikami.movement;

import com.cshikami.elevator.RiderRequest;

public class MovableImpl implements Movable {
	
	private int destination;
	//private RiderRequest riderRequest;

	public MovableImpl(int desiredFloorIn) {
		destination = desiredFloorIn;
	}

	@Override
	public void setDestination(int destination) {
		this.destination = destination;
	}

	@Override
	public int getDestination() {
		return destination;
	}
}
