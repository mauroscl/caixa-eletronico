package br.com.mauroscl.application;

import java.math.BigDecimal;

class SaqueCommand {
  private final BigDecimal valor;

  SaqueCommand(final BigDecimal valor) {
    this.valor = valor;
  }

  BigDecimal getValor() {
    return this.valor;
  }
}
