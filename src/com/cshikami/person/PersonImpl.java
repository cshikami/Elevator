package com.cshikami.person;

import com.cshikami.identification.Identifiable;
import com.cshikami.identification.IdentifiableImplFactory;
import com.cshikami.exception.InvalidDataException;
import com.cshikami.movement.Movable;
import com.cshikami.movement.MovableImplFactory;

/**
 * Person implementation object
 * @author christopher_shikami
 *
 */
public class PersonImpl implements Person, Movable {
	
	int desiredFloor;
	private Identifiable identity;
	private Movable movable;
	
	public PersonImpl(int desiredFloorIn) throws InvalidDataException {
		
		identity = IdentifiableImplFactory.createIdentifiable("Person");
		movable = MovableImplFactory.createMovable(desiredFloorIn);
		desiredFloor = desiredFloorIn;
	}

	@Override
	public String getIdentifier() {
		return identity.getIdentifier();
	}
	
	public int getDesiredFloor() {
		return movable.getDestination();
	}
	
	public String toString() {
		return identity.getIdentifier();
	}

	@Override
	public void setDestination(int destination) {
		movable.setDestination(destination);
	}

	@Override
	public int getDestination() {
		return movable.getDestination();
	}

	@Override
	public void move(int milliseconds) throws InterruptedException {
		
	}

}
