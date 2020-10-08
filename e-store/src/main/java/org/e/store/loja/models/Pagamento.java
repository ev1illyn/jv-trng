package org.e.store.loja.models;

import java.math.BigDecimal;

public class Pagamento {
	
	private BigDecimal value;

	public Pagamento(BigDecimal value) {
		this.setValue(value);
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
