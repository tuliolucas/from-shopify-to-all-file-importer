package org.pushplussize.magalu.io.generate.type.product;

import static java.util.Arrays.stream;

public enum MagaluProductTitle {

    CODIGO_PRINCIPAL("Código Principal", 0),
    CODIGO_VARIACAO("Código Variação", 1),
    NOME_PRODUTO("Nome do Produto", 2),
    NOME_VARIACAO("Nome da Variação", 3),
    FICHA_TECNICA("Ficha Técnica/Descrição", 4),
    MARCA("Marca", 5),
    ORIGEM("Origem (Nacional ou Importado)", 6),
    NCM("NCM  (não obrigatório)", 7),
    GARANTIA("Garantia (meses)", 8),
    ID_CATEGORIA("ID da Categoria no Integra", 9),
    EAN("EAN (não obrigatório)", 10),
    ALTURA("Altura (cm)", 11),
    LARGURA("Largura (cm)", 12),
    COMPRIMENTO("Comprimento (cm)", 13),
    PESO("Peso (kg)", 14),
    PRECO("Preço", 15),
    PRECO_PROMOCIONAL("Preço Promocional", 16),
    CODIGO_ISBN("Código", 17);

    public String description;
    public Integer index;

    MagaluProductTitle(String valor, Integer index) {
        this.description = valor;
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public static String getMagaluTitleDescription(Integer indexInRow) {
        return getMagaluTitle(indexInRow).getDescription();
    }

    public static MagaluProductTitle getMagaluTitle(Integer indexInRow) {
        return stream(MagaluProductTitle.values())
                .filter(title -> title.index.equals(indexInRow))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Magalu title was not found at index:" + indexInRow));
    }
}
