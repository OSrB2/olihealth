CREATE TABLE tb_health (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  level INTEGER,
  client_id BIGINT,
  FOREIGN KEY (client_id) REFERENCES tb_client(id)
);