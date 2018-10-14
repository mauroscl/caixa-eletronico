# caixa-eletronico
Algoritmo que calcula as notas de uma operação de saque utilizando o menor número de notas possíveis

**Problema inicial**

Desenvolva um programa que simule a entrega de notas quando um cliente efetuar um saque em um caixa eletrônico. Os requisitos básicos são os seguintes:

Entregar o menor número de notas;
É possível sacar o valor solicitado com as notas disponíveis;
Saldo do cliente infinito;
Quantidade de notas infinito (pode-se colocar um valor finito de cédulas para aumentar a dificuldade do problema);
Notas disponíveis de R$ 100,00; R$ 50,00; R$ 20,00 e R$ 10,00
Exemplos:

Valor do Saque: R$ 30,00 – Resultado Esperado: Entregar 1 nota de R$20,00 e 1 nota de R$ 10,00.
Valor do Saque: R$ 80,00 – Resultado Esperado: Entregar 1 nota de R$50,00 1 nota de R$ 20,00 e 1 nota de R$ 10,00.

**Solução implementada**

* As notas não ficaram restritas apenas às notas do enunciado. Podem ser utilizadadas qualquer nota com valor maior do que zero.
São permitidas inclusive notas em centavos. Sendo assim, a solução ficaria extensível caso fossem emitidas notas de outros valores, 
notas fossem tiradas de circulação, ou até mesmo para o caso de haver um caixa que também permita sacar moedas.
* A solução foi implementada com quantidade de notas finita. Portanto, pode haver três tipos de resposta: valor indisponível, valor disponível mas não com as mesmas notas de uma solução com dinheiro infinito, valor disponível com o menor número de notas possível.
* Quando for possível atender à solicitação de saque foi implementado também o cálculo do novo montante disponível para saque, 
removendo do montante inicial as notas que serão entregues ao cliente.

**Solução técnica**

 A solução implementada não é uma aplicação completa. As classes foram testadas isoladamente e usado o mecanismo de _mock_ para simular o comportamento das dependências quando necessário.
 
 A idéia para uma aplicação completa seria organizar o código conforme a sugestão de camadas do DDD: UI, aplicação, infra-estrutra, domínio.
 
 Neste código temos apenas aplicação e domínio. Para as implementações que pertenceriam à camada de infra-estrutura foram declaradas apenas interfaces 
 e utilizado o recurso de mocks para simular os seus comportamentos.
 São elas:
 * _IMontanteRepository_: contratos para carregar e atualizar o montante de notas disponíveis
 * _ICaixaService_: contratos de comunicação com o caixa eletrônico para entregar o dinheiro ou avisar que não tem disponibilidade.
 
 Não foi implementada nenhuma UI, mas qualquer UI implementada deverá chamar o método sacar da classe SaqueService, pois este é o método que coordena 
 as ações necessárias para efetuar o saque.

**Aplicação**

Responsável por validar a entrada de dados e coordenar as ações necessárias para atender o caso de uso de saque, delegando para o domínio à validação das regras de negócio.
Foi colocada na aplicação a validação do valor solicitado para saque.  

**Domínio**

Responsável por calcular o mínimo de notas para atender à solicitação de saque e também por calcular o novo montante disponível.
Se o cliente não tivesse saldo infinito, colocaríamos no domínio a verificação se o cliente tem saldo em conta para poder sacar o valor solicitado.

**Modelagem**

Existem duas classes principais:
* _GrupoNotas_: responsável por conter a quantidade de cada tipo de nota disponível. Além disso, contém operações para somar e substrair com outro grupo de notas. 
Como GrupoNotas é um objeto de valor (imutável), estas operações geram uma nova instância de GrupoNotas
* _Montante_: contém uma coleção de GrupoNotas. Utilizada tanto para representar o montante total disponível no caixa, quanto o montante a ser entregue para o cliente. 
Contém a operação _**remover**_, utilizada para calcular o novo montante disponível
   
Existem dois serviços principais: 
* _CalculadorMinimoNotas_: responsável por calcular a quantidade mínima de notas para atender a solicitação.
* _AvaliadorSaqueService_: responsável por coordenar o cálculo do mínimo de notas e por calcular o novo montante disponível para o próximo saque. 
Importante ressaltar que este serviço recebe como parâmetro a interface ICalculadorNotas. Então se houvesse uma mudança de estratégia, que não fosse 
mais entregar o mínimo de notas e fosse, por exemplo, balancear o número de notas para tentar manter no caixa sempre notas de todos os valores, 
poderia ser criada uma nova implementação dessa interface e substituir o CalculadorMinimoNotas. 