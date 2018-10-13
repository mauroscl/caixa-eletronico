package domain;

public interface IMontanteRepository {

  Montante obterDisponivel();
  void salvar(Montante montante);
}
