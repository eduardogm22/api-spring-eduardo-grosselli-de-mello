# 📘 Documentação da API - Cadastro de Produtos e Usuários

---
## 🚀 Como Utilizar
1. Crie um banco de dados no PostgreSQL
2. Configure o arquivo `.env` com os dados do seu banco de dados (usuário, senha, nome do banco, hostname e porta).
3. Rode a aplicação 
4. A API ficará disponível em http://localhost:8080

---
## 🔑 Autenticação
- Formato: Basic Auth
- Todas as rotas são autenticadas, exceto a de cadastro.
---
## 🌐 Endpoints 
> Collection do Postman: [Clique aqui para acessar]( https://web.postman.co/workspace/My-Workspace~b6675b87-cd40-4eff-8973-bbe06d0d9a3a/collection/44013103-2353ddbf-f14d-4814-acf7-245e8ead47ee?action=share&source=copy-link&creator=44013103 )

## 👤 Usuários

### ➕ Cadastrar Usuário
- **POST** `/usuario/cadastro`
- Envio:
```json
{
    "username": "username",
    "senha" : "senha"
}
```
- Retorno:
```json
{
    "username": "username",
    "id" : 1
}
```

### 🔍 Listar Todos Usuários
- **GET** `/usuario`
- Retorno:
```json
[
    {
        "username": "username",
        "id": 1
    },
    {
        "username": "username2",
        "id": 2
    },
    {
        "username": "username3",
        "id": 3
    }
]
```
### 🔍 Buscar Usuário por ID
- **GET** `/usuario/{id}`
- Retorno:
```json
{
  "username": "username3",
  "id": 1
}
```

### ✏️ Atualizar Usuário
- **PUT** `/usuario`
```json
{
    "id" : 1,
    "username": "novo username",
    "senha" : "senha"
}
```
- Retorno:
```json
{
    "username": "novo username",
    "id" : 1
}
```
### ❌ Deletar Usuário
- **DELETE** `/usuario/{id}`
- Retorno: 204 NO_CONTENT

## ⚠️ Possíveis Erros
- **400 Bad Request** → 
```json
{
  "strMensagem": "Erro no campo: username - Erro: tamanho deve ser entre 5 e 100"
  }
```
```json
{
  "strMensagem": "Erro no campo: senha - Erro: tamanho deve ser entre 5 e 100"
  }
```
- **404 Not Found** → 
```json
{
    "strMensagem": "Não encontrados usuários com id: 30"
}
```
- **409 Conflict** →
```json
{
  "strMensagem": "Nome de usuário já em uso!"
}
```
- **401 Unauthorized** → retorna caso o usuário/ senha não estejam cadastrados
  no banco de dados, ou não foram informados na requisição.
---
## 📦 Produtos
### ➕ Cadastrar Produto
- **POST** `/produto`
```json
{
  "nome": "Leite UTH Integral",
  "descricao": "Leite em caixinha de 1 litro",
  "valorUnitario": 5.00,
  "qtdEmEstoque": 12,
  "dtValidade": "30/10/2025"
}
```
- Retorno:
```json
{
  "id": 1,
  "nome": "Leite UTH Integral",
  "descricao": "Leite em caixinha de 1 litro",
  "valorUnitario": 5.00,
  "qtdEmEstoque": 12,
  "dtValidade": "30/10/2025"
}
```
### 🔍 Listar Todos Produtos
- **GET** `/produto`
- Retorno: 
```json
[
    {
        "id": 1,
        "nome": "Leite UTH Integral",
        "descricao": "Leite em caixinha de 1 litro",
        "valorUnitario": 5.00,
        "qtdEmEstoque": 12,
        "dtValidade": "30/10/2025"
    },
    {
        "id": 2,
        "nome": "Notebook Dell Inspiron 15",
        "descricao": "Notebook com processador i7, 16GB RAM e SSD 512GB",
        "valorUnitario": 4500.00,
        "qtdEmEstoque": 10,
        "dtValidade": null
    },
    {
        "id": 3,
        "nome": "Notebook Acer azpire 55",
        "descricao": "Notebook com processador i5, 16GB RAM e SSD 512GB",
        "valorUnitario": 4000.00,
        "qtdEmEstoque": 10,
        "dtValidade": null
    }
]
```

### 🔍 Buscar Produto por ID
- **GET** `/produto/{id}`
- Retorno
```json
{
  "id": 1,
  "nome": "Leite UTH Integral",
  "descricao": "Leite em caixinha de 1 litro",
  "valorUnitario": 5.00,
  "qtdEmEstoque": 12,
  "dtValidade": "30/10/2025"
}
```

### ✏️ Atualizar Produto
- **PUT** `/produto`
```json
{
  "id" : "3",
  "nome": "Notebook Acer Aspire 5",
  "descricao": "Notebook com processador i5, 16GB RAM e SSD 512GB",
  "valorUnitario": 4000.00,
  "qtdEmEstoque": 10,
  "dtValidade": null
}
```
- Retorno:
```json
{
  "id": 3,
  "nome": "Notebook Acer Aspire 5",
  "descricao": "Notebook com processador i5, 16GB RAM e SSD 512GB",
  "valorUnitario": 4000.00,
  "qtdEmEstoque": 10,
  "dtValidade": null
}
```
### ❌ Deletar Produto
- **DELETE** `/produto/{id}`
- Retorno: 204 NO_CONTENT

## ⚠️ Possíveis Erros
- **400 Bad Request** →
```json
{
  "strMensagem": "Erro no campo: nome - Erro: tamanho deve ser entre 5 e 100"
}
```
```json
{
  "strMensagem": "Erro no campo: qtdEmEstoque - Erro: não deve ser nulo"
}
```
- **404 Not Found** →
```json
{
  "strMensagem": "Não encontrado nenhum produto com id: 34"
}
```
- **409 Conflict** →
```json
{
  "strMensagem": "Produto já cadastrado!"
}
```
- **401 Unauthorized** → retorna caso o usuário/ senha não estejam cadastrados 
no banco de dados, ou não foram informados na requisição.
---

