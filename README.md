# Sistema de Gerenciamento de Eventos

Este projeto é um sistema de gerenciamento de eventos que permite aos usuários se cadastrarem, realizarem login e gerenciarem seus eventos inscritos. O sistema foi desenvolvido em **Java**, utilizando **JDBC** para interação com o banco de dados **MySQL**, e conta com uma interface gráfica desenvolvida com **WindowBuilder**.

## Funcionalidades Principais

1. **Cadastro de Usuários**
   - Usuários podem se cadastrar com **nome**, **email**, **senha** e demais dados opcionais.
   - Validação de dados de entrada.

2. **Login de Usuários**
   - Autenticação por email e senha.

3. **Gerenciamento de Eventos**
   - Criar, editar, visualizar e excluir eventos.
   - Cada evento possui data/hora, duração, local, capacidade máxima, status, categoria e preço.

4. **Inscrição em Eventos**
   - Usuários participantes podem se inscrever nos eventos disponíveis.
   - Status da inscrição: ativa, cancelada ou pendente.

5. **CRUD de Usuários (Admin)**
   - O **administrador** pode criar, editar, visualizar e excluir usuários.

6. **CRUD de Eventos (Admin)**
   - O **administrador** pode criar, editar, visualizar e excluir eventos.

7. **Relatórios de Eventos e Inscrições**
   - Tanto usuários comuns quanto administradores podem gerar relatórios.
   - Os relatórios podem incluir detalhes de inscrições, eventos passados e futuros.

8. **Interface Gráfica**
   - Desenvolvida com **WindowBuilder**.
   - Telas para cadastro, login, gerenciamento de eventos e inscrições.

## Tecnologias Utilizadas

- **Java**
- **JDBC**
- **MySQL**
- **WindowBuilder**

## Estrutura de Diretórios

```
/src
  /dao
    BancoDados.java
    EventoDAO.java
    UsuarioDAO.java
  /entities
    Evento.java
    Usuario.java
  /service
    CadastroUsuarioService.java
    EventoService.java
    LoginService.java
  /userinterfaces
    CadastroView.java
    LoginView.java
    EventoView.java
    EventosView.java
    Teste.java
```

## Script SQL de Criação do Banco de Dados

```sql
-- Criando o banco de dados e selecionando-o
CREATE DATABASE IF NOT EXISTS gerenciadoreventos;
USE gerenciadoreventos;

-- Criando a tabela de eventos
CREATE TABLE eventos (
  id INT(11) NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(255) NOT NULL,
  descricao TEXT DEFAULT NULL,
  data_hora DATETIME NOT NULL,
  duracao_horas INT(11) NOT NULL,
  local VARCHAR(255) DEFAULT NULL,
  capacidade_maxima INT(11) NOT NULL,
  status ENUM('FECHADO','ABERTO','ENCERRADO','CANCELADO') DEFAULT 'FECHADO',
  categoria ENUM('PALESTRA','WORKSHOP','CONFERENCIA') NOT NULL,
  preco DECIMAL(10,2) DEFAULT 0.00,
  organizador_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  KEY organizador_id (organizador_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando a tabela de inscrições
CREATE TABLE inscricoes (
  id INT(11) NOT NULL AUTO_INCREMENT,
  participante_id INT(11) NOT NULL,
  evento_id INT(11) NOT NULL,
  data_inscricao DATETIME DEFAULT CURRENT_TIMESTAMP(),
  status_inscricao ENUM('ATIVA','CANCELADA','PENDENTE') DEFAULT 'ATIVA',
  PRIMARY KEY (id),
  UNIQUE KEY participante_evento (participante_id, evento_id),
  KEY evento_id (evento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando a tabela de usuários
CREATE TABLE usuarios (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nome_completo VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  tipo_usuario ENUM('ADMINISTRADOR','PARTICIPANTE') NOT NULL,
  cargo VARCHAR(255) DEFAULT NULL,
  data_contratacao DATE DEFAULT NULL,
  data_nascimento DATE DEFAULT NULL,
  cpf VARCHAR(14) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando chaves estrangeiras
ALTER TABLE eventos
  ADD CONSTRAINT eventos_organizador_fk FOREIGN KEY (organizador_id) REFERENCES usuarios (id) ON DELETE CASCADE;

ALTER TABLE inscricoes
  ADD CONSTRAINT inscricoes_participante_fk FOREIGN KEY (participante_id) REFERENCES usuarios (id) ON DELETE CASCADE,
  ADD CONSTRAINT inscricoes_evento_fk FOREIGN KEY (evento_id) REFERENCES eventos (id) ON DELETE CASCADE;

COMMIT;
```

---

Caso deseje expandir esse projeto com autenticação JWT, API REST com Spring Boot ou front-end em Angular, basta solicitar! 🚀
