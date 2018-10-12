package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Montante implements Comparable<Montante> {

  private BigDecimal valorNota;
  private Long quantidade;

  public Montante(final BigDecimal valorNota, final Long quantidade) {
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
  public int compareTo(final Montante montante) {
    return this.valorNota.compareTo(montante.getValorNota());
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
    return Objects.equals(getValorNota(), montante.getValorNota()) && Objects
        .equals(getQuantidade(), montante.getQuantidade());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValorNota(), getQuantidade());
  }
}
