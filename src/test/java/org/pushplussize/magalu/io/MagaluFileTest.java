package org.pushplussize.magalu.io;

import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.pushplussize.magalu.io.generate.type.product.MagaluProductTitle.*;
import static org.pushplussize.shopify.io.ShopifyFileTest.SHOPIFY_FILE_NAME;
import static org.pushplussize.shopify.io.ShopifyFileTest.getShopifyFile;

@RunWith(JUnit4.class)
public class MagaluFileTest extends TestCase {

    public static final String MAGALU_FILE_NAME = "magalu-template-upload-products-skus.xlsx";

    public static File getFile(String filename) {
        return new File(MagaluFileTest.class.getClassLoader().getResource(filename).getFile());
    }

    @Test
    public void shouldHaveDefaultMagaluProductsHeaders() throws IOException, InvalidFormatException {
        assertTrue(new MagaluFile(getFile(MAGALU_FILE_NAME)).isValid());
    }

    @Test
    public void shouldImportFromShopifyFileSuccessfully() throws IOException, InvalidFormatException {
        MagaluFile magaluFile = new MagaluFile(getFile(MAGALU_FILE_NAME));
        XSSFWorkbook xssfWorkbook = magaluFile.importToProductFile(getShopifyFile(SHOPIFY_FILE_NAME));
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        assertThat(xssfWorkbook.getNumberOfSheets(), is(1));
        XSSFRow row = sheet.getRow(3);
        assertThat(row.getCell(CODIGO_PRINCIPAL.index).getStringCellValue(), is("blusa-jeans-mangas-bufantes-barra-lastex"));
        assertThat(row.getCell(CODIGO_VARIACAO.index).getStringCellValue(), is("100000429"));
        assertThat(row.getCell(NOME_PRODUTO.index).getStringCellValue(), is("Blusa Jeans Mangas bufantes Barra Lastex"));
        assertThat(row.getCell(NOME_VARIACAO.index).getStringCellValue(), is("Blusa Jeans Mangas bufantes Barra Lastex"));
        assertThat(row.getCell(FICHA_TECNICA.index).getStringCellValue(), containsString("Blusa jeans confeccionada em tecido"));
        assertThat(row.getCell(MARCA.index).getStringCellValue(), is("Push Plus Size"));
        assertThat(row.getCell(ID_CATEGORIA.index).getStringCellValue(), is("2"));
        assertThat(row.getCell(ORIGEM.index).getStringCellValue(), is("0"));
        assertThat(row.getCell(GARANTIA.index).getStringCellValue(), is("1"));
        assertThat(row.getCell(ALTURA.index).getStringCellValue(), is("10"));
        assertThat(row.getCell(LARGURA.index).getStringCellValue(), is("22"));
        assertThat(row.getCell(COMPRIMENTO.index).getStringCellValue(), is("30"));
        assertThat(row.getCell(PESO.index).getStringCellValue(), is("1"));
        assertThat(row.getCell(PRECO.index).getStringCellValue(), is("149,90"));
    }

    @Test
    public void shouldCreateFileSuccessfully() throws IOException, InvalidFormatException {
        MagaluFile magaluFile = new MagaluFile(getFile(MAGALU_FILE_NAME));
        XSSFWorkbook xssfWorkbook = magaluFile.importToProductFile(getShopifyFile(SHOPIFY_FILE_NAME));

        String fileName = getFileName();

        File file = new File(fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        xssfWorkbook.write(outputStream);
    }

    private String getFileName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "/Users/thoughtworks/Documents/Pessoal/personal-projects/from-shopify-importer/src/test/resources/" +
                "magaluProducts_"
                +dateFormat.format(new Date())
                +".xlsx";
        return fileName;
    }
}