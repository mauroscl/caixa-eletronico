package domain;

public class CalculoNotasRetorno {
  private boolean encontrouNotas;
  private Montante montante;

  public CalculoNotasRetorno(final boolean encontrouNotas, final Montante montante) {
    this.encontrouNotas = encontrouNotas;
    this.montante = montante;
  }

  public boolean isEncontrouNotas() {
    return encontrouNotas;
  }

  public Montante getMontante() {
    return montante;
  }
}
