package application;

import static java.util.Objects.isNull;

import domain.IAvaliadorSaqueService;
import domain.IMontanteRepository;
import domain.Montante;
import java.math.BigDecimal;
import shared.BigDecimalComparador;

class SaqueService implements ISaqueService {

  protected static final String VALOR_SAQUE_INVALIDO = "Valor solicitado para saque deve ser maior do que zero.";
  protected static final String CASAS_DECIMAIS_SAQUE_INVALIDO = "Valor solicitado para saque deve conter no máximo 2 dígitos decimais.";

  private final IMontanteRepository montanteRepository;
  private final IAvaliadorSaqueService avaliadorSaqueService;
  private final ICaixaService caixaService;

  SaqueService(final IMontanteRepository montanteRepository,
      final IAvaliadorSaqueService avaliadorSaqueService, final ICaixaService caixaService) {
    this.montanteRepository = montanteRepository;
    this.avaliadorSaqueService = avaliadorSaqueService;
    this.caixaService = caixaService;
  }

  @Override
  public void sacar(SaqueCommand command) {
    if (isNull(command.getValor()) || BigDecimalComparador
        .menorOuIgualQue(command.getValor(), BigDecimal.ZERO)) {
      throw new IllegalArgumentException(VALOR_SAQUE_INVALIDO);
    }

    if (command.getValor().scale() >= 2) {
      throw new IllegalArgumentException(CASAS_DECIMAIS_SAQUE_INVALIDO);
    }

    final Montante montanteDisponivel = this.montanteRepository.obterDisponivel();

    this.avaliadorSaqueService.avaliar(command.getValor(), montanteDisponivel)
        .ifPresentOrElse(resultado -> {
          this.caixaService.entregarDinheiro(resultado.getMontanteParaEntregar());
          this.montanteRepository.salvar(resultado.getNovoMontanteDisponivel());
        }, () -> this.caixaService.avisarIndisponibilidade());
  }

}
