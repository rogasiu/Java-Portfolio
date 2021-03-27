create database stm_db;
create user 'stm_user'@'localhost' identified by 'qwe123';
GRANT CREATE, DROP, ALTER, REFERENCES,INDEX,SELECT,INSERT,UPDATE,DELETE ON
stm_db.* TO 'stm_user'@'localhost';