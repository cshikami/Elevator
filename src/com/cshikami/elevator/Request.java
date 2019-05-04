package com.cshikami.elevator;

public class Request {
	
	private int start;
	private Direction direction;

	public Request(int startIn, Direction directionIn) {
		setStart(startIn);
		direction = directionIn;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int newStart) {
		start = newStart;
	}
	
}
