# Backend
Kero é um site criado por alunos de Ciência da Computação da UFT, idealizado pelo professor Delson, de Ciências Contábeis. O projeto visa aumentar a visibilidade de alunos do campus Palmas que vendem produtos ou serviços, facilitando a conexão entre vendedores e compradores. O backend utiliza Java, Spring Boot e Docker.

### Documentação da API Kero

---

## **Autenticação**
Endpoints relacionados à autenticação de usuários.

### **Login**
**POST** `/api/auth/login`

- **Parâmetros** (Body):
  - `email` (String): Email do usuário.
  - `password` (String): Senha do usuário.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Retorna os tokens de acesso e atualização.
  - Falha (`401 Unauthorized`): Credenciais inválidas.

---

### **Atualizar Token**
**POST** `/api/auth/refresh`

- **Parâmetros** (Body):
  - `refreshToken` (String): Token de atualização válido.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Retorna um novo token de acesso.
  - Falha (`401 Unauthorized`): Token inválido ou expirado.

---

### **Logout**
**POST** `/api/auth/logout`

- **Parâmetros** (Header):
  - `Authorization` (String): Token no formato Bearer.
- **Autenticação**: Necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Mensagem de sucesso no logout.
  - Falha (`400 Bad Request`): Token inválido ou ausente.

---

## **Recuperação de Senha**
Endpoints para recuperação ou redefinição de senhas.

### **Solicitar Recuperação de Senha**
**POST** `/api/recovery/recover-password`

- **Parâmetros** (Body):
  - `email` (String): Email do usuário.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Confirmação de envio do email de recuperação.

---

### **Redefinir Senha**
**POST** `/api/recovery/reset-password`

- **Parâmetros** (Body):
  - `token` (String): Token de recuperação.
  - `newPassword` (String): Nova senha.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Confirmação de redefinição de senha.

---

## **Produtos**
Endpoints relacionados à gestão de produtos.

### **Criar Produto**
**POST** `/api/produtos/new`

- **Parâmetros** (Form-data):
  - `produto` (JSON): Detalhes do produto.
  - `files` (Lista): Arquivos anexados.
  - `Authorization` (Header): Token no formato Bearer.
- **Autenticação**: Necessária.
- **Resposta**:
  - Sucesso (`201 Created`): Retorna o produto criado.
  - Falha (`400 Bad Request`, `500 Internal Server Error`).

---

### **Atualizar Produto**
**PATCH** `/api/produtos/atulizar-produto/{produtoId}`

- **Parâmetros**:
  - `produtoId` (Path): ID do produto.
  - `produtoRequest` (Body): Detalhes atualizados do produto.
- **Autenticação**: Necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Produto atualizado.
  - Falha (`400 Bad Request`, `500 Internal Server Error`).

---

### **Remover Produto**
**DELETE** `/api/produtos/remover/{produtoId}`

- **Parâmetros**:
  - `produtoId` (Path): ID do produto.
- **Autenticação**: Necessária.
- **Resposta**:
  - Sucesso (`204 No Content`): Confirmação de remoção.
  - Falha (`400 Bad Request`, `500 Internal Server Error`).

---

### **Buscar Todos os Produtos**
**GET** `/api/produtos`

- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Lista de produtos.
  - Falha (`500 Internal Server Error`).

---

### **Buscar Produto pelo Nome**
**GET** `/api/produtos/{nomeProduto}`

- **Parâmetros**:
  - `nomeProduto` (Path): Nome do produto.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Produtos correspondentes.
  - Falha (`500 Internal Server Error`).

---

### **Buscar Produto pela Tag**
**GET** `/api/produtos/tag/{nomeTag}`

- **Parâmetros**:
  - `nomeTag` (Path): Nome da tag.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Produtos correspondentes.
  - Falha (`500 Internal Server Error`).

---

## **Usuários**
Endpoints relacionados à gestão de usuários.

### **Cadastrar Usuário**
**POST** `/api/usuario/new`

- **Parâmetros** (Body):
  - Detalhes de registro do usuário.
- **Autenticação**: Não é necessária.
- **Resposta**:
  - Sucesso (`201 Created`): Objeto do usuário.
  - Falha (`400 Bad Request`).

---

### **Completar Cadastro**
**PATCH** `/api/usuario/completar-cadastro`

- **Parâmetros** (Form-data):
  - `file` (Arquivo): Arquivo do usuário.
  - `user` (JSON): Detalhes do usuário.
  - `Authorization` (Header): Token no formato Bearer.
- **Autenticação**: Necessária.
- **Resposta**:
  - Sucesso (`200 OK`): Usuário atualizado.
  - Falha (`400 Bad Request`, `500 Internal Server Error`).

---

### **Listar Todos os Usuários**
**GET** `/api/usuario/all`

- **Autenticação**: Apenas para administradores.
- **Resposta**:
  - Sucesso (`200 OK`): Lista de usuários.

---

### **Buscar Usuário por ID**
**GET** `/api/usuario/{usuarioId}`

- **Parâmetros**:
  - `usuarioId` (Path): ID do usuário.
- **Autenticação**: Apenas para administradores.
- **Resposta**:
  - Sucesso (`200 OK`): Objeto do usuário.
  - Falha (`404 Not Found`).

---

### **Excluir Usuário**
**DELETE** `/api/usuario/{usuarioId}`

- **Parâmetros**:
  - `usuarioId` (Path): ID do usuário.
- **Autenticação**: Apenas para administradores.
- **Resposta**:
  - Sucesso (`204 No Content`).
  - Falha (`404 Not Found`).
