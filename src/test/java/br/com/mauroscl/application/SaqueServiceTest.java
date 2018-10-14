package br.com.mauroscl.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.mauroscl.domain.AvaliacaoSaqueResultado;
import br.com.mauroscl.domain.GrupoNotas;
import br.com.mauroscl.domain.IAvaliadorSaqueService;
import br.com.mauroscl.domain.IMontanteRepository;
import br.com.mauroscl.domain.Montante;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SaqueServiceTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  private final ISaqueService saqueService;
  private final IMontanteRepository montanteRepository;
  private final IAvaliadorSaqueService avaliadorSaqueService;
  private final ICaixaService caixaService;
  private final Montante montanteDisponivel;

  public SaqueServiceTest() {

    this.montanteDisponivel = new Montante();
    this.montanteDisponivel.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 30));
    this.montanteRepository = mock(IMontanteRepository.class);
    when(this.montanteRepository.obterDisponivel()).thenReturn(montanteDisponivel);

    this.avaliadorSaqueService = mock(IAvaliadorSaqueService.class);

    this.caixaService = mock(ICaixaService.class);

    this.saqueService = new SaqueService(this.montanteRepository, this.avaliadorSaqueService, this.caixaService);
  }

  @Test
  public void deveRealizarSaqueComSucessoQuandoHouverDisponibilidadeDeNotas() {

    final Montante montanteParaEntregar = new Montante();
    montanteParaEntregar.adicionarGrupo(new GrupoNotas(BigDecimal.TEN, 10));
    final AvaliacaoSaqueResultado avaliacaoSaqueResultado = new AvaliacaoSaqueResultado(
        montanteParaEntregar, mock(Montante.class));

    when(this.avaliadorSaqueService.avaliar(any(BigDecimal.class), any(Montante.class)))
        .thenReturn(Optional.of(avaliacaoSaqueResultado));

    BigDecimal valorSaque = BigDecimal.valueOf(100.00);
    SaqueCommand saqueCommand = new SaqueCommand(valorSaque);
    this.saqueService.sacar(saqueCommand);

    verify(this.avaliadorSaqueService, times(1)).avaliar(valorSaque, this.montanteDisponivel);
    verify(this.avaliadorSaqueService, times(1)).avaliar(any(BigDecimal.class), any(Montante.class));

    verify(this.caixaService, times(1)).entregarDinheiro(any(Montante.class));
    verify(this.montanteRepository, times(1)).salvar(any(Montante.class));
  }

  @Test
  public void deveAvisarIndisponibilidadeQuandoNaoHouverDinheiroParaAtenderSolicitacao() {
    when(this.avaliadorSaqueService.avaliar(any(BigDecimal.class), any(Montante.class)))
        .thenReturn(Optional.empty());

    BigDecimal valorSaque = BigDecimal.valueOf(100.00);
    SaqueCommand saqueCommand = new SaqueCommand(valorSaque);
    this.saqueService.sacar(saqueCommand);

    verify(this.caixaService, times(1)).avisarIndisponibilidade();
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