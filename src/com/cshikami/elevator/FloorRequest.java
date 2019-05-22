package com.cshikami.elevator;

import com.cshikami.exception.InvalidParamException;

public class FloorRequest {
	
	private int start;
	private Direction direction;

	public FloorRequest(int startIn, Direction directionIn) throws InvalidParamException {
		setStart(startIn);
		setDirection(directionIn);
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int newStart) throws InvalidParamException {
		if (newStart < 1) {
			throw new InvalidParamException("Invalid start floor passed to setStart: " + newStart);
		}
		start = newStart;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction newDirection) {
		direction = newDirection;
	}
	
	public String toString() {
		return start + "";
	}
	
}
