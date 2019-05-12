package com.cshikami.elevator;

import java.util.ArrayList;
import java.util.List;

import com.cshikami.identification.InvalidDataException;


public class StandardElevator implements Elevator {
	
	private Elevator elevator;
	
	private final List<FloorRequest> floorRequests = new ArrayList<>();

	public StandardElevator(int elevatorIdIn) throws InvalidDataException {
		
		elevator = ElevatorImplFactory.createElevator(elevatorIdIn);
	}

	@Override
	public String getIdentifier() {
		return elevator.getIdentifier();
	}
	
	public String toString() {
		return getIdentifier();
	}

	@Override
	public void addFloorRequest(FloorRequest request) {
		floorRequests.add(request);
		
	}

	@Override
	public void move(int milliseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDestination(int destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDestination() {
		// TODO Auto-generated method stub
		return 0;
	}

}
