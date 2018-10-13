package domain;

import java.math.BigDecimal;
import java.util.Optional;

public interface IAvaliadorSaqueService {
  Optional<AvaliacaoSaqueResultado>  avaliar(BigDecimal valorSaque, Montante montanteDisponivel);
}
