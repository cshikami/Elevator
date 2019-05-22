package com.cshikami.identification;

import com.cshikami.exception.InvalidDataException;

/**
 * Identifiable implementation object to get and set identifier
 * @author christopher_shikami
 *
 */
public class IdentifiableImpl implements Identifiable {

    private String identifier;

    public IdentifiableImpl(String id) throws InvalidDataException {
        setIdentifier(id);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String id) throws InvalidDataException {
        if (id == null || id.length() == 0) {
            throw new InvalidDataException("Null or empty ID passed to setIdentifier");
        }
        identifier = id;
    }
}
