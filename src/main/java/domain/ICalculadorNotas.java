package domain;

import java.math.BigDecimal;
import java.util.Collection;

public interface ICalculadorNotas {

  Collection<Montante> calcular(BigDecimal valorSaque);
}
