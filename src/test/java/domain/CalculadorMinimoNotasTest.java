package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

public class CalculadorMinimoNotasTest {

  private ICalculadorNotas calculadorNotas;
  private ICarregadorNotas carregadorNotas;

  public CalculadorMinimoNotasTest() {
    this.carregadorNotas = mock(ICarregadorNotas.class);
    this.calculadorNotas = new CalculadorMinimoNotas(this.carregadorNotas);
  }

  @Test
  public void deveDisponibilizarSaqueComMinimoNotasQuandoTiverTodasNotasDisponiveis() {
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(5), 2));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(10), 1));

    when(carregadorNotas.obterDisponiveis()).thenReturn(montante);

    final Collection<GrupoNotas> notasEsperadas = Arrays
        .asList(new GrupoNotas(BigDecimal.valueOf(20), 1), new GrupoNotas(BigDecimal.valueOf(10), 1));

    final BigDecimal valorSaque = BigDecimal.valueOf(30);
    final Montante montanteEntregue = this.calculadorNotas.calcular(valorSaque);
    assertEquals(valorSaque, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

  @Test
  public void deveDisponilizarSaqueQuandoPuderAtenderComAsNotasExistentesMesmoQueNaoSejaDaManeiraMaisOtimizada() {
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(5), 6));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montante);

    List<GrupoNotas> notasEsperadas = Arrays
        .asList(new GrupoNotas(BigDecimal.valueOf(20), 1), new GrupoNotas(BigDecimal.valueOf(5), 2));

    final BigDecimal valorSaque = BigDecimal.valueOf(30);
    Montante montanteEntregue = this.calculadorNotas.calcular(valorSaque);
    assertEquals(valorSaque, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoNaoTiverMontanteNoCaixaParaAtenderValorIntegral() {
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montante);

    BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    Montante montanteCalculado = this.calculadorNotas.calcular(valorSolicitado);

    assertNotEquals(valorSolicitado, montanteCalculado.getValorTotal());
  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoTiverMontanteMasNaoTiverAsNotasParaAtenderSolicitacao() {
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 2));

    when(carregadorNotas.obterDisponiveis()).thenReturn(montante);

    BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    Montante montanteCalculado = this.calculadorNotas.calcular(valorSolicitado);

    assertNotEquals(valorSolicitado, montanteCalculado.getValorTotal());
  }

  @Test
  public void devePermitirSacarCentavosQuandoDisponivel() {
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(0.25), 4));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montante);

    List<GrupoNotas> notasEsperadas = Arrays.asList(new GrupoNotas(BigDecimal.valueOf(0.25), 3));

    BigDecimal valorSolicitado = BigDecimal.valueOf(0.75);
    Montante montanteEntregue = this.calculadorNotas.calcular(valorSolicitado);
    assertEquals(valorSolicitado, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

}