package org.pushplussize.shopify.io;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

@RunWith(JUnit4.class)
public class ShopifyFileTest extends TestCase {

    public static final String SHOPIFY_FILE_NAME = "shopify_products_export.csv";
    public static final String WRONG_SHOPIFY_FILE_NAME = "wrong_shopify_products_export.csv";

    public static File getFile(String filename) {
        return new File(ShopifyFileTest.class.getClassLoader().getResource(filename).getFile());
    }

    public static ShopifyFile getShopifyFile(String filename) throws IOException {
        return new ShopifyFile(getFile(filename));
    }

    @Test
    public void shouldNotHaveDefaultShopifyHeaders() throws IOException {
        assertFalse(getShopifyFile(WRONG_SHOPIFY_FILE_NAME).isValid());
    }


    @Test
    public void shouldHaveAllDefaultShopifyHeaders() throws IOException {
        assertTrue(getShopifyFile(SHOPIFY_FILE_NAME).isValid());
    }

}