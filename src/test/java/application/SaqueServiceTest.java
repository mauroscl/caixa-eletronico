package application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.ICalculadorNotas;
import domain.GrupoNotas;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SaqueServiceTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private ISaqueService saqueService;
  private ICalculadorNotas calculadorNotas;

  public SaqueServiceTest() {
    this.calculadorNotas = mock(ICalculadorNotas.class);
    when(this.calculadorNotas.calcular(any(BigDecimal.class)))
        .thenReturn(Arrays.asList(new GrupoNotas(BigDecimal.TEN, 10)));
    this.saqueService = new SaqueService(this.calculadorNotas);
  }

  @Test
  public void deveRealizarSaqueComSucessoQuandoInformarValorValido() {
    BigDecimal valorSaque = BigDecimal.valueOf(100.00);
    SaqueCommand saqueCommand = new SaqueCommand(valorSaque);
    this.saqueService.sacar(saqueCommand);
    verify(this.calculadorNotas, times(1)).calcular(valorSaque);
    verify(this.calculadorNotas, times(1)).calcular(any(BigDecimal.class));
  }

  @Test
  public void naoDevePermitirRealizacaoSaqueQuandoInformarValorInvalido() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(SaqueService.VALOR_SAQUE_INVALIDO);

    SaqueCommand saqueCommand = new SaqueCommand(BigDecimal.valueOf(-100));
    this.saqueService.sacar(saqueCommand);
  }

  @Test
  public void naoDevePermitirRealizacaoSaqueQuandoNaoInformarValor() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(SaqueService.VALOR_SAQUE_INVALIDO);

    SaqueCommand saqueCommand = new SaqueCommand(null);
    this.saqueService.sacar(saqueCommand);
  }


  @Test
  public void naoDevePermitirRealizacaoSaqueQuandoInformarValorComMaisDeDuasCasasDecimais() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(SaqueService.CASAS_DECIMAIS_SAQUE_INVALIDO);

    SaqueCommand saqueCommand = new SaqueCommand(BigDecimal.valueOf(20.125));
    this.saqueService.sacar(saqueCommand);
  }



}