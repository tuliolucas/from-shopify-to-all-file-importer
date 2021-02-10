package org.pushplussize.shopify.io;

import java.util.Arrays;

public enum ShopifyProductTitle {

    HANDLE("Handle", 0),
    TITLE("Title", 1),
    BODY("Body (HTML)", 2),
    VENDOR("Vendor", 3),
    TYPE("Type", 4),
    TAGS("Tags", 5),
    PUBLISHED("Published", 6),
    OPTION1_NAME("Option1 Name", 7),
    OPTION1_VALUE("Option1 Value", 8),
    OPTION2_NAME("Option2 Name", 9),
    OPTION2_VALUE("Option2 Value", 10),
    OPTION3_NAME("Option3 Name", 11),
    OPTION3_VALUE("Option3 Value", 12),
    VARIANT_SKU("Variant SKU", 13),
    VARIANT_GRAMS("Variant Grams", 14),
    VARIANT_INVENTORY_TRACKER("Variant Inventory Tracker", 15),
    VARIANT_INVENTORY_QTY("Variant Inventory Qty", 16),
    VARIANT_INVENTORY_POLICY("Variant Inventory Policy", 17),
    VARIANT_FULFILLMENT_ServicE("Variant Fulfillment Service", 18),
    VARIANT_PRICE("Variant Price", 19),
    VARIANT_COMPARE_AT_PRICE("Variant Compare At Price", 20),
    VARIANT_REQUIRES_SHIPPING("Variant Requires Shipping", 21),
    VARIANT_TAXABLE("Variant Taxable", 22),
    VARIANT_BARCODE("Variant Barcode", 23),
    IMAGE_SRC("Image Src", 24),
    IMAGE_POSITION("Image Position", 25),
    IMAGE_ALT("Image Alt Text", 26),
    GIT_CARD("Gift Card", 27),
    SEO_TITLE("SEO Title", 28),
    SEO_DESCRIPTION("SEO Description", 29),
    GOOGLE_1("Google Shopping / Google Product Category", 30),
    GOOGLE_2("Google Shopping / Gender", 31),
    GOOGLE_3("Google Shopping / Age Group", 32),
    GOOGLE_4("Google Shopping / MPN", 33),
    GOOGLE_5("Google Shopping / AdWords Grouping", 34),
    GOOGLE_6("Google Shopping / AdWords Labels", 35),
    GOOGLE_7("Google Shopping / Condition", 36),
    GOOGLE_8("Google Shopping / Custom Product", 37),
    GOOGLE_9("Google Shopping / Custom Label 0", 38),
    GOOGLE_10("Google Shopping / Custom Label 1", 39),
    GOOGLE_11("Google Shopping / Custom Label 2", 40),
    GOOGLE_12("Google Shopping / Custom Label 3", 41),
    GOOGLE_13("Google Shopping / Custom Label 4", 42),
    GOOGLE_14("Variant Image", 43),
    VARIANT_WEIGH_UNIT("Variant Weight Unit", 44),
    VARIANT_TAX_CODE("Variant Tax Code", 45),
    COST_PER_ITEM("Cost per item", 46),
    STATUS("Status", 47);

    public String description;
    public Integer index;

    ShopifyProductTitle(String valor, Integer index) {
        this.description = valor;
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public static String getShopifyDescriptionTitle(Integer indexInRow) {
        int index = indexInRow + 1;
        return Arrays.stream(ShopifyProductTitle.values())
                .filter(title -> title.index.equals(index))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Shopify title was not found at index:" + index))
                .getDescription();
    }
}
