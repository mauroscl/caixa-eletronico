package br.com.mauroscl.domain;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import br.com.mauroscl.shared.BigDecimalComparador;

public class GrupoNotas {

  static final String VALOR_NOTA_INVALIDO = "Valor da nota deve ser uma valor positivo.";
  static final String QUANTIDADE_NOTA_INVALIDO = "Quantidade de notas deve ser uma valor positivo.";
  static final String SOMA_VALORES_DIFERENTES = "Não é possível somar notas de valores diferentes.";
  static final String SUBTRACAO_VALORES_DIFERENTES = "Não é possível subtrair notas de valores diferentes.";

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

  GrupoNotas somar(final GrupoNotas grupoNotas) {
    if (BigDecimalComparador.diferente(this.getValor(), grupoNotas.getValor())) {
      throw new IllegalArgumentException(SOMA_VALORES_DIFERENTES);
    }
    return new GrupoNotas(this.valor, this.quantidade + grupoNotas.quantidade);
  }

  Optional<GrupoNotas> subtrair(final GrupoNotas grupoNotas) {
    if (BigDecimalComparador.diferente(this.getValor(), grupoNotas.getValor())) {
      throw new IllegalArgumentException(SUBTRACAO_VALORES_DIFERENTES);
    }
    return this.quantidade.equals(grupoNotas.quantidade)
        ? Optional.empty()
        : Optional.of(new GrupoNotas(this.valor, this.quantidade - grupoNotas.quantidade)) ;
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
