package com.cshikami.movement;

import com.cshikami.exception.InvalidRangeException;

public interface Movable {

	void setDestination(int destination);

	int getDestination();
	
	void move(int milliseconds) throws InterruptedException, InvalidRangeException;

}
