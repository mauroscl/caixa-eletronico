package application;

import static java.util.Objects.isNull;

import domain.CalculoNotasResultado;
import domain.ICalculadorNotas;
import java.math.BigDecimal;
import shared.BigDecimalComparador;

class SaqueService implements ISaqueService {

  protected static final String VALOR_SAQUE_INVALIDO = "Valor solicitado para saque deve ser maior do que zero.";
  protected static final String CASAS_DECIMAIS_SAQUE_INVALIDO = "Valor solicitado para saque deve conter no máximo 2 dígitos decimais.";

  private final ICalculadorNotas calculadorNotas;
  private final ICaixaService caixaService;

  SaqueService(final ICalculadorNotas calculadorNotas, final ICaixaService caixaService) {
    this.calculadorNotas = calculadorNotas;
    this.caixaService = caixaService;
  }

  @Override
  public void sacar(SaqueCommand command) {
    if (isNull(command.getValor()) || BigDecimalComparador.menorOuIgualQue(command.getValor(), BigDecimal.ZERO)) {
      throw new IllegalArgumentException(VALOR_SAQUE_INVALIDO);
    }

    if (command.getValor().scale() >= 2) {
      throw new IllegalArgumentException(CASAS_DECIMAIS_SAQUE_INVALIDO);
    }

    final CalculoNotasResultado resultado = this.calculadorNotas.calcular(command.getValor());

    if(resultado.isEncontrouNotas()) {
      this.caixaService.entregarDinheiro(resultado.getMontante());
    } else {
      this.caixaService.avisarIndisponibilidade(command.getValor(), resultado.getMontante());
    }
  }

}
