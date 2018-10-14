package br.com.mauroscl.domain;

import java.math.BigDecimal;

interface ICalculadorNotas {

  Montante calcular(BigDecimal valorSaque, Montante montanteDisponivel);
}
