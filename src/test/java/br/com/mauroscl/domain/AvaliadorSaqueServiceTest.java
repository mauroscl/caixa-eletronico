package br.com.mauroscl.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import org.junit.Test;

public class AvaliadorSaqueServiceTest {

  private final IAvaliadorSaqueService avaliadorSaqueService;

  public AvaliadorSaqueServiceTest() {
    this.avaliadorSaqueService = new AvaliadorSaqueService(new CalculadorMinimoNotas());
  }

  @Test
  public void deveRetornarMontanteParaEntregarENovoMontanteDisponivelQuandoTiverNotas() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 5));
    assertTrue(this.avaliadorSaqueService.avaliar(BigDecimal.valueOf(50), montanteDisponivel).isPresent());
  }

  @Test
  public void deveRetornarResultadoVazioQuandoNaoTiverNotasSuficientes() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 5));
    assertFalse(this.avaliadorSaqueService.avaliar(BigDecimal.valueOf(15), montanteDisponivel).isPresent());
  }

  @Test
  public void deveRetornarResultadoVazioQuandoNaoTiverSaldoEmCaixaParaAtenderSolicitacao() {
    final Montante montanteDisponivel = new Montante();
    montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 5));
    assertFalse(this.avaliadorSaqueService.avaliar(BigDecimal.valueOf(100), montanteDisponivel).isPresent());
  }

}