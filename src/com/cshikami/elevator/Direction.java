package com.cshikami.elevator;

/**
 * Direction enums with static method to determine direction based on inputs
 * @author christopher_shikami
 *
 */
public enum Direction {

	IDLE, UP, DOWN;
	
	/*
	 * Determine direction based on inputs
	 */
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
