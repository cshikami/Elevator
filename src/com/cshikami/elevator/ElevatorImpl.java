package com.cshikami.elevator;

import java.util.ArrayList;
import java.util.List;

import com.cshikami.identification.Identifiable;
import com.cshikami.identification.IdentifiableImplFactory;
import com.cshikami.identification.InvalidDataException;

public class ElevatorImpl implements Elevator {
	
	private Identifiable identity;
	private int elevatorId;
	
	private final List<Request> floorRequests = new ArrayList<>();
	
	public ElevatorImpl(int elevatorIdIn) throws InvalidDataException {
		identity = IdentifiableImplFactory.createIdentifiable("Elevator");
		elevatorId = elevatorIdIn;
	}
	
	public int getElevatorId() {
		return elevatorId;
	}

	@Override
	public String getIdentifier() {
		return identity.getIdentifier();
	}

	@Override
	public void addFloorRequest(Request request) {
		floorRequests.add(request);
		//test to see if floor request was added
		System.out.println("Added floor request " + floorRequests.get(0).getStart());
	}
	
	public void popFloorRequest() {
		floorRequests.get(0);
		floorRequests.remove(0);
	}
	
	public String toString() {
		return "Elevator " + getElevatorId();
	}
	
}
