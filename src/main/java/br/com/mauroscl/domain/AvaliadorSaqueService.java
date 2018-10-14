package br.com.mauroscl.domain;

import br.com.mauroscl.shared.BigDecimalComparador;
import java.math.BigDecimal;
import java.util.Optional;

public class AvaliadorSaqueService implements IAvaliadorSaqueService {

  private final ICalculadorNotas calculadorNotas;

  public AvaliadorSaqueService(final ICalculadorNotas calculadorNotas) {
    this.calculadorNotas = calculadorNotas;
  }

  @Override
  public Optional<AvaliacaoSaqueResultado> avaliar(final BigDecimal valorSaque,
      final Montante montanteDisponivel) {
    if (BigDecimalComparador.maiorQue(valorSaque, montanteDisponivel.getValorTotal())) {
      return Optional.empty();
    }
    final Montante montanteCalculado = this.calculadorNotas.calcular(valorSaque, montanteDisponivel);
    boolean encontrouNotas = BigDecimalComparador.igual(valorSaque, montanteCalculado.getValorTotal());
    if (encontrouNotas) {
      final Montante novoMontanteDisponivel = montanteDisponivel.remover(montanteCalculado);
      final AvaliacaoSaqueResultado resultadoAvaliacao = new AvaliacaoSaqueResultado(montanteCalculado, novoMontanteDisponivel);
      return Optional.of(resultadoAvaliacao);
    }
    return Optional.empty();
  }
}
