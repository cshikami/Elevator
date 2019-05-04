package com.cshikami.person;

import com.cshikami.identification.Identifiable;
import com.cshikami.identification.IdentifiableImplFactory;
import com.cshikami.identification.InvalidDataException;

public class PersonImpl implements Person {
	
	int desiredFloor;
	private Identifiable identity;
	
	public PersonImpl(int desiredFloorIn) throws InvalidDataException {
		
		identity = IdentifiableImplFactory.createIdentifiable("Person");
		desiredFloor = desiredFloorIn;
	}

	@Override
	public String getIdentifier() {
		return identity.getIdentifier();
	}
	
	public String toString() {
		return getIdentifier();
	}
}
