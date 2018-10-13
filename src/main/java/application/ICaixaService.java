package application;

import domain.Montante;
import java.math.BigDecimal;

public interface ICaixaService {
  boolean entregarDinheiro(Montante montante);
  void avisarIndisponibilidade(BigDecimal valorSocilitado, Montante montanteDisponivel);
}
