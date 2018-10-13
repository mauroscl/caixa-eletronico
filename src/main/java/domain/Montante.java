package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Montante {

  private Collection<GrupoNotas> gruposNotas;

  public Montante() {
    this.gruposNotas = new ArrayList<>();
  }

  public void adicionarGrupo(GrupoNotas grupoNotas){
    this.gruposNotas.add(grupoNotas);
  }

  protected BigDecimal getValorTotal() {
    return this.gruposNotas.stream().map(grupoNotas -> grupoNotas.getValorTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  Collection<GrupoNotas> getGruposNotas() {
    return Collections.unmodifiableCollection(this.gruposNotas);
  }


}
