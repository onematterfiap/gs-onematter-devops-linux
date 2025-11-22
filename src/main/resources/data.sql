-- ============================================================================
-- MASSA DE DADOS COMPLETA E HARMONIZADA (5 Questões x 5 Alternativas)
-- ASSUME: ALTERNATIVA_1 é sempre a correta (Para o ESP32)
-- ============================================================================

-- CANDIDATOS (Usuários) - IDs 1..3
INSERT INTO TB_ONEM_CANDIDATO (id_candidato, dt_criacao, deleted, nm_candidato, ds_email, ds_senha_hash, tp_usuario, cpf, dt_nascimento, genero, nr_telefone)
VALUES (1, SYSDATE, 0, 'Administrador Master', 'admin@onematter.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 'ADMIN', '11122233344', TO_DATE('1980-01-01', 'YYYY-MM-DD'), 'MASCULINO', '11900001111');

INSERT INTO TB_ONEM_CANDIDATO (id_candidato, dt_criacao, deleted, nm_candidato, ds_email, ds_senha_hash, tp_usuario, cpf, dt_nascimento, genero, nr_telefone)
VALUES (2, SYSDATE, 0, 'Usuário Comum Teste', 'user@onematter.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 'USER', '22233344455', TO_DATE('1995-05-20', 'YYYY-MM-DD'), 'FEMININO', '21900002222');

INSERT INTO TB_ONEM_CANDIDATO (id_candidato, dt_criacao, deleted, nm_candidato, ds_email, ds_senha_hash, tp_usuario, cpf, dt_nascimento, genero, nr_telefone)
VALUES (3, SYSDATE, 0, 'Candidato Real', 'candidato@onematter.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 'USER', '33344455566', TO_DATE('2000-10-10', 'YYYY-MM-DD'), 'OUTRO', '31900003333');

-- EMPRESAS - IDs 1..3
INSERT INTO TB_ONEM_EMPRESA (id_empresa, dt_criacao, deleted, nm_empresa, nr_telefone, endereco, cnpj, email)
VALUES (1, SYSDATE, 0, 'Tech Solutions Ltda', '1155554444', 'Av. Tecnologia, 100', '00001111222233', 'empresa@tech.com');

INSERT INTO TB_ONEM_EMPRESA (id_empresa, dt_criacao, deleted, nm_empresa, nr_telefone, endereco, cnpj, email)
VALUES (2, SYSDATE, 0, 'Alpha Consultoria Global', '2133332222', 'Rua Alfa, 500', '11112222333344', 'rh@alpha.com');

INSERT INTO TB_ONEM_EMPRESA (id_empresa, dt_criacao, deleted, nm_empresa, nr_telefone, endereco, cnpj, email)
VALUES (3, SYSDATE, 0, 'Beta Digital Innov.', '3144445555', 'Av. Beta, 1000', '22223333444455', 'contato@beta.com');

-- RECRUTADORES - IDs 1..3
INSERT INTO TB_ONEM_RECRUTADOR (id_recrutador, dt_criacao, deleted, nm_recrutador, nr_telefone, cpf, email, senha_hash, id_empresa)
VALUES (1, SYSDATE, 0, 'Ana Recrutadora', '11988887777', '99988877766', 'ana@tech.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 1);

INSERT INTO TB_ONEM_RECRUTADOR (id_recrutador, dt_criacao, deleted, nm_recrutador, nr_telefone, cpf, email, senha_hash, id_empresa)
VALUES (2, SYSDATE, 0, 'João Recrutador', '21977776666', '88877766655', 'joao@alpha.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 2);

INSERT INTO TB_ONEM_RECRUTADOR (id_recrutador, dt_criacao, deleted, nm_recrutador, nr_telefone, cpf, email, senha_hash, id_empresa)
VALUES (3, SYSDATE, 0, 'Carla Tech Hunter', '31966665555', '77766655544', 'carla@beta.com', '$2a$10$6Ra0FhM5OIJ2RG8tfx3tWOtm0meVQsYbYr5CHra45/PMZkUGDMYN6', 3);

-- SKILLS - IDs 1..5
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (1, SYSDATE, 0, 'Java', 'Linguagem');
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (2, SYSDATE, 0, 'SQL', 'Banco de Dados');
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (3, SYSDATE, 0, 'Spring', 'Framework');
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (4, SYSDATE, 0, 'JavaScript', 'Linguagem');
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (5, SYSDATE, 0, 'UX/UI', 'Design');
INSERT INTO TB_ONEM_SKILL (id_skill, dt_criacao, deleted, nm_skill, categoria) VALUES (6, SYSDATE, 0, 'DevOps', 'Infra');

-- VAGAS EXISTENTES (Foco nas 3 principais)
INSERT INTO TB_ONEM_VAGA (id_vaga, dt_criacao, deleted, ds_vaga, id_empresa, id_recrutador) VALUES (1, SYSDATE, 0, 'Vaga para Desenvolvedor Backend (Sênior)', 1, 1);
INSERT INTO TB_ONEM_VAGA (id_vaga, dt_criacao, deleted, ds_vaga, id_empresa, id_recrutador) VALUES (2, SYSDATE - 2, 0, 'Vaga para Engenheiro Front-end (Pleno)', 2, 2);
INSERT INTO TB_ONEM_VAGA (id_vaga, dt_criacao, deleted, ds_vaga, id_empresa, id_recrutador) VALUES (3, SYSDATE - 5, 0, 'Engenheiro DevOps Sênior', 3, 3);


-- ========================================================
-- QUESTÕES (5x5 - Alternativa 1 é a correta)
-- ========================================================

-- Q1 (Backend/Geral): Threads
INSERT INTO TB_ONEM_QUESTAO (id_questao, nv_dificuldade, enunciado_questao, alternativa_1, alternativa_2, alternativa_3, alternativa_4, alternativa_5)
VALUES (1, 3, 'Qual método Java usamos para esperar a conclusão de uma Thread?',
    'join()',
    'wait()',
    'sleep()',
    'pause()',
    'yield()');

-- Q2 (Geral): SOLID
INSERT INTO TB_ONEM_QUESTAO (id_questao, nv_dificuldade, enunciado_questao, alternativa_1, alternativa_2, alternativa_3, alternativa_4, alternativa_5)
VALUES (2, 4, 'Qual princípio do SOLID exige que classes base possam ser substituídas por suas derivadas?',
    'Liskov Substitution Principle (LSP)',
    'Open/Closed Principle (OCP)',
    'Single Responsibility Principle (SRP)',
    'Dependency Inversion Principle (DIP)',
    'Interface Segregation Principle (ISP)');

-- Q3 (SQL/DBA): JOIN
INSERT INTO TB_ONEM_QUESTAO (id_questao, nv_dificuldade, enunciado_questao, alternativa_1, alternativa_2, alternativa_3, alternativa_4, alternativa_5)
VALUES (3, 2, 'Qual tipo de JOIN retorna todas as linhas da tabela da esquerda e as correspondentes da direita?',
    'LEFT JOIN',
    'RIGHT JOIN',
    'INNER JOIN',
    'FULL JOIN',
    'CROSS JOIN');

-- Q4 (Front-end): Promises
INSERT INTO TB_ONEM_QUESTAO (id_questao, nv_dificuldade, enunciado_questao, alternativa_1, alternativa_2, alternativa_3, alternativa_4, alternativa_5)
VALUES (4, 3, 'Qual estado representa a conclusão bem-sucedida de uma Promise em JavaScript?',
    'Fulfilled',
    'Pending',
    'Rejected',
    'Settled',
    'Resolved');

-- Q5 (DevOps): IaC
INSERT INTO TB_ONEM_QUESTAO (id_questao, nv_dificuldade, enunciado_questao, alternativa_1, alternativa_2, alternativa_3, alternativa_4, alternativa_5)
VALUES (5, 4, 'Qual ferramenta é um exemplo clássico de Infrastructure as Code (IaC)?',
    'Terraform',
    'Kubernetes (K8s)',
    'Docker',
    'Prometheus',
    'Grafana');


-- ========================================================
-- TESTES E VÍNCULOS
-- ========================================================

-- TESTE 1: Associado à Vaga 1 (Desenvolvedor Backend Sênior)
INSERT INTO TB_ONEM_TESTE (id_teste, dt_inicio, dt_fim, pontuacao_teste, id_vaga) VALUES (1, SYSDATE, SYSDATE + 7, 0.0, 1);
-- 5 Questões para o Teste 1
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (1, 1); -- Threads
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (1, 2); -- SOLID
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (1, 3); -- SQL JOIN
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (1, 4); -- Promises JS
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (1, 5); -- IaC


-- TESTE 2: Associado à Vaga 2 (Engenheiro Front-end Pleno)
INSERT INTO TB_ONEM_TESTE (id_teste, dt_inicio, dt_fim, pontuacao_teste, id_vaga) VALUES (2, SYSDATE, SYSDATE + 7, 0.0, 2);
-- 3 Questões para o Teste 2 (Apenas para garantir que a tabela tem mais dados)
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (2, 4);
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (2, 3);
INSERT INTO TB_ONEM_TESTE_QUESTAO (id_teste, id_questao) VALUES (2, 5);


-- VAGA_SKILL
INSERT INTO TB_ONEM_VAGA_SKILL (id_vaga, id_skill) VALUES (1, 1); -- Vaga 1 tem Java
INSERT INTO TB_ONEM_VAGA_SKILL (id_vaga, id_skill) VALUES (1, 3); -- Vaga 1 tem Spring
INSERT INTO TB_ONEM_VAGA_SKILL (id_vaga, id_skill) VALUES (2, 4); -- Vaga 2 tem JavaScript


-- FORMAÇÕES (Para o Candidato Real ID 3)
INSERT INTO TB_ONEM_FORMACAO (id_formacao, dt_criacao, nm_formacao, ds_formacao, nm_instituicao, dt_inicio, dt_fim, id_candidato)
VALUES (1, SYSDATE, 'Bacharelado em T.I.', 'Curso de 4 anos', 'Universidade FIAP', TO_DATE('2015-01-01', 'YYYY-MM-DD'), TO_DATE('2018-12-31', 'YYYY-MM-DD'), 3);


-- CANDIDATO_SKILL (Para o Candidato Real ID 3)
INSERT INTO TB_ONEM_CANDIDATO_SKILL (id_candidato, id_skill) VALUES (3, 1); -- Candidato 3 tem Java
INSERT INTO TB_ONEM_CANDIDATO_SKILL (id_candidato, id_skill) VALUES (3, 3); -- Candidato 3 tem Spring


-- ========================================================
-- FLUXO PRINCIPAL: CANDIDATURA ATIVA (Candidato 3 -> Vaga 1)
-- ========================================================

-- CANDIDATURA 1: Candidato Real aplica para Vaga 1
INSERT INTO TB_ONEM_CANDIDATURA (id_candidatura, dt_criacao, deleted, id_vaga, id_candidato)
VALUES (1, SYSDATE, 0, 1, 3);

-- STATUS 1: Status inicial para a Candidatura 1 (Pronta para o Teste)
INSERT INTO TB_ONEM_STATUS_CANDIDATURA (id_status_candidatura, dt_atualizacao, ds_status, id_candidatura)
VALUES (1, SYSDATE, 'AGUARDANDO_TESTE', 1);


-- COMMIT FINAL
COMMIT;