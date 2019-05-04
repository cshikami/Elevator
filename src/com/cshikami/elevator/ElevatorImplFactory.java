package com.cshikami.elevator;

import com.cshikami.identification.InvalidDataException;

public class ElevatorImplFactory {
	
	public static Elevator createElevator(int elevatorId) throws InvalidDataException {
		return new ElevatorImpl(elevatorId);
	}

}
