package br.com.mauroscl.domain;

public class AvaliacaoSaqueResultado {
  private final Montante montanteParaEntregar;
  private final Montante novoMontanteDisponivel;

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
