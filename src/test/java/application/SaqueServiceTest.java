package application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.CalculoNotasResultado;
import domain.GrupoNotas;
import domain.ICalculadorNotas;
import domain.Montante;
import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

public class SaqueServiceTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private ISaqueService saqueService;
  private ICalculadorNotas calculadorNotas;
  private ICaixaService caixaService;

  public SaqueServiceTest() {
    this.calculadorNotas = mock(ICalculadorNotas.class);
    final Montante montante = new Montante();
    montante.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 10));
    final CalculoNotasResultado retorno = new CalculoNotasResultado(true, montante);
    when(this.calculadorNotas.calcular(any(BigDecimal.class))).thenReturn(retorno);

    this.caixaService = mock(ICaixaService.class);
    when(this.caixaService.entregarDinheiro(any(Montante.class))).thenReturn(true);
    doNothing().when(this.caixaService).avisarIndisponibilidade(any(BigDecimal.class), any(Montante.class));

    this.saqueService = new SaqueService(this.calculadorNotas, caixaService);
  }

  @Test
  public void deveRealizarSaqueComSucessoQuandoInformarValorValido() {
    BigDecimal valorSaque = BigDecimal.valueOf(100.00);
    SaqueCommand saqueCommand = new SaqueCommand(valorSaque);
    this.saqueService.sacar(saqueCommand);
    verify(this.calculadorNotas, times(1)).calcular(valorSaque);
    verify(this.calculadorNotas, times(1)).calcular(any(BigDecimal.class));

    verify(this.caixaService, times(1)).entregarDinheiro(any(Montante.class));
  }

  @Test
  public void deveAvisarIndisponibilidadeQuandoNaoHouverDinheiroParaAtenderSolicitacao() {
    final CalculoNotasResultado retorno = new CalculoNotasResultado(false, new Montante());
    when(this.calculadorNotas.calcular(any(BigDecimal.class))).thenReturn(retorno);

    BigDecimal valorSaque = BigDecimal.valueOf(100.00);
    SaqueCommand saqueCommand = new SaqueCommand(valorSaque);
    this.saqueService.sacar(saqueCommand);

    verify(this.caixaService, times(1)).avisarIndisponibilidade(any(BigDecimal.class), any(Montante.class));
    verify(this.caixaService, never()).entregarDinheiro(any(Montante.class));
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