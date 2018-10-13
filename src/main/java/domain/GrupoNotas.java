package domain;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.Objects;
import shared.BigDecimalComparador;

public class GrupoNotas {

  protected static final String VALOR_NOTA_INVALIDO = "Valor da nota deve ser uma valor positivo.";
  protected static final String QUANTIDADE_NOTA_INVALIDO = "Quantidade de notas deve ser uma valor positivo.";

  private BigDecimal valor;
  private Long quantidade;

  public GrupoNotas(final BigDecimal valor, final long quantidade) {
    if (isNull(valor) || BigDecimalComparador.menorOuIgualQue(valor, BigDecimal.ZERO)) {
      throw new IllegalArgumentException(VALOR_NOTA_INVALIDO);
    }
    if (quantidade <= 0) {
      throw new IllegalArgumentException(QUANTIDADE_NOTA_INVALIDO);
    }
    this.valor = valor;
    this.quantidade = quantidade;
  }

  public Long getQuantidade() {
    return this.quantidade;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public BigDecimal getValorTotal() {
    return this.valor.multiply(BigDecimal.valueOf(this.quantidade));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final GrupoNotas grupoNotas = (GrupoNotas) o;
    return getValor().compareTo(grupoNotas.getValor()) == 0 && Objects
        .equals(getQuantidade(), grupoNotas.getQuantidade());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValor(), getQuantidade());
  }
}