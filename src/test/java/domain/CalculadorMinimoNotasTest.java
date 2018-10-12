package domain;

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
  public void deveDisponibilizarSaqueMinimoNotasQuandoTiverTodasNotasDisponiveis() {
    List<Montante> montanteDisponivel = Arrays
        .asList(new Montante(BigDecimal.valueOf(20), 1), new Montante(BigDecimal.valueOf(5), 2),
            new Montante(BigDecimal.valueOf(10), 1));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays
        .asList(new Montante(BigDecimal.valueOf(20), 1), new Montante(BigDecimal.valueOf(10), 1));

    Collection<Montante> notasEntregues = this.calculadorNotas.calcular(BigDecimal.valueOf(30));

    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

  @Test
  public void deveDisponilizarSaqueQuandoPuderAtenderComAsNotasExistentesMesmoQueNaoSejaDaManeiraMaisOtimizada() {
    List<Montante> montanteDisponivel = Arrays
        .asList(new Montante(BigDecimal.valueOf(20), 1), new Montante(BigDecimal.valueOf(5), 6));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays
        .asList(new Montante(BigDecimal.valueOf(20), 1), new Montante(BigDecimal.valueOf(5), 2));

    Collection<Montante> notasEntregues = this.calculadorNotas.calcular(BigDecimal.valueOf(30));

    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));

  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoNaoTiverMontanteNoCaixaParaAtenderValorIntegral() {
    List<Montante> montanteDisponivel = Arrays.asList(new Montante(BigDecimal.valueOf(20), 1));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(valorSolicitado);

    BigDecimal valorDisponivel = notasDisponiveis.stream().map(m -> m.getValorTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    assertNotEquals(valorSolicitado, valorDisponivel);

  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoTiverMontanteMasNaoTiverAsNotasParaAtenderSolicitacao() {

    List<Montante> montanteDisponivel = Arrays.asList(new Montante(BigDecimal.valueOf(20), 2));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(valorSolicitado);

    BigDecimal valorDisponivel = notasDisponiveis.stream().map(m -> m.getValorTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    assertNotEquals(valorSolicitado, valorDisponivel);

  }

  @Test
  public void devePermitirSacarCentavosQuandoDisponivel() {
    List<Montante> montanteDisponivel = Arrays.asList(new Montante(BigDecimal.valueOf(0.25), 4));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays.asList(new Montante(BigDecimal.valueOf(0.25), 3));

    BigDecimal valorSolicitado = BigDecimal.valueOf(0.75);
    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(valorSolicitado);

    assertTrue(notasEsperadas.containsAll(notasDisponiveis));
    assertTrue(notasDisponiveis.containsAll(notasEsperadas));
  }

}