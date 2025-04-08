# AuthAPI 🔐

Uma API REST de autenticação desenvolvida com Spring Boot, Hibernate e Spring Security. Oferece funcionalidades de login com verificação de licença (`Key`) e criptografia de senhas.

---

## 🚀 Funcionalidades

- Registro de usuários
- Login com autenticação segura (hash de senha)
- Verificação de licença (data de expiração e uso)
- Integração com banco de dados usando Spring Data JPA
- Proteção com Spring Security

---

## 🧱 Estrutura do Projeto

```
com.cc.authapi
├── application
│   └── KeyService.java
│   └── UserService.java
├── config
│   └── PasswordConfig.java
│   └── SecurityConfig.java
├── controllers
│   └── AuthController.java
├── domain
│   ├── User.java
│   └── Key.java
├── repository
│   ├── IUserRepository.java
│   └── IKeyRepository.java
└── AuthApiApplication.java
```

---

## 📦 Requisitos

- Java 24+
- Maven ou Gradle
- Banco de dados (MySQL, PostgreSQL, etc.)
- Spring Boot 3+
- Hibernate ORM
- Spring Security

---

## ⚙️ Configuração

1. Clone o repositório:

```bash
  git clone git@github.com:caiocollete/authapi.git
  cd authapi
```

2. Configure o `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth
spring.datasource.username=postgres
spring.datasource.password=postgres123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

3. Instale dependências e rode o projeto:

```bash
    ./mvnw spring-boot:run
```

---

## 🧪 Endpoints

### PUT `/login`
Autentica um usuário:

```json
{
  "username": "admin",
  "password": "123456"
}
```

Respostas:
- ✅ 200: Login com sucesso
- ❌ 400: Credenciais inválidas ou licença expirada

---

### POST `/register`
Registra um usuário:

```json
{
  "email": "email@email.com",
  "username": "admin",
  "password": "123456",
  "key": {
    "id": "<UUID>"
  }
}
```

Respostas:
- ✅ 200: Registrado com sucesso
- ❌ 400: Credenciais ou licença inválidas

---

### POST `/gen`
Gera um token:

```"?time=<7d>"```

Respostas:
- ✅ 200: Token gerado com sucesso
- ❌ 400: Formato invalido ou Erro ao gerar a chave

---

### GET `/users`
Obtém lista de usuários:

Respostas:
- ✅ 200: Token gerado com sucesso
- ❌ 400: Formato invalido ou Erro ao gerar a chave

---

## 💾 Database Scheme
```aiignore
CREATE TABLE public.keys (
    id uuid NOT NULL,
    expires timestamp(6) without time zone NOT NULL,
    use boolean NOT NULL
);
```
```aiignore
CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    last_request_date timestamp(6) without time zone NOT NULL,
    username character varying(255) NOT NULL,
    key_id uuid
);
```

---

## Proximos passos

🔐 1. JWT Authentication
Pra gerar tokens de acesso e proteger endpoints (login → recebe token, token → acessa recursos).

📦 2. DTOs (Data Transfer Objects)
Evitar expor entidades diretamente nos endpoints (ex: UserRegisterRequest, UserLoginRequest, UserResponse).

🧪 3. Validações com Bean Validation
Anotações como @NotNull, @Email, @Size, etc. usando @Valid nos controllers.

🧼 4. Padronizar respostas com uma classe ApiResponse


---

## 📄 Licença

Projeto com fins educacionais. Sinta-se livre para modificar e utilizar como quiser.

---

❤ [Caio Collete](https://github.com/caiocollete)
