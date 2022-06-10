# Liquibase Multiple DBs

Java Liquibase Application with multiple databases.

### Add DB

To add a new database just include it in the DatabaseConfig.java class and configure application.yaml

# [PT-BR] Migrations || Padrão de projeto e Documentação

### Adicionando novo arquivo migration

Para adicionar um novo arquivo de migração basta criar um novo arquivo SQL de acordo com a estrutura de pastas abaixo e seguindo os padrões 
do framework conforme documentação e adicioná-lo ao db_changelog.xml responsável (Há 01 por schema/DB)

#### Estrutura de pastas
Para adicionar um novo migration basta criar um novo arquivo .sql seguindo os padrões de pasta do projeto conforme abaixo:

    -- src/main/resources/migration
        -- <Banco de Dados>
            -- </Schema or DBName>
                -- <YYYYMM>
                    -- <Sequencial>



Caso seja necessário criar novos schemas/dbs ou pastas para divisão Ano/Mes basta seguir o padrão já existente.

##### Arquivo migration

O liquibase permite trabalharmos com vários formatos de migration mas por padrão adotaremos as migrações utilizando o formato .sql de maneira a simplificar nossas rotinas de alteração
do banco de dados. O arquivo de migração contém a seguinte estrutura:

    --liquibase formatted sql 
    --changeset <Usuário>:<Número Sequencial INTERNO do migration> <liquibaseAttributes:value>[...]
    --preconditions <preconditionAttributes:values>[...]
    --precondition-sql-check expectedResult:<Resultado esperado> <Comando SQL a ser executado para pré-condição>

    <COMANDO SQL A SER EXECUTADO>
    
    --rollback <Ação de Rollback>;


##### PRÉ CONDIÇÕES

Um dos diferenciais do liquibase é a definição de pré condições para executar o changeset onde definimos algumas pré condições e ações como por exemplo onFail ou onError
abaixo temos um exemplo Real:

Como exemplo real vamos efetuar um update na coluna de status da tabela TABLE para FINALIZADO mas apenas quando não houver registros em aberto na tabela TABLE_2


    --liquibase formatted sql
    --changeset matheus.dias:1 
    --preconditions onFail:MARK_RAN
    --precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM TABLE_2 WHERE STATUS = 'EM ABERTO'
    
    SELECT 1 FROM DB001.dbo.tableTest;
    
    --rollback rollback;

Por boas práticas deve se adotar as pré condições para todos os comandos no banco, não só DML (SELECT, UPDATE, INSERT, DELETE, etc.) 
mas também para comandos DDL (CREATE, ALTER, DROP, etc.)

* [Documentação das Pré Condições](https://docs.liquibase.com/concepts/changelogs/preconditions.html)


##### ROLLBACK

O liquibase ainda permite criar uma instrução de reversão personalizada que pode ser executada através do comando mvn liquibase:rollback

* [Documentação Rollback](https://docs.liquibase.com/workflows/liquibase-community/using-rollback.html)
* [Manual Baeldung Rollback](https://www.baeldung.com/liquibase-rollback)


### DOCUMENTAÇÃO

A documentação oficial do liquibase pode ser acessada através do link abaixo:

* [Documentação oficial](https://docs.liquibase.com/home.html)
