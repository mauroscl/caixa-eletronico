package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Montante implements Comparable<Montante> {

  private Nota nota;
  private Long quantidade;

  public Montante(final Nota nota, final Long quantidade) {
    this.nota = nota;
    this.quantidade = quantidade;
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
    return Objects.equals(nota, montante.nota) && Objects.equals(quantidade, montante.quantidade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nota, quantidade);
  }

  public Long getQuantidade() {
    return this.quantidade;
  }

  public BigDecimal getValor() {
    return this.nota.getValor();
  }

  public Nota getNota() {
    return this.nota;
  }

  public BigDecimal getValorTotal() {
    return this.getValor().multiply(BigDecimal.valueOf(this.quantidade));
  }

  @Override
  public int compareTo(final Montante montante) {
    return this.getValor().compareTo(montante.getValor());
  }
}
