-- Inserir equipes responsáveis
INSERT INTO equipes (nome) VALUES
                               ('ADMFIN'),
                               ('ADMPLN'),
                               ('ADMAPO');

-- Inserir status de projetos e tarefas
INSERT INTO status (nome) VALUES
                              ('Planejado'),
                              ('Em execução'),
                              ('Abortado'),
                              ('Finalizado');


INSERT INTO responsaveis (nome) VALUES
                                    ('PLO'),
                                    ('GFU'),
                                    ('CTB'),
                                    ('GBP');


-- 4.
INSERT INTO projetos (nome, descricao, data_inicio, data_fim, equipe_id, status_id)
VALUES ('projeto teste', 'descricao teste', '2012-05-11', '2012-05-11', 1, 1);

-- 5.
INSERT INTO tarefas (titulo, descricao, projeto_id, responsavel_id, prazo_dias, status_id)
VALUES ('título teste', 'título teste', 1, 1, 10, 1);