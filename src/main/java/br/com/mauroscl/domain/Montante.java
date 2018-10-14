package br.com.mauroscl.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import br.com.mauroscl.shared.BigDecimalComparador;

public class Montante {

  private final Collection<GrupoNotas> gruposNotas;

  public Montante() {
    this.gruposNotas = new ArrayList<>();
  }

  BigDecimal getValorTotal() {
    return this.gruposNotas.stream().map(GrupoNotas::getValorTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  Collection<GrupoNotas> getGruposNotas() {
    return Collections.unmodifiableCollection(this.gruposNotas);
  }

  public void adicionarGrupo(GrupoNotas grupoNotas) {
    this.gruposNotas.stream().filter(this.notasDoMesmoValor(grupoNotas.getValor())).findFirst()
        .ifPresentOrElse(grupoExistente -> {
          GrupoNotas grupoAtualizado = grupoExistente.somar(grupoNotas);
          this.gruposNotas.remove(grupoExistente);
          this.gruposNotas.add(grupoAtualizado);
        }, () -> this.gruposNotas.add(grupoNotas));
  }

  Montante remover(final Montante montanteParaRemover) {
    final Montante montante = new Montante();
    final Collection<GrupoNotas> gruposNotasParaRemover = montanteParaRemover.getGruposNotas();

    for (GrupoNotas grupoNotas : this.gruposNotas) {
      gruposNotasParaRemover.stream().filter(this.notasDoMesmoValor(grupoNotas.getValor()))
          .findFirst()
          .ifPresentOrElse(possivelGrupo -> grupoNotas.subtrair(possivelGrupo)
              .ifPresent(montante::adicionarGrupo),
          () -> montante.adicionarGrupo(grupoNotas));
    }
    return montante;
  }

  private Predicate<GrupoNotas> notasDoMesmoValor(BigDecimal valor) {
    return gn -> BigDecimalComparador.igual(gn.getValor(), valor);
  }

}
