package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MontanteTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void deveCriarMontanteComValoresCorretos() {
    final Montante montante = new Montante(BigDecimal.valueOf(10), 20L);
    assertEquals(BigDecimal.valueOf(10), montante.getValorNota());
    assertTrue(montante.getQuantidade().equals(20L));
  }

  @Test
  public void deveCalcularValorTotalCorretamente() {
    final Montante montante = new Montante(BigDecimal.valueOf(10), 20L);
    assertEquals(BigDecimal.valueOf(200), montante.getValorTotal());
  }

  @Test
  public void doisMontantesDeMesmoValorDevemSerConsideradosIguaisIndependenteDaEscala() {
    final Montante montante1 = new Montante(BigDecimal.valueOf(10), 20L);
    final Montante montante2 = new Montante(BigDecimal.valueOf(10.00), 20L);
    assertEquals(montante1, montante2);
  }

  @Test
  public void naoDeveSerPermitidoCriarMontanteComValorDeNotaInvalido()  {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(Montante.VALOR_NOTA_INVALIDO);
    new Montante(BigDecimal.valueOf(-5), 10L);
  }

  @Test
  public void naoDeveSerPermitidoCriarMontanteComValorDeNotaNulo()  {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(Montante.VALOR_NOTA_INVALIDO);
    new Montante(null, 10L);
  }

  @Test
  public void naoDeveSerPermitidoCriarMontanteComQuantidadeInvalida() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(Montante.QUANTIDADE_NOTA_INVALIDO);
    new Montante(BigDecimal.valueOf(10), -10);
  }


}