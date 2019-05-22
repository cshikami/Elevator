package com.cshikami.elevator;

import com.cshikami.exception.InvalidParamException;
import com.cshikami.exception.InvalidRangeException;
import com.cshikami.identification.Identifiable;
import com.cshikami.movement.Movable;

public interface Elevator extends Identifiable, Movable {
	
	public void addFloorRequest(FloorRequest request) throws InvalidParamException;

	public void move(int milliseconds) throws InterruptedException, InvalidRangeException;

	public void setMaxPersonsPerElevator(int maxPersonsPerElevator) throws InvalidParamException;
	
	public void setDoorOpenTime(int time) throws InvalidParamException;
	
	public int getMaxPersonsPerElevator();
	
}
