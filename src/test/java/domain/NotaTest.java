package domain;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Test;

public class NotaTest {

  @Test
  public void deveCriarNotaComValor() {
    Nota nota = Nota.comValor(50);
    assertTrue(BigDecimal.valueOf(50.00).compareTo(nota.getValor()) == 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void naoDevePermitirCriarNotaComUmValorNegativo() {
    Nota.comValor(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void naoDevePermitirCriarNotaComUmValorZero() {
    Nota.comValor(BigDecimal.ZERO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void naoDevePermitirCriarNotaComUmValorNulo() {
    Nota.comValor(null);
  }


}