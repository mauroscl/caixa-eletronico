package br.com.mauroscl.shared;

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

  @Test
  public void igualDeveRetornarVerdadeiroQuandoValor1ForIgualValor2() {
    assertTrue(BigDecimalComparador.igual(BigDecimal.TEN, BigDecimal.TEN));
  }

  @Test
  public void igualDeveRetornarFalsoQuandoValor1ForMenorQueValor2() {
    assertFalse(BigDecimalComparador.igual(BigDecimal.ONE, BigDecimal.TEN));
  }

  @Test
  public void igualDeveRetornarFalsoQuandoValor1ForMaiorQueValor2() {
    assertFalse(BigDecimalComparador.igual(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void diferenteDeveRetornarVerdadeiroQuandoValor1ForMenorQueValor2() {
    assertTrue(BigDecimalComparador.diferente(BigDecimal.ONE, BigDecimal.TEN));
  }

  @Test
  public void diferenteDeveRetornarVerdadeiroQuandoValor1ForMaiorQueValor2() {
    assertTrue(BigDecimalComparador.diferente(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void diferenteDeveRetornarFalsoQuandoValor1ForIgualValor2() {
    assertFalse(BigDecimalComparador.diferente(BigDecimal.TEN, BigDecimal.TEN));
  }

  @Test
  public void maiorDeveRetornarVerdadeiroQuandoValor1ForMaiorQueValor2() {
    assertTrue(BigDecimalComparador.maiorQue(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void maiorDeveRetornarFalsoQuandoValor1ForIgualValor2() {
    assertFalse(BigDecimalComparador.maiorQue(BigDecimal.TEN, BigDecimal.TEN));
  }

  @Test
  public void maiorDeveRetornarFalsoQuandoValor1ForMenorQueValor2() {
    assertFalse(BigDecimalComparador.maiorQue(BigDecimal.ONE, BigDecimal.TEN));
  }

}