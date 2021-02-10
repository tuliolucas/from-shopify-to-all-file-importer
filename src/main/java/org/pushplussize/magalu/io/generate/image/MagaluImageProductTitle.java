package org.pushplussize.magalu.io.generate.image;

import static java.util.Arrays.stream;

public enum MagaluImageProductTitle {

    CODIGO_VARIACAO("Cód do Sku", 0),
    URL_IMAGEM_1("URL Imagem 1", 1),
    URL_IMAGEM_2("URL Imagem 2", 2),
    URL_IMAGEM_3("URL Imagem 3", 3),
    URL_IMAGEM_4("URL Imagem 4", 4),
    URL_IMAGEM_5("URL Imagem 5", 5);

    public String description;
    public Integer index;

    MagaluImageProductTitle(String valor, Integer index) {
        this.description = valor;
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public static String getMagaluTitleDescription(Integer indexInRow) {
        return getMagaluTitle(indexInRow).getDescription();
    }

    public static MagaluImageProductTitle getMagaluTitle(Integer indexInRow) {
        return stream(MagaluImageProductTitle.values())
                .filter(title -> title.index.equals(indexInRow))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Magalu title was not found at index:" + indexInRow));
    }
}
