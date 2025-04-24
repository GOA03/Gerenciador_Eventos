# Sistema de Gerenciamento de Eventos

Este projeto é um sistema de gerenciamento de eventos que permite aos usuários se cadastrarem, realizarem login e gerenciarem seus eventos. O sistema foi desenvolvido em **Java**, utilizando **JDBC** para interação com o banco de dados **MySQL**, e conta com uma interface gráfica desenvolvida com **WindowBuilder**.

## Descrição do Projeto

O Sistema de Gerenciamento de Eventos é uma aplicação desenvolvida para permitir que administradores gerenciem eventos como palestras, workshops, conferências e outros tipos de encontros. O sistema também possibilita que participantes visualizem eventos disponíveis, realizem inscrições e acompanhem suas participações.

### Módulos do Sistema

#### 1. Módulo de Cadastro de Usuários
Responsável pelo cadastro e autenticação de usuários. Existem dois tipos de usuários: administradores e participantes.

##### Funcionalidades:
- Cadastro de novos usuários com validação de dados.
- Login e autenticação.
- Diferença de permissões baseada no tipo de usuário.

##### Dados Necessários:
- **Administradores**: id, nome completo, e-mail, senha, tipo de usuário (administrador ou participante), cargo e data da contratação.
- **Participantes**: id, nome completo, e-mail, senha, tipo de usuário (administrador ou participante), data de nascimento e CPF.

##### Regras:
- O e-mail deve ser único para cada usuário.
- Administradores e Participantes devem ter acessos diferenciados.

#### 2. Módulo Gerenciamento de Eventos
Permite que administradores criem, atualizem, visualizem e excluam eventos.

##### Funcionalidades:
- CRUD de eventos.
- Listagem de eventos abertos, encerrados ou cancelados.
- Modificar o status de um evento (fechado, aberto, encerrado ou cancelado).
- Controle da capacidade máxima de participantes.

##### Dados Necessários:
- **Eventos**: id, título, descrição, data e hora, duração (em horas), local (endereço ou link se o evento for online), capacidade máxima de participantes, status (fechado, aberto, encerrado ou cancelado), categoria (palestra, workshop ou conferência), preço (se o evento for pago), organizador (referência ao administrador responsável).

##### Regras:
- Ao criar um novo evento, este iniciará como status “fechado”.

#### 3. Módulo de Inscrições
Permite que participantes se inscrevam em eventos disponíveis e acompanhem suas inscrições.

##### Funcionalidades:
- Inscrição em eventos com controle de capacidade.
- Cancelamento de inscrição.
- Confirmação de presença.

##### Dados Necessários:
Para cada inscrição de um participante em um evento, deve-se armazenar:
- Data da inscrição.
- Status da inscrição (ativa, cancelada ou pendente de pagamento).
- Presença confirmada (sim ou não).

##### Regras:
- A capacidade máxima do evento não pode ser ultrapassada.
- As inscrições só poderão ser realizadas pelos participantes quando o status do evento estiver como “aberto”.
- O cancelamento da inscrição em um evento só poderá ser realizado pelo participante enquanto o status do evento estiver como “aberto” e antes do evento iniciar.

#### 4. Módulo Relatórios (Administradores)
Usado por administradores para gerar relatórios e obter informações sobre eventos e participações.

##### Funcionalidades:
- Relatório dos participantes que participaram de um determinado evento.
- Eventos mais populares (maior número de inscrições).
- Lista de todos os eventos que ainda não ocorreram, mostrando a capacidade restante de cada evento.
- Relatório detalhado de um determinado evento que ainda não ocorreu, mostrando os detalhes do evento, participantes inscritos e status de cada inscrição (ativas, canceladas, pendentes).

#### 5. Módulo Relatórios (Participantes)
Usado por participantes para gerar relatórios e obter informações sobre os eventos que está inscrito ou participou.

##### Funcionalidades:
- **Exportação**:
  - Listagem de eventos inscritos com todos os detalhes de cada evento.
  - Histórico de participação em eventos passados, contendo todos os detalhes de cada evento.

##### Observações:
Tanto administradores quanto participantes podem exportar os relatórios citados anteriormente em um arquivo (por exemplo, `.xls`) que será armazenado em disco.

## Funcionalidades Principais

1. **Cadastro de Usuários**:
   - Usuários podem se cadastrar com **nome**, **email**, **senha** e dados opcionais.
   - Validação de dados de entrada.

2. **Login de Usuários**:
   - Autenticação por e-mail e senha.

3. **Gerenciamento de Eventos**:
   - Criar, editar, visualizar e excluir eventos.
   - Cada evento possui data/hora, duração, local, capacidade máxima, status, categoria e preço.

4. **Inscrição em Eventos**:
   - Usuários participantes podem se inscrever nos eventos disponíveis.
   - Status da inscrição: ativa, cancelada ou pendente.

5. **CRUD de Usuários (Admin)**:
   - O **administrador** pode criar, editar, visualizar e excluir usuários.

6. **CRUD de Eventos (Admin)**:
   - O **administrador** pode criar, editar, visualizar e excluir eventos.

7. **Relatórios de Eventos e Inscrições**:
   - Tanto usuários comuns quanto administradores podem gerar relatórios.
   - Os relatórios podem incluir detalhes de inscrições, eventos passados e futuros.

8. **Interface Gráfica**:
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
