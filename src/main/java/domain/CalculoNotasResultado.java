package domain;

public class CalculoNotasResultado {
  private boolean encontrouNotas;
  private Montante montante;

  public CalculoNotasResultado(final boolean encontrouNotas, final Montante montante) {
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
