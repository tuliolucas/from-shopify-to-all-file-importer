package org.pushplussize.magalu.io.generate.type.product;

import org.pushplussize.magalu.io.FromTo;
import org.pushplussize.magalu.io.generate.image.MagaluImageProductTitle;
import org.pushplussize.magalu.io.generate.type.ImporterType;

import java.util.HashMap;
import java.util.Map;

import static org.pushplussize.magalu.io.ImporterFieldType.*;
import static org.pushplussize.magalu.io.ImporterFieldType.MONEY;

import static org.pushplussize.magalu.io.generate.type.product.MagaluProductTitle.*;
import static org.pushplussize.shopify.io.ShopifyProductTitle.*;

public class MagaluProductsImporter implements ImporterType {

    private static Map<MagaluProductTitle, FromTo> fromToMap = new HashMap<>();

    static {
        fromToMap.put(CODIGO_PRINCIPAL, new FromTo(HANDLE));
        fromToMap.put(CODIGO_VARIACAO, new FromTo(VARIANT_SKU, TEXT_WITH_REPLACE, "'"));
        fromToMap.put(NOME_PRODUTO, new FromTo(TITLE));
        fromToMap.put(NOME_VARIACAO, new FromTo(TITLE));
        fromToMap.put(FICHA_TECNICA, new FromTo(BODY, HTML));
        fromToMap.put(MARCA, new FromTo("Push Plus Size"));
        fromToMap.put(ORIGEM, new FromTo("0"));
        fromToMap.put(GARANTIA, new FromTo("1"));
        fromToMap.put(ID_CATEGORIA, new FromTo(TYPE, TYPE_CATEGORY));
        fromToMap.put(ALTURA, new FromTo("10,0"));
        fromToMap.put(LARGURA, new FromTo("22,0"));
        fromToMap.put(COMPRIMENTO, new FromTo("30,0"));
        fromToMap.put(PESO, new FromTo("1,0"));
        fromToMap.put(PRECO, new FromTo(VARIANT_PRICE, MONEY));
        fromToMap.put(PRECO_PROMOCIONAL, new FromTo(VARIANT_PRICE, MONEY));
    }

    @Override
    public FromTo getConfigByFrom(Enum magaluHeaderTitleEnum) {
        return fromToMap.get(magaluHeaderTitleEnum);
    }

    @Override
    public String getMagaluTitleDescription(Integer indexInRow) {
        return MagaluProductTitle.getMagaluTitleDescription(indexInRow);
    }

    @Override
    public Enum getMagaluTitle(Integer indexInRow) {
        return MagaluProductTitle.getMagaluTitle(indexInRow);
    }
}
