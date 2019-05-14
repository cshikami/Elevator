package com.cshikami.elevator;

import com.cshikami.identification.Identifiable;
import com.cshikami.movement.Movable;

public interface Elevator extends Identifiable, Movable {
	
	public void addFloorRequest(FloorRequest request);

	public void move(int milliseconds) throws InterruptedException;

	public void setMaxPersonsPerElevator(int maxPersonsPerElevator);
	
	public int getMaxPersonsPerElevator();
	
}
