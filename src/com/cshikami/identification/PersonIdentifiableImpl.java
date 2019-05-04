package com.cshikami.identification;

public class PersonIdentifiableImpl implements Identifiable {
	
	private String identifier;
	
	public PersonIdentifiableImpl(String id) throws InvalidDataException {
		setIdentifier(id);
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return identifier;
	}

	public void setIdentifier(String id) throws InvalidDataException {
        if (id == null || id.length() == 0) {
            throw new InvalidDataException("Null or empty ID passed to setIdentifier");
        }
        identifier = id;
    }
}
