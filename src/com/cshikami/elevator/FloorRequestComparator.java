package com.cshikami.elevator;

import java.util.Comparator;

public class FloorRequestComparator implements Comparator<FloorRequest> {

	@Override
	public int compare(FloorRequest r1, FloorRequest r2) {
		// TODO Auto-generated method stub
		return r1.getStart() < r2.getStart() ? -1 : r1.getStart() == r2.getStart() ? 0 : 1;
	}

}
