package domain;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.Objects;
import shared.BigDecimalComparador;

public class Montante {

  protected static String VALOR_NOTA_INVALIDO = "Valor da nota deve ser uma valor positivo.";
  protected static String QUANTIDADE_NOTA_INVALIDO = "Quantidade de notas deve ser uma valor positivo.";

  private BigDecimal valorNota;
  private Long quantidade;

  public Montante(final BigDecimal valorNota, final long quantidade) {
    if (isNull(valorNota) || BigDecimalComparador.menorOuIgualQue(valorNota, BigDecimal.ZERO)) {
      throw new IllegalArgumentException(VALOR_NOTA_INVALIDO);
    }
    if (quantidade <= 0) {
      throw new IllegalArgumentException(QUANTIDADE_NOTA_INVALIDO);
    }
    this.valorNota = valorNota;
    this.quantidade = quantidade;
  }

  public Long getQuantidade() {
    return this.quantidade;
  }

  public BigDecimal getValorNota() {
    return this.valorNota;
  }

  public BigDecimal getValorTotal() {
    return this.valorNota.multiply(BigDecimal.valueOf(this.quantidade));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Montante montante = (Montante) o;
    return getValorNota().compareTo(montante.getValorNota()) == 0 && Objects
        .equals(getQuantidade(), montante.getQuantidade());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValorNota(), getQuantidade());
  }
}
