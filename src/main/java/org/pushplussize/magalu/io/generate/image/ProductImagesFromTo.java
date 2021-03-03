package org.pushplussize.magalu.io.generate.image;

import org.pushplussize.magalu.io.FromTo;
import org.pushplussize.magalu.io.generate.type.ImporterType;
import org.pushplussize.magalu.io.generate.type.product.MagaluProductTitle;

import java.util.HashMap;
import java.util.Map;

import static org.pushplussize.magalu.io.ImporterFieldType.*;
import static org.pushplussize.magalu.io.generate.image.MagaluImageProductTitle.*;
import static org.pushplussize.shopify.io.ShopifyProductTitle.*;

public class ProductImagesFromTo implements ImporterType {

    private static Map<MagaluImageProductTitle, FromTo> fromToMap = new HashMap<>();

    static {
        fromToMap.put(CODIGO_VARIACAO, new FromTo(VARIANT_SKU, TEXT_WITH_REPLACE, "'"));
        fromToMap.put(URL_IMAGEM_1, new FromTo(IMAGE_SRC));
    }

    @Override
    public FromTo getConfigByFrom(Enum magaluHeaderTitleEnum) {
        return fromToMap.get(magaluHeaderTitleEnum);
    }

    @Override
    public String getMagaluTitleDescription(Integer indexInRow) {
        return MagaluImageProductTitle.getMagaluTitleDescription(indexInRow);
    }

    @Override
    public Enum getMagaluTitle(Integer indexInRow) {
        return MagaluImageProductTitle.getMagaluTitle(indexInRow);
    }
}
