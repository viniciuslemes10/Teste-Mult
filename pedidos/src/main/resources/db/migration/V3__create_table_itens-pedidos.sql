CREATE TABLE ItemPedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    caixas_id BIGINT,
    sku VARCHAR(255),
    quantidade INT,
    FOREIGN KEY (caixas_id) REFERENCES Caixa(id)
);