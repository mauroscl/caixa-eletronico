package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import shared.BigDecimalComparador;

public class CalculadorMinimoNotas implements ICalculadorNotas {

  private ICarregadorNotas carregadorNotas;

  public CalculadorMinimoNotas(final ICarregadorNotas carregadorNotas) {
    this.carregadorNotas = carregadorNotas;
  }

  @Override
  public Collection<GrupoNotas> calcular(final BigDecimal valorSaque) {
    final Collection<GrupoNotas> notasDisponiveis = this.carregadorNotas.obterDisponiveis();
    final Collection<GrupoNotas> grupoNotasCandidatos = this
        .obterNotasCandidatas(notasDisponiveis, valorSaque);

    BigDecimal valorRestante = valorSaque;
    final Collection<GrupoNotas> notasParaEntregar = new ArrayList<>();
    for (GrupoNotas grupoCandidato : grupoNotasCandidatos) {

      if (BigDecimalComparador.maiorOuIgualQue(valorRestante, grupoCandidato.getValor())) {

        final Long quantidadeNotasNecessarias = valorRestante
            .divideToIntegralValue(grupoCandidato.getValor()).longValue();

        final Long quantidadeNotasUtilizadas =
            quantidadeNotasNecessarias <= grupoCandidato.getQuantidade()
                ? quantidadeNotasNecessarias : grupoCandidato.getQuantidade();

        final GrupoNotas grupoNotasUtilizadas = new GrupoNotas(grupoCandidato.getValor(),
            quantidadeNotasUtilizadas);
        notasParaEntregar.add(grupoNotasUtilizadas);

        valorRestante = valorRestante.subtract(grupoNotasUtilizadas.getValorTotal());

      }

    }
    return notasParaEntregar;
  }

  private Collection<GrupoNotas> obterNotasCandidatas(
      final Collection<GrupoNotas> gruposDisponiveis, final BigDecimal valor) {
    final List<GrupoNotas> gruposCandidatos = gruposDisponiveis.stream().filter(
        nota -> nota.getQuantidade() > 0 && BigDecimalComparador
            .menorOuIgualQue(nota.getValor(), valor)).collect(Collectors.toList());
    Collections.sort(gruposCandidatos,
        Comparator.comparing(GrupoNotas::getValor, Comparator.reverseOrder()));
    return gruposCandidatos;
  }
}
