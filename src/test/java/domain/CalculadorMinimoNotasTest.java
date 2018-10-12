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
        .asList(new Montante(Nota.comValor(10), 1L), new Montante(Nota.comValor(1), 5L),
            new Montante(Nota.comValor(2), 1L));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays
        .asList(new Montante(Nota.comValor(10), 1L), new Montante(Nota.comValor(2), 1L));

    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(BigDecimal.valueOf(12));

    assertTrue(notasEsperadas.containsAll(notasDisponiveis));
    assertTrue(notasDisponiveis.containsAll(notasEsperadas));
  }

  @Test
  public void deveDisponilizarSaqueQuandoPuderAtenderComAsNotasExistentes() {
    List<Montante> montanteDisponivel = Arrays
        .asList(new Montante(Nota.comValor(10), 1L), new Montante(Nota.comValor(5), 2L));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays
        .asList(new Montante(Nota.comValor(10), 1L), new Montante(Nota.comValor(5), 2L));

    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(BigDecimal.valueOf(20));

    assertTrue(notasEsperadas.containsAll(notasDisponiveis));
    assertTrue(notasDisponiveis.containsAll(notasEsperadas));

  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoNaoTiverNotasParaAtenderValorIntegral() {
    List<Montante> montanteDisponivel = Arrays
        .asList(new Montante(Nota.comValor(10), 1L), new Montante(Nota.comValor(5), 1L));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    BigDecimal valorSolicitado = BigDecimal.valueOf(11);
    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(valorSolicitado);

    BigDecimal valorDisponivel = notasDisponiveis.stream().map(m -> m.getValorTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    assertNotEquals(valorSolicitado, valorDisponivel);

  }

  @Test
  public void devePermitirSacarCentavosQuandoDisponivel() {
    List<Montante> montanteDisponivel = Arrays
        .asList(new Montante(Nota.comValor(BigDecimal.valueOf(0.25)), 4L));
    when(carregadorNotas.obterDisponiveis()).thenReturn(montanteDisponivel);

    List<Montante> notasEsperadas = Arrays
        .asList(new Montante(Nota.comValor(BigDecimal.valueOf(0.25)), 3L));

    BigDecimal valorSolicitado = BigDecimal.valueOf(0.75);
    Collection<Montante> notasDisponiveis = this.calculadorNotas.calcular(valorSolicitado);

    assertTrue(notasEsperadas.containsAll(notasDisponiveis));
    assertTrue(notasDisponiveis.containsAll(notasEsperadas));
  }

}