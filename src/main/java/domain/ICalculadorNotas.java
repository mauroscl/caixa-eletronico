package domain;

import java.math.BigDecimal;
import java.util.Collection;

public interface ICalculadorNotas {

  CalculoNotasRetorno calcular(BigDecimal valorSaque);
}
