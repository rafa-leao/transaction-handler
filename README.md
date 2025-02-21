# Transaction Handler

Este projeto é um sistema de autorização de transações que permite a criação de cartões, consulta de saldo e autorização de transações.

## Pré Requisito

Tenha Docker e Docker Compose configurado e o projeto deverá rodar normalmente.

O shell script disponibilizado é suficiente para iniciar ou finalizar a execução da aplicação.

Permita que o script seja executado pela CLI

- ``` chmod -x deploy.sh ```

Inicie os servidores Spring Boot e MySql

- ``` ./deploy ```

Finalize
- ``` ./deploy --off ```

## Considerações

### Desafios

O projeto está configurado com Pessimistic Concurrency Control.

Essa tecnologia garante o bloqueio da entidade até o fim da transação vigente. Assim que a mesma for finalizada, a próxima transação da fila será executada.

Leituras e escritas entrarão em espera até que uma transação seja finalizada.

Essa configuração garante dados consistentes mesmo em ambientes com concorrência.

O projeto não contém condicional IF a não ser no script shell para realizar o deploy local.

### Estrutura do projeto 

Os dominios principais são: Cartão e Transação. Apenas os dados do cartão é persistido.

No diretório principal onde contém os arquivos Java, há um diretório para cada domínio e um para configurações.

Os diretórios de domíno possuem organização própria para cada contexto. As camadas encontradas são: exception, modelos, repository, interfaces de serviço e serviço implementador.

No diretório pai em relação aos domínios, está a camada controladora onde estão as chamadas API REST.

Os dois domínios dividem o mesmo espaço na controladora, são poucas opções de manipulação. Se faz conveniente mantê-los juntos enquanto o escopo é pequeno.

Nos testes, a camada de controladora é a única a ser dividida afim de manter a legibilidade e manutenabilidade.
