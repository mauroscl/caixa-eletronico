package br.com.mauroscl.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class CalculadorMinimoNotasTest {

  private final ICalculadorNotas calculadorNotas;

  public CalculadorMinimoNotasTest() {
    this.calculadorNotas = new CalculadorMinimoNotas();
  }

  @Test
  public void deveDisponibilizarSaqueComMinimoNotasQuandoTiverTodasNotasDisponiveis() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(5), 2));
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(10), 1));

    final Collection<GrupoNotas> notasEsperadas = Arrays
        .asList(new GrupoNotas(BigDecimal.valueOf(20), 1), new GrupoNotas(BigDecimal.valueOf(10), 1));

    final BigDecimal valorSaque = BigDecimal.valueOf(30);

    final Montante montanteEntregue = this.calculadorNotas.calcular(valorSaque, montanteDisponivel);
    assertEquals(valorSaque, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

  @Test
  public void deveDisponilizarSaqueQuandoPuderAtenderComAsNotasExistentesMesmoQueNaoSejaDaManeiraMaisOtimizada() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(5), 6));

    /*
    * A maneira mais otimizada seria uma nota de 20 e uma de 10 se fossem notas infinitas,
    * mas como não temos notas de 10, deve retornar uma de 20 e duas de 5.
    * */
    List<GrupoNotas> notasEsperadas = Arrays
        .asList(new GrupoNotas(BigDecimal.valueOf(20), 1), new GrupoNotas(BigDecimal.valueOf(5), 2));

    final BigDecimal valorSaque = BigDecimal.valueOf(30);

    Montante montanteEntregue = this.calculadorNotas.calcular(valorSaque, montanteDisponivel);
    assertEquals(valorSaque, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoNaoTiverMontanteNoCaixaParaAtenderValorIntegral() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 1));

    final BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    final Montante montanteCalculado = this.calculadorNotas.calcular(valorSolicitado, montanteDisponivel);
    assertNotEquals(valorSolicitado, montanteCalculado.getValorTotal());
  }

  @Test
  public void deveRetornarSaqueIndisponivelQuandoTiverMontanteMasNaoTiverAsNotasParaAtenderSolicitacao() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(20), 2));

    final BigDecimal valorSolicitado = BigDecimal.valueOf(30);
    final Montante montanteCalculado = this.calculadorNotas.calcular(valorSolicitado, montanteDisponivel);

    assertNotEquals(valorSolicitado, montanteCalculado.getValorTotal());
  }

  @Test
  public void devePermitirSacarCentavosQuandoDisponivel() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.valueOf(0.25), 4));

    List<GrupoNotas> notasEsperadas = Collections
        .singletonList(new GrupoNotas(BigDecimal.valueOf(0.25), 3));

    BigDecimal valorSolicitado = BigDecimal.valueOf(0.75);
    Montante montanteEntregue = this.calculadorNotas.calcular(valorSolicitado, montanteDisponivel);
    assertEquals(valorSolicitado, montanteEntregue.getValorTotal());

    final Collection<GrupoNotas> notasEntregues = montanteEntregue.getGruposNotas();
    assertTrue(notasEsperadas.containsAll(notasEntregues));
    assertTrue(notasEntregues.containsAll(notasEsperadas));
  }

}