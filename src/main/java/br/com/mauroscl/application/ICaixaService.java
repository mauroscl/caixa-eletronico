package br.com.mauroscl.application;

import br.com.mauroscl.domain.Montante;

interface ICaixaService {
  void entregarDinheiro(Montante montante);
  void avisarIndisponibilidade();
}
