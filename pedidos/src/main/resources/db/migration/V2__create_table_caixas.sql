CREATE TABLE Caixa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedidos_id BIGINT,
    FOREIGN KEY (pedidos_id) REFERENCES Pedidos(id)
);