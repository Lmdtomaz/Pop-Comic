CREATE TABLE Produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    avaliacao DOUBLE,
    descricaoDetalhada TEXT,
    precoProduto DOUBLE NOT NULL,
    qtdEstoque INT NOT NULL,
    imagemPadrao VARCHAR(255),
    imagens TEXT
);