package org.pushplussize.magalu.io;

import org.jsoup.Jsoup;
import org.pushplussize.shopify.io.ShopifyProductTitle;

import static org.pushplussize.magalu.io.ImporterFieldType.HARDCODED;
import static org.pushplussize.magalu.io.ImporterFieldType.TEXT;

public class FromTo {

    public static final String MAGALU_CATEGORY_TYPE_ID_CONJUNTO = "4";
    public static final String MAGALU_CATEGORY_TYPE_ID_MACACAO_MACAQUINHO = "5";
    public static final String MAGALU_CATEGORY_TYPE_ID_SHORT_SAIA = "6";
    public static final String MAGALU_CATEGORY_TYPE_ID_COLETE_JAQUETA = "3";
    public static final String MAGALU_CATEGORY_TYPE_ID_COLETE_ACESSORIO = "8";
    public static final String MAGALU_CATEGORY_TYPE_ID_COLETE_CALCA = "7";
    public static final String MAGALU_CATEGORY_TYPE_ID_COLETE_BLUSA = "2";
    public static final String MAGALU_CATEGORY_TYPE_ID_COLETE_VESTIDO = "1";

    private ShopifyProductTitle shopifyProductTitle;
    private String value;
    private ImporterFieldType fieldType;

    public FromTo(ShopifyProductTitle shopifyProductTitle,
                  String value) {
        this.shopifyProductTitle = shopifyProductTitle;
        this.value = value;
        this.fieldType = TEXT;
    }

    public FromTo(String value) {
        this.value = value;
        this.fieldType = HARDCODED;
    }

    public FromTo(ShopifyProductTitle shopifyProductTitle,
                  ImporterFieldType type) {
        this.shopifyProductTitle = shopifyProductTitle;
        this.fieldType = type;
    }

    public FromTo(ShopifyProductTitle shopifyProductTitle) {
        this.shopifyProductTitle = shopifyProductTitle;
        this.fieldType = TEXT;
    }

    public FromTo(ShopifyProductTitle shopifyProductTitle,
                  ImporterFieldType type,
                  String value) {
        this.shopifyProductTitle = shopifyProductTitle;
        this.fieldType = type;
        this.value = value;
    }

    public ShopifyProductTitle getShopifyProductTitle() {
        return shopifyProductTitle;
    }

    public String getValue() {
        return value;
    }

    public ImporterFieldType getFieldType() {
        return fieldType;
    }

    public String getValue(String shopifyValue) {

        ImporterFieldType fieldType = getFieldType();

        switch (fieldType) {

            case MONEY: shopifyValue = shopifyValue.replace(".", ",");
                break;

            case HTML: {
                shopifyValue = Jsoup.parse(shopifyValue).text();
                break;
            }
            case HARDCODED: shopifyValue = getValue();
                break;

            case TYPE_CATEGORY: shopifyValue = getMagaluType(shopifyValue);
                break;

            case TEXT_WITH_REPLACE: shopifyValue = shopifyValue.replace(getValue(), "");;
                break;
        }
        return shopifyValue;
    }

    public String getMagaluType(String shopifyType) {

        String shopifyUpperCase = shopifyType.toUpperCase();

        if (shopifyUpperCase.equalsIgnoreCase("VESTIDO")) {
            return MAGALU_CATEGORY_TYPE_ID_COLETE_VESTIDO;
        } else if (shopifyUpperCase.equalsIgnoreCase("BLUSA")
                || shopifyUpperCase.equalsIgnoreCase("BODY")
                || shopifyUpperCase.equalsIgnoreCase("CAMISA")
                || shopifyUpperCase.equalsIgnoreCase("CROPPED")) {
            return MAGALU_CATEGORY_TYPE_ID_COLETE_BLUSA;
        } else if (shopifyUpperCase.equalsIgnoreCase("CALÇA")
                || shopifyUpperCase.equalsIgnoreCase("CALÇA PANTALONA")) {
            return MAGALU_CATEGORY_TYPE_ID_COLETE_CALCA;
        } else if (shopifyUpperCase.equalsIgnoreCase("CINTO")
                || shopifyUpperCase.equalsIgnoreCase("CARTÃO-PRESENTE")) {
            return MAGALU_CATEGORY_TYPE_ID_COLETE_ACESSORIO;
        } else if (shopifyUpperCase.equalsIgnoreCase("COLETE")
                || shopifyUpperCase.equalsIgnoreCase("JAQUETA")) {
            return MAGALU_CATEGORY_TYPE_ID_COLETE_JAQUETA;
        } else if (shopifyUpperCase.equalsIgnoreCase("CONJUNTO")) {
            return MAGALU_CATEGORY_TYPE_ID_CONJUNTO;
        } else if (shopifyUpperCase.equalsIgnoreCase("JARDINEIRA")
                || shopifyUpperCase.equalsIgnoreCase("KIMONO")
                || shopifyUpperCase.equalsIgnoreCase("MACACÃO")
                || shopifyUpperCase.equalsIgnoreCase("MACAQUINHO")) {
            return MAGALU_CATEGORY_TYPE_ID_MACACAO_MACAQUINHO;
        } else if(shopifyUpperCase.equalsIgnoreCase("SAIA")
                || shopifyUpperCase.equalsIgnoreCase("SHORT")
                || shopifyUpperCase.equalsIgnoreCase("SHORT SAIA")){
            return MAGALU_CATEGORY_TYPE_ID_SHORT_SAIA;
        }
        return "";
    }

}
