package br.com.mauroscl.shared;

import java.math.BigDecimal;

public class BigDecimalComparador {

  public static boolean menorOuIgualQue(BigDecimal valor1, BigDecimal valor2) {
    return valor1.compareTo(valor2) <= 0;
  }

  public static boolean maiorOuIgualQue(BigDecimal valor1, BigDecimal valor2) {
    return valor1.compareTo(valor2) >= 0;
  }

  public static boolean igual(final BigDecimal valor1, final BigDecimal valor2) {
    return valor1.compareTo(valor2) == 0;
  }

  public static boolean diferente(final BigDecimal valor1, final BigDecimal valor2) {
    return valor1.compareTo(valor2) != 0;
  }
}
