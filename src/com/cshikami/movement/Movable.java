package com.cshikami.movement;

public interface Movable {

	void setDestination(int destination);

	int getDestination();
	
	void move(int milliseconds) throws InterruptedException;

}
