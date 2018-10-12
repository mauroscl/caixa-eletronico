package domain;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.Objects;

class Nota {

  protected static String MENSAGEM_VALOR_INVALIDO = "Valor da nota deve ser positivo";

  private final BigDecimal valor;

  private Nota(final BigDecimal valor) {
    if (isNull(valor)) {
      throw new IllegalArgumentException(MENSAGEM_VALOR_INVALIDO);
    }
    if (BigDecimal.ZERO.compareTo(valor) >= 0) {
      throw new IllegalArgumentException(MENSAGEM_VALOR_INVALIDO);
    }
    this.valor = valor;
  }

  public static Nota comValor(BigDecimal valor) {
    return new Nota(valor);
  }

  public static Nota comValor(long valor) {
    return new Nota(BigDecimal.valueOf(valor));
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Nota nota = (Nota) o;
    return Objects.equals(getValor(), nota.getValor());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValor());
  }
}
