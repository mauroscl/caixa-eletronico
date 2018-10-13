package domain;

import java.math.BigDecimal;

public interface ICalculadorNotas {

  CalculoNotasResultado calcular(BigDecimal valorSaque);
}
