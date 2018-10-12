package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import shared.BigDecimalComparador;

public class CalculadorMinimoNotas implements ICalculadorNotas {

  private ICarregadorNotas carregadorNotas;

  public CalculadorMinimoNotas(final ICarregadorNotas carregadorNotas) {
    this.carregadorNotas = carregadorNotas;
  }

  @Override
  public Collection<Montante> calcular(final BigDecimal valorSaque) {
    final Collection<Montante> notasDisponiveis = this.carregadorNotas.obterDisponiveis();
    //obtem as notas com valor menor ou igual ao valor solicitado no saque
    final Collection<Montante> notasCandidatas = this.obterNotasCandidatas(notasDisponiveis, valorSaque);

    BigDecimal valorRestante = valorSaque;
    final List<Montante> montanteParaSacar = new ArrayList<>();
    for (Montante montanteDisponivel : notasCandidatas) {

      if (BigDecimalComparador.maiorQue(valorRestante, BigDecimal.ZERO)) {

        final Long quantidadeNotasNecessarias = valorRestante
            .divideToIntegralValue(montanteDisponivel.getValorNota()).longValue();

        final Long quantidadeNotasUtilizadas =
            quantidadeNotasNecessarias <= montanteDisponivel.getQuantidade()
                ? quantidadeNotasNecessarias
                : montanteDisponivel.getQuantidade();

        //inclui a nota no montante para sacar
        final Montante montanteUtilizado = new Montante(montanteDisponivel.getValorNota(),
            quantidadeNotasUtilizadas);
        montanteParaSacar.add(montanteUtilizado);

        //atualizar o valor restante
        valorRestante = valorRestante.subtract(montanteUtilizado.getValorTotal());

      }

    }
    return montanteParaSacar;
  }

  private Collection<Montante> obterNotasCandidatas(final Collection<Montante> notasDisponiveis,
      final BigDecimal valor) {
    final List<Montante> notasCandidatas = notasDisponiveis.stream().filter(
        nota -> nota.getQuantidade() > 0 && BigDecimalComparador
            .menorOuIgualQue(nota.getValorNota(), valor)).collect(Collectors.toList());
    Collections.sort(notasCandidatas, Collections.reverseOrder());
    return notasCandidatas;
  }
}
