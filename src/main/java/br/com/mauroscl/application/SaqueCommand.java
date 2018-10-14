package br.com.mauroscl.application;

import java.math.BigDecimal;

public class SaqueCommand {
  private BigDecimal valor;

  SaqueCommand(final BigDecimal valor) {
    this.valor = valor;
  }

  BigDecimal getValor() {
    return this.valor;
  }
}
