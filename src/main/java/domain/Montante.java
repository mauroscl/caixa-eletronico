package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import shared.BigDecimalComparador;

public class Montante {

  private Collection<GrupoNotas> gruposNotas;

  public Montante() {
    this.gruposNotas = new ArrayList<>();
  }

  public void adicionarGrupo(GrupoNotas grupoNotas) {
    final Optional<GrupoNotas> possivelGrupoExistente = this.gruposNotas.stream()
        .filter(gn -> BigDecimalComparador.igual(gn.getValor(), grupoNotas.getValor())).findFirst();

    if (possivelGrupoExistente.isPresent()) {
      final GrupoNotas grupoExistente = possivelGrupoExistente.get();
      GrupoNotas grupoAtualizado = grupoExistente.somar(grupoNotas);
      this.gruposNotas.remove(grupoExistente);
      this.gruposNotas.add(grupoAtualizado);
    } else {
      this.gruposNotas.add(grupoNotas);
    }

  }

  protected BigDecimal getValorTotal() {
    return this.gruposNotas.stream().map(grupoNotas -> grupoNotas.getValorTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  protected Collection<GrupoNotas> getGruposNotas() {
    return Collections.unmodifiableCollection(this.gruposNotas);
  }

}
