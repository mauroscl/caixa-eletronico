package application;

import domain.Montante;
import java.math.BigDecimal;

public interface ICaixaService {
  void entregarDinheiro(Montante montante);
  void avisarIndisponibilidade();
}
