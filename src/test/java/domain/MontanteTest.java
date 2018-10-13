package domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import org.junit.Test;

public class MontanteTest {

  @Test
  public void deveCalcularValorTotalMontante() {
    Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(2), 5));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 3));
    assertEquals(BigDecimal.valueOf(40), montante.getValorTotal());
  }
}