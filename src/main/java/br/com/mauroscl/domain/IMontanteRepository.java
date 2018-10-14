package br.com.mauroscl.domain;

public interface IMontanteRepository {

  Montante obterDisponivel();
  void salvar(Montante montante);
}
