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

  @Test
  public void deveCalcularNovoMontanteQuandoRemoverNotas() {
    final Montante montanteOriginal = new Montante();
    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(10), 5));
    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 4));
    montanteOriginal.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(50), 3));

    /*
    * notas de 10 - remove 2 ficam 3
    * notas de 20 - não remove nenhuma
    * notas de 50 - remove todas
     */
    final Montante montanteParaRemover = new Montante();
    montanteParaRemover.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(10), 2));
    montanteParaRemover.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(50), 3));

    Montante montanteResultado = montanteOriginal.remover(montanteParaRemover);

    assertEquals(BigDecimal.valueOf(110), montanteResultado.getValorTotal());
    final Collection<GrupoNotas> gruposNotas = montanteResultado.getGruposNotas();
    assertEquals(2, gruposNotas.size());
  }
}