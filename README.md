# APIREST de Cadastro de Usuários e Carros

Projeto desenvolvido com finalidade de apresentar o conhecimento de varias tecnologias.

## 🚀 Começando
Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Implantação](#-implanta%C3%A7%C3%A3o)** para saber como implantar o projeto.

### 📋 Pré-requisitos
Java17

Maven


### 🔧 Instalação

Voçê pode baixar o projeto abrindo o terminal de sua preferência e usando o comando, 

git clone https://github.com/drrqueiroz/desafiopitang-api.git 
ou acessar o link https://github.com/drrqueiroz/desafiopitang-api/tree/master para baixar o projeto. 
Navegue até a raiz do projeto "pasta que contém o arquivo pom.xml", e execute o sequinte comando.
```
mvn install
```
Utilize uma ide de desenvovimento como IntelliJ ou Eclipse para abrir o projeto e Postman ou Isomnia para realizar os testes.
Com o projeto aberto, start o mesmo para iniciar os testes, voce pode consultar os endpoint acessando o link 
http://localhost:8080/swagger-ui/index.html no seu navegador.

Exemplo:

```
Endpoint de cadastro de usuário: http://localhost:8080/api/users
Verbo: POST
```

```
Json para teste "copiar e colar no body do Postman ou Insomnia"
{
"firstName": "Hello",
"lastName": "World",
"email": "hello@world.com",
"birthday": "1990-05-01",
"login": "hello.world",
"password": "h3ll0",
"phone": "988888888",
}
```

```
Endpoint listar todos usuários cadastrados: http://localhost:8080/api/users
Verbo: GET
```

```
Json de retorno
{
"id": 1
"firstName": "Hello",
"lastName": "World",
"email": "hello@world.com",
"birthday": "1990-05-01",
"login": "hello.world",
"password": "h3ll0",
"phone": "988888888",
}
```
## ⚙️ Executando os testes
Se desejar realizar os test acesse o seu pronpt de commando apontando para a pasta raiz do projeto e digite o comando abaixo.
```
mvn test
```

### 🔩 Analise os testes de ponta a ponta

Os testes foram denvolvidos nas principais funçoes da API, e ajudaram na apresentação de possíveis erros

## 📦 Implantação

Para rodar localmente a API sem precisar utilizar uma IDE de desenvolvimento basta seguir as instruções abaixo
```
## Roda local a aplicação
## Abrir o terminal na raiz do projeto e realizar os seguintes comandos.

1. mvn clean package
2. Na pasta raiz do projeto procurar pela pasta target "cd ..\..\..\target" e localizar o arquivo .jar
3. Via prompt de commando na pasta aonde esta localizado o arquivo .jar do projeto digitar os seguinte commando "java -jar nome_arquivo.jar"
```

## 🛠️ Construído com


* [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/) - IDE de desenvolvimento
* [Maven](https://maven.apache.org/) - Gerente de Dependência

## 📌 Versão

Nós usamos [Git](https://github.com) para controle de versão. Para as versões disponíveis, observe as [tags neste repositório](https://github.com/drrqueiroz/desafiopitang-api).

## ✒️ Autores

* **Um desenvolvedor** - *Trabalho Inicial* - [Davidson Queiroz](https://github.com/drrqueiroz/desafiopitang-api)
* **Davidson Queiroz** - *Documentação* - [Davidson Queiroz](https://github.com/drrqueiroz/desafiopitang-api)


# HISTÓRIAS DO USUÁRIO

## PERSONA: USUÁRIO
### FUNCIONALIDADE 1: Autenticar no Sistema"

História 1.1: Como usuário, desejo acessar a tela de login para realizar a autenticaçao no sistema.

História 1.2: Como usuário, desejo acessar a tela de carro para cadastra meus carros

## PERSONA: USUÁRIO
### FUNCIONALIDADE 2: Cadastrar Usuário "Gerenciar Dados do Usuário"
### OBS: O usuário pode acessar este endpoint sem a necessidade de estar logado.

História 2.1: Como usuário, desejo acessar a tela de cadastro para inserir minhas informações pessoais.

História 2.2: Como usuário, desejo acessar a tela de perfil para visualizar e editar minhas informações pessoais.

História 2.3: Como usuário, desejo acessar a tela de perfil para remover minha conta, excluindo meus registros.

História 2.4: Como usuário, desejo acessar a tela de consulta para buscar e visualizar meus registros de usuário.

## PERSONA: USUÁRIO
### FUNCIONALIDADE 3: Cadastrar Carro "Gerenciar Informações do Carro"
#### OBS: O usuário precisa estar logado para acessar este endpoint.

História 3.1: Como usuário, desejo acessar a tela de cadastro de carro para adicionar informações sobre meu(s) veículo(s).

História 3.2: Como usuário, desejo acessar a tela de perfil do carro para editar detalhes específicos do veículo.

História 3.3: Como usuário, desejo acessar a tela de perfil do carro para excluir o registro de um veículo.

História 3.4: Como usuário, desejo acessar a tela de consulta de carro para encontrar e visualizar informações sobre meus veículos cadastrados.







