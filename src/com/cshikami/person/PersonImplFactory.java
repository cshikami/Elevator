package com.cshikami.person;

import com.cshikami.identification.InvalidDataException;

public class PersonImplFactory {
	
	public static Person createPerson(int desiredFloor) throws InvalidDataException {
		return new PersonImpl(desiredFloor);
	}
}
