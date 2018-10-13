package domain;

public class AvaliacaoSaqueResultado {
  private Montante montanteParaEntregar;
  private Montante novoMontanteDisponivel;

  public AvaliacaoSaqueResultado(final Montante montanteParaEntregar, final Montante novoMontanteDisponivel) {
    this.montanteParaEntregar = montanteParaEntregar;
    this.novoMontanteDisponivel = novoMontanteDisponivel;
  }

  public Montante getMontanteParaEntregar() {
    return montanteParaEntregar;
  }

  public Montante getNovoMontanteDisponivel() {
    return novoMontanteDisponivel;
  }


}
