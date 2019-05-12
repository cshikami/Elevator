package com.cshikami.elevator;

public class FloorRequest {
	
	private int start;
	private Direction direction;

	public FloorRequest(int startIn, Direction directionIn) {
		setStart(startIn);
		setDirection(directionIn);
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int newStart) {
		start = newStart;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction newDirection) {
		direction = newDirection;
	}
	
}
