package br.com.mauroscl.domain;

import java.math.BigDecimal;
import java.util.Optional;
import br.com.mauroscl.shared.BigDecimalComparador;

public class AvaliadorSaqueService implements IAvaliadorSaqueService {

  private ICalculadorNotas calculadorNotas;

  public AvaliadorSaqueService(final ICalculadorNotas calculadorNotas) {
    this.calculadorNotas = calculadorNotas;
  }

  @Override
  public Optional<AvaliacaoSaqueResultado> avaliar(final BigDecimal valorSaque,
      final Montante montanteDisponivel) {
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
