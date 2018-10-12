package shared;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Test;

public class BigDecimalComparadorTest {

  @Test
  public void menorOuIgualQueDeveRetornarVerdadeiroQuandoValor1ForMenorQueValor2() {
    assertTrue(BigDecimalComparador.menorOuIgualQue(BigDecimal.ONE, BigDecimal.TEN));
  }

  @Test
  public void menorOuIgualQueDeveRetornarVerdadeiroQuandoValor1ForIgualAoValor2() {
    assertTrue(BigDecimalComparador.menorOuIgualQue(BigDecimal.TEN, BigDecimal.TEN));
  }

  @Test
  public void menorOuIgualQueDeveRetornarFalsoQuandoValor1ForMaiorQueValor2() {
    assertFalse(BigDecimalComparador.menorOuIgualQue(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void maiorOuIgualQueDeveRetornarVerdadeiroQuandoValor1ForMaiorQueValor2() {
    assertTrue(BigDecimalComparador.maiorOuIgualQue(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void maiorOuIgualQueDeveRetornarVerdadeiroQuandoValor1ForIgualAoValor2() {
    assertTrue(BigDecimalComparador.maiorOuIgualQue(BigDecimal.TEN, BigDecimal.TEN));
  }

  @Test
  public void maiorOuIgualQueDeveRetornarFalsoQuandoValor1ForMenorQueValor2() {
    assertFalse(BigDecimalComparador.maiorOuIgualQue(BigDecimal.ONE, BigDecimal.TEN));
  }


}