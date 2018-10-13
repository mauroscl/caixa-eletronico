package domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
import org.junit.Test;

public class MontanteTest {

  @Test
  public void deveCalcularValorTotalMontante() {
    Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(2), 5));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 3));
    assertEquals(BigDecimal.valueOf(40), montante.getValorTotal());
  }

  @Test
  public void deveAumentarQuantidadeQuandoAdicionarNotasEmGrupoExistente() {
    Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(2), 5));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(2), 10));

    final Collection<GrupoNotas> gruposNotas = montante.getGruposNotas();
    assertEquals(1, gruposNotas.size());
    assertEquals(new GrupoNotas(BigDecimal.valueOf(2), 15), gruposNotas.toArray()[0]);
  }

//  @Test
//  public void deveCalcularNovoMontanteQuandoRemoverNotas() {
//    final Montante montanteOriginal = new Montante();
//    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(10), 5));
//    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 4));
//    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(50), 3));
//  }
}