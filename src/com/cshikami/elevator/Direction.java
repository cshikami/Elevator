package com.cshikami.elevator;

public enum Direction {

	IDLE, UP, DOWN;
	
	public static Direction determineDirection(int start, int end) {
		Direction direction;
		if (start == end) {
			direction = IDLE;
		} else if (start < end) {
			direction = UP;
		} else {
			direction = DOWN;
		}
		
		return direction;
	}
	
}
