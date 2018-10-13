package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GrupoNotasTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void deveCriarGrupoNotasComValoresCorretos() {
    final GrupoNotas grupoNotas = new GrupoNotas(BigDecimal.valueOf(10), 20);
    assertEquals(BigDecimal.valueOf(10), grupoNotas.getValor());
    assertTrue(grupoNotas.getQuantidade() == 20);
  }

  @Test
  public void deveCalcularValorTotalDoGrupoCorretamente() {
    final GrupoNotas grupoNotas = new GrupoNotas(BigDecimal.valueOf(10), 20);
    assertEquals(BigDecimal.valueOf(200), grupoNotas.getValorTotal());
  }

  @Test
  public void duasNotasDeMesmoValorDevemSerConsideradosIguaisIndependenteDaEscala() {
    final GrupoNotas grupoNotas1 = new GrupoNotas(BigDecimal.valueOf(10), 1);
    final GrupoNotas grupoNotas2 = new GrupoNotas(BigDecimal.valueOf(10.00), 1);
    assertEquals(grupoNotas1, grupoNotas2);
  }

  @Test
  public void naoDevePermitirInformarValidoInvalidoParaNota()  {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.VALOR_NOTA_INVALIDO);
    new GrupoNotas(BigDecimal.valueOf(-5), 10);
  }

  @Test
  public void naoDevePermitirInformarValorNuloParaNota()  {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.VALOR_NOTA_INVALIDO);
    new GrupoNotas(null, 10L);
  }

  @Test
  public void naoDeveSerPermitirCriarGrupoComQuantidadeInvalida() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.QUANTIDADE_NOTA_INVALIDO);
    new GrupoNotas(BigDecimal.valueOf(10), -10);
  }

  @Test
  public void deveSomarGruposDeNotasDeMesmoValor() {
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 3);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.TEN, 5);
    final GrupoNotas novoGrupo = grupo1.somar(grupo2);
    assertEquals(new GrupoNotas(BigDecimal.TEN, 8), novoGrupo);
  }

  @Test
  public void naoDevePermirSomarGruposDeNotasDeValoresDiferentes() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.SOMA_VALORES_DIFERENTES);
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 3);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.ONE, 5);
    grupo1.somar(grupo2);
  }

  @Test
  public void deveSubtrairGruposDeNotasDeMesmoValor() {
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 5);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.TEN, 3);
    final Optional<GrupoNotas> possivelGrupo = grupo1.subtrair(grupo2);
    assertTrue(possivelGrupo.isPresent());
    final GrupoNotas novoGrupo = possivelGrupo.get();
    assertEquals(new GrupoNotas(BigDecimal.TEN, 2), novoGrupo);
  }

  @Test
  public void quandoQuantidadesForemIguaisDeveRetornarGrupoVazio() {
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 5);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.TEN, 5);
    final Optional<GrupoNotas> novoGrupo = grupo1.subtrair(grupo2);
    assertFalse(novoGrupo.isPresent());
  }

  @Test
  public void naoDevePermirSubtrairGruposDeNotasDeValoresDiferentes() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.SUBTRACAO_VALORES_DIFERENTES);
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 3);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.ONE, 5);
    grupo1.subtrair(grupo2);
  }

  @Test
  public void naoDevePermirSubtrairQuandoQuantidadeDoSegundoGrupoForMaiorQueDoPrimeiro() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(GrupoNotas.QUANTIDADE_NOTA_INVALIDO);
    final GrupoNotas grupo1 = new GrupoNotas(BigDecimal.TEN, 3);
    final GrupoNotas grupo2 = new GrupoNotas(BigDecimal.TEN, 5);
    grupo1.subtrair(grupo2);
  }



}