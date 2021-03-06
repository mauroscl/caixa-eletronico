package br.com.mauroscl.domain;

import br.com.mauroscl.shared.BigDecimalComparador;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CalculadorMinimoNotas implements ICalculadorNotas {

  @Override
  public Montante calcular(final BigDecimal valorSaque, Montante montanteDisponivel) {
    final Collection<GrupoNotas> grupoNotasCandidatos = this
        .obterNotasCandidatas(montanteDisponivel.getGruposNotas(), valorSaque);

    final Montante montanteParaEntregar = new Montante();
    BigDecimal valorRestante = valorSaque;
    for (GrupoNotas grupoCandidato : grupoNotasCandidatos) {

      if (BigDecimalComparador.maiorOuIgualQue(valorRestante, grupoCandidato.getValor())) {

        final Long quantidadeNotasNecessarias = valorRestante
            .divideToIntegralValue(grupoCandidato.getValor()).longValue();

        final Long quantidadeNotasUtilizadas =
            quantidadeNotasNecessarias <= grupoCandidato.getQuantidade()
                ? quantidadeNotasNecessarias : grupoCandidato.getQuantidade();

        final GrupoNotas grupoNotasUtilizadas = new GrupoNotas(grupoCandidato.getValor(),
            quantidadeNotasUtilizadas);
        montanteParaEntregar.adicionarGrupo(grupoNotasUtilizadas);

        valorRestante = valorRestante.subtract(grupoNotasUtilizadas.getValorTotal());

      }

    }

    return montanteParaEntregar;
  }

  private Collection<GrupoNotas> obterNotasCandidatas(
      final Collection<GrupoNotas> gruposDisponiveis, final BigDecimal valor) {
    return gruposDisponiveis.stream().filter(
        nota -> nota.getQuantidade() > 0 && BigDecimalComparador
            .menorOuIgualQue(nota.getValor(), valor))
        .sorted(Comparator.comparing(GrupoNotas::getValor, Comparator.reverseOrder()))
        .collect(Collectors.toList());
  }
}
