package org.pushplussize.magalu.io.generate.type.product;

import org.pushplussize.magalu.io.FromTo;
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
        fromToMap.put(ALTURA, new FromTo("10"));
        fromToMap.put(LARGURA, new FromTo("22"));
        fromToMap.put(COMPRIMENTO, new FromTo("30"));
        fromToMap.put(PESO, new FromTo("1"));
        fromToMap.put(PRECO, new FromTo(VARIANT_PRICE, MONEY));
    }

    @Override
    public FromTo getConfigByFrom(MagaluProductTitle magaluProductTitle) {
        return fromToMap.get(magaluProductTitle);
    }

    @Override
    public String getMagaluTitleDescription(Integer indexInRow) {
        return null;
    }

    @Override
    public MagaluProductTitle getMagaluTitle(Integer indexInRow) {
        return null;
    }
}
