<html lang="pt-br">
<body>
  <h1>Sistema de Gerenciamento de Pedidos</h1>

  <p>Este é um sistema de gerenciamento de pedidos desenvolvido em Java usando Spring Boot.</p>

  <h2>Visão Geral</h2>

  <p>O Sistema de Gerenciamento de Pedidos permite aos usuários criar e visualizar, além de gerenciar os itens associados a cada pedido.</p>

  <p><strong>Principais funcionalidades:</strong></p>
  <ul>
      <li>Ao criar um Pedido ele vai devolver na resposta a quantidade total de itens naquele json que você enviou e o peso total em gramas de todos os item juntos.</li>
      <li>Criar pedidos com informações detalhadas, as caixas, os itens que pertencem a caixa e os pesos de acordo com cada item.</li>
      <li>Visualizar detalhes de pedidos, incluindo informações sobre os itens associados e as caixas associadas.</li>
  </ul>

  <h2>Instalação e Configuração</h2>

  <ol>
      <li><strong>Pré-requisitos</strong>:
          <ul>
              <li>JDK 17 ou superior instalado</li>
              <li>Maven 3.x ou superior instalado</li>
              <li>Banco de dados MySQL (ou outro banco de dados suportado pelo Spring Boot)</li>
          </ul>
      </li>
        <li><strong>Clonar o repositório</strong>:
            <code>git clone https://github.com/viniciuslemes10/Teste-Mult.git</code>
        </li>
      <li><strong>Configuração do Banco de Dados</strong>:
          <ul>
              <li>Crie um banco de dados MySQL com o nome <code>pedidos_api</code>.</li>
              <li>Configure as credenciais do banco de dados no arquivo <code>application.properties</code>.</li>
          </ul>
      </li>
      <li><strong>Executar a Aplicação</strong>:
          <ol>
              <li>Navegue até o diretório do projeto</li>
              <li>Execute o seguinte comando:
                  <code>mvn spring-boot:run</code>
              </li>
          </ol>
      </li>
  </ol>

  <p>A aplicação estará disponível em <code>http://localhost:8080</code>.</p>

  <h2>Uso</h2>

  <ul>
      <li><strong>Criar um Pedido - Caso aconteça algum erro de digitação no json ele irá alertar e não vai cadastrar o produto. Outro ponto também importante, caso haja 
      algum erro de colocar o "sku" ou a "qtd" inválidos, aquele produto que tiver errado não vai ser cadastrado</strong>:
        <ul>
            <li><code>POST /pedidos</code></li>
            <li>Corpo da solicitação:
              <br>
              <br>
                <pre>
                     "pedidos": {
                          "pedido1": {
                              "caixa1": [{"sku":"pendrive","qtd":3}],
                              "caixa2": [{"sku":"mouse","qtd":2},{"sku":"pendrive","qtd":2}]
                          }
                      }
                      "pesoPorSku": {
                          "pendrive": 50,
                          "mouse": 210
                      }
                  </pre>
            </li>
          <br>
          <li>Ele devolve a quantidade de itens e a soma total do peso em gramas.</li>
          <li>Resposta:
            <br>
            <br>
              <pre>
                Pedido Criado. Total de Produtos: 7, Peso Total: 670.0 gramas
              </pre>
          </li>
          
  <br>
        </ul>
        <ul>
            </li>
            <li><strong>Listar Itens - Ele vai trazer as informações de cada item contendo sku, quantidade total daquele item e o peso dele</strong>:</li> 
              <li><code>GET /pedidos</code></li>
              <li>Corpo da solicitação:
                <br>
                <br>
                    <pre>
                         [
                            {
                                "pesoTotal": 10.0,
                                "quantidadeTotal": 2,
                                "sku": "mouse"
                            },
                            {
                                "pesoTotal": 10.0,
                                "quantidadeTotal": 5,
                                "sku": "pendrive"
                            }
                          ]
                      </pre>
              </li>
          </ul>

  <ul>
      <br>     
  <li><strong>Listar Detalhamento de Pedidos - Ele vai trazer um json contendo o id do pedido, as caixas que pertencem a este pedido, a descrição dos itens como no exemplo são os (peso do item, o sku(nome), quantidade de itens
  em cada caixa e o id do item)</strong>:</li> 
  <li><code>GET /pedidos/detalhes</code>
      <li>Corpo da solicitação:
        <br>
        <br>
         <pre>
              [
                  {
                      "itens": [
                          {
                              "caixa_id": 1,
                              "peso_por_sku": 50.0,
                              "sku": "pendrive",
                              "quantidade": 3,
                              "item_pedido_id": 1
                          },
                          {
                              "caixa_id": 2,
                              "peso_por_sku": 210.0,
                              "sku": "mouse",
                              "quantidade": 2,
                              "item_pedido_id": 2
                          },
                          {
                              "caixa_id": 2,
                              "peso_por_sku": 50.0,
                              "sku": "pendrive",
                              "quantidade": 2,
                              "item_pedido_id": 3
                          }
                      ],
                      "pedido_id": 1
                  }
            ]
        </pre>
     </li>

  <br>
  <li><strong>Total de Peso e Quantidade de Itens - Mostra o total de peso em gramas, quantidade de itens que tem na base de dados </strong>:</li> 
  <li><code>GET /pedidos/total-peso-quantidade</code>
     <li>Corpo da solicitação:
       <br>
       <br>
         <pre>
           {
                "quantidade_total": 74,
                "peso_total_gramas": 31280.0
           }
         </pre>
     </li>
  <br>
  <li><strong>Total de Peso e Quantidade por Caixa - Neste endpoint mostra o id de cada caixa, a quantidade de itens que contém na caixa, os nomes dos skus que estão naquela caixa e o peso daquela caixa em gramas</strong>:</li> 
  
  <li><code>GET /pedidos/pedidos/total-peso-quantidade-por-caixa</code>
     <li>Corpo da solicitação:
       <br>
       <br>
         <pre>
          [
                {
                    "caixa_id": 1,
                    "quantidade_total": 3,
                    "skus": "pendrive",
                    "peso_total_gramas": 150.0
                },
                {
                    "caixa_id": 2,
                    "quantidade_total": 4,
                    "skus": "mouse,pendrive",
                    "peso_total_gramas": 520.0
                }
          ]
         </pre>
     </li>
     <br>
    <li><strong>Total de Peso e Quantidade por Pedido - Neste endpoint mostra o id de cada pedido, a quantidade de itens que contém no pedido, os nomes dos skus que estão naquela pedido, o peso daquela pedido em gramas e os ids das caixas que pertencem aquele pedido</strong>:</li> 
  
  <li><code>GET /pedidos/total-peso-quantidade-por-pedido</code>
     <li>Corpo da solicitação:
       <br>
       <br>
         <pre>
             [
                  {
                      "caixas_id": "1,2",
                      "quantidade_total_itens": 7,
                      "pedido_id": 1,
                      "skus_itens": "pendrive,mouse,pendrive",
                      "peso_total_pedido": 670.0
                  },
                  {
                      "caixas_id": "3",
                      "quantidade_total_itens": 6,
                      "pedido_id": 2,
                      "skus_itens": "pendrive,mouse,keyboard",
                      "peso_total_pedido": 1980.0
                  }
            ]
         </pre>
     </li>
  </ul>
  
  

  <h2>Contribuição</h2>

  <p>Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests com melhorias, correções de bugs ou novas funcionalidades.</p>
</body>
</html>
