-- Criação da tabela de equipes responsáveis pelos projetos
CREATE TABLE IF NOT EXISTS equipes (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(50) NOT NULL UNIQUE,
                                       criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela de status dos projetos e tarefas
CREATE TABLE IF NOT EXISTS status (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nome VARCHAR(50) NOT NULL UNIQUE,
                                      criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela de projetos
CREATE TABLE IF NOT EXISTS projetos (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        nome VARCHAR(100) NOT NULL UNIQUE ,
                                        descricao VARCHAR(255) NOT NULL,
                                        data_inicio DATE NOT NULL,
                                        data_fim DATE,
                                        equipe_id INT NOT NULL,
                                        status_id INT NOT NULL,
                                        criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        CONSTRAINT fk_equipe FOREIGN KEY (equipe_id) REFERENCES equipes (id),
                                        CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES status (id)
);

-- tabela responsavel
CREATE TABLE IF NOT EXISTS responsaveis (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            nome VARCHAR(50) NOT NULL UNIQUE
);

-- Criação da tabela de tarefas
CREATE TABLE IF NOT EXISTS tarefas (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       titulo VARCHAR(100) NOT NULL,
                                       descricao VARCHAR(255) NOT NULL,
                                       projeto_id INT NOT NULL,
                                       responsavel_id INT NOT NULL,
                                       prazo_dias INT NOT NULL,
                                       status_id INT NOT NULL,
                                       criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                                       CONSTRAINT fk_projeto FOREIGN KEY (projeto_id) REFERENCES projetos (id),
                                       CONSTRAINT fk_tarefa_status FOREIGN KEY (status_id) REFERENCES status (id),
                                       CONSTRAINT fk_tarefa_responsavel FOREIGN KEY (responsavel_id) REFERENCES responsaveis (id)
);

