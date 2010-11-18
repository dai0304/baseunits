package com.domainlanguage.base;

import java.math.BigDecimal;

public enum Rounding {
	
	CEILING(BigDecimal.ROUND_CEILING),

	UP(BigDecimal.ROUND_UP),

	DOWN(BigDecimal.ROUND_DOWN),

	FLOOR(BigDecimal.ROUND_FLOOR),

	HALF_UP(BigDecimal.ROUND_HALF_UP),

	HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),

	HALF_EVEN(BigDecimal.ROUND_HALF_EVEN),

	UNNECESSARY(BigDecimal.ROUND_UNNECESSARY);
	
	public final int value;
	

	private Rounding(int value) {
		this.value = value;
	}
}
