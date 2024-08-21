
# Case ITAU - CRUD de PIX 

O Pix é o arranjo de pagamentos e recebimentos instantâneos, disponível todos os dias do ano, com liquidação em tempo real de suas transações. Ou seja, permite a transferência imediata de valores entre diferentes instituições, em todos os horários e dias, entre pessoas físicas, empresas ou entes governamentais.
O Pix é uma forma de pagar e receber valores.
Chave Pix: um apelido para a conta transacional que deve ser atribuído pelo titular da conta ou representante legal/operador permissionado, usado para identificar a conta corrente do cliente recebedor por meio de uma única


## Possiveis Retorno
```

POST: /casepix/v1/cadastrar
{
  "tipoChave": "cpf",
  "valorChave": "24774502049",
  "tipoConta": "corrente",
  "numeroAgencia": "1227",
  "numeroConta": "123477",
  "nomeCorrentista": "teste",
  "sobreNomeCorrentista":"teste"
} 

{
  "tipoChave": "celular",
  "valorChave": "+5511985196640",
  "tipoConta": "corrente",
  "numeroAgencia": "1227",
  "numeroConta": "13354",
  "nomeCorrentista": "fulano",
  "sobreNomeCorrentista":"fulano"
} 

PUT

casepix/v1/atualizar/4f89bb8d-62cf-454f-a465-87f8d35a9e52

Body
{
  "numeroAgencia": "1233",
  "numeroConta": "12335677",
  "nomeCorrentista": "teste teste",
  "sobreNomeCorrentista":"teste"
}

Retorno
{
    "id": "4f89bb8d-62cf-454f-a465-87f8d35a9e52",
    "tipoChave": "cpf",
    "valorChave": "48937414899",
    "tipoConta": null,
    "numeroAgencia": 1233,
    "numeroConta": 12335677,
    "nomeCorrentista": "teste teste",
    "sobreNomeCorrentista": "teste",
    "dataRegistro": "21-08-2024 12:26:43",
    "dataInclusao": "2024-08-21 12:26:43.7727225",
    "dataInativacao": null,
    "inativa": false
}


GET

casepix/v1/cosultar?inomeCorrentista=joaozinho
 
 nesse caso filtrando pelo nome

 [
    {
        "id": "ac32eab8-3624-4602-9ffe-1384697460eb",
        "tipoChave": "celular",
        "valorChave": "+551198746640",
        "tipoConta": "administrativo",
        "numeroAgencia": 1227,
        "numeroConta": 12234477,
        "nomeCorrentista": "joaozinho",
        "sobreNomeCorrentista": "jesus",
        "dataRegistro": "21-08-2024 12:32:00",
        "dataInclusao": "2024-08-21 12:32:00.9804493",
        "dataInativacao": null,
        "inativa": false
    },
    {
        "id": "669230b7-21ae-44ad-94d3-051aac5b084c",
        "tipoChave": "cpf",
        "valorChave": "28625583032",
        "tipoConta": "corrente",
        "numeroAgencia": 1227,
        "numeroConta": 34477,
        "nomeCorrentista": "joaozinho",
        "sobreNomeCorrentista": "jesus",
        "dataRegistro": "21-08-2024 12:34:23",
        "dataInclusao": "2024-08-21 12:34:23.9779131",
        "dataInativacao": null,
        "inativa": false
    },
    {
        "id": "df72d2ce-d4f4-4181-8a8f-ddb71a471e44",
        "tipoChave": "cpf",
        "valorChave": "66501751020",
        "tipoConta": "corrente",
        "numeroAgencia": 1227,
        "numeroConta": 1234477,
        "nomeCorrentista": "joaozinho",
        "sobreNomeCorrentista": "jesus",
        "dataRegistro": "21-08-2024 12:34:56",
        "dataInclusao": "2024-08-21 12:34:56.2470979",
        "dataInativacao": null,
        "inativa": false
    },
    {
        "id": "578b006f-f1f1-4e26-bad9-c374b662023e",
        "tipoChave": "cpf",
        "valorChave": "24774502049",
        "tipoConta": "corrente",
        "numeroAgencia": 1227,
        "numeroConta": 123477,
        "nomeCorrentista": "joaozinho",
        "sobreNomeCorrentista": "jesus",
        "dataRegistro": "21-08-2024 12:35:12",
        "dataInclusao": "2024-08-21 12:35:12.8937373",
        "dataInativacao": null,
        "inativa": false
    }
]
```
## Deploy

Para fazer o deploy desse projeto rode

```bash
  mvn run deploy

  Local: -Dspring.profile.active=local
```


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn run test
```


## Autor

- [@linkedin ](https://www.linkedin.com/in/joaociardullo/)

