package domain;

import java.math.BigDecimal;

public interface ICalculadorNotas {

  Montante calcular(BigDecimal valorSaque, Montante montanteDisponivel);
}
