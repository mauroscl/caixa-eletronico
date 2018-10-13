package domain;

import java.math.BigDecimal;
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
  public CalculoNotasResultado calcular(final BigDecimal valorSaque) {
    final Montante montanteDisponivel = this.carregadorNotas.obterDisponiveis();
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

    boolean encontrouNotas = BigDecimalComparador.igual(valorSaque, montanteParaEntregar.getValorTotal());

    return new CalculoNotasResultado(encontrouNotas, montanteParaEntregar);

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
