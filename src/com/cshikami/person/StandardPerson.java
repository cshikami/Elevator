package com.cshikami.person;

import com.cshikami.identification.InvalidDataException;

/**
 * 
 * @author christopher_shikami
 *
 */

public class StandardPerson implements Person {
	
	private Person myPerson; //Delegate - will refer to some implementation object
	
	//int desiredFloor;

	public StandardPerson(int desiredFloorIn) throws InvalidDataException {
		
		myPerson = PersonImplFactory.createPerson(desiredFloorIn);
		//desiredFloor = desiredFloorIn;
	}
	
	public String getIdentifier() {
		return myPerson.getIdentifier();
	}
	
	public String toString() {
		return getIdentifier();
	}
	
}