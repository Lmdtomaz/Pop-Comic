CREATE TABLE Produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    avaliacao DOUBLE,
    descricaoDetalhada TEXT,
    precoProduto DECIMAL(10, 2) NOT NULL,
    qtdEstoque INT NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    imagemPadrao VARCHAR(255),
    imagens TEXT
);