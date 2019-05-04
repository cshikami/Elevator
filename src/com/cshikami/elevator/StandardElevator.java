package com.cshikami.elevator;

import java.util.ArrayList;
import java.util.List;

import com.cshikami.identification.InvalidDataException;


public class StandardElevator implements Elevator {
	
	private Elevator elevator;
	
	private final List<Request> floorRequests = new ArrayList<>();

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
	public void addFloorRequest(Request request) {
		floorRequests.add(request);
		
	}

}