package org.pushplussize.magalu.io;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pushplussize.magalu.io.generate.type.ImporterType;
import org.pushplussize.magalu.io.generate.type.product.MagaluProductsImporter;
import org.pushplussize.shopify.io.ShopifyFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.pushplussize.magalu.io.ImporterFieldType.*;

public class MagaluFile {

    private final static Logger LOGGER = Logger.getLogger(MagaluFile.class.getName());
    private static final int MAXIMUM_NUMBER_OF_TITLES = 17;
    private static final int TITLE_ROW_NUM = 2;
    private static final String TITLE_SHEET_NAME = "Produtos";

    private MagaluProductsImporter magaluProductsImporter;
    private XSSFWorkbook workbook;

    public MagaluFile(File File) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(File);
        this.magaluProductsImporter = new MagaluProductsImporter();
    }

    public boolean isValid() {
        XSSFSheet productsSheet = workbook.getSheet(TITLE_SHEET_NAME);
        Row titlesRow = productsSheet.getRow(TITLE_ROW_NUM);
        for(int index = 0; index < MAXIMUM_NUMBER_OF_TITLES; index++) {
            Cell cell = titlesRow.getCell(index);
            String titleValue = cell.getStringCellValue();

            //TODO Refatorar
            String magaluDescriptionTitle = magaluProductsImporter.getMagaluTitleDescription(index);
            if (!titleValue.equals(magaluDescriptionTitle)) {
                LOGGER.log(Level.SEVERE, "Title ["+magaluDescriptionTitle+"] was not found at the index ["+index+"]");
                return false;
            }
        }
        return true;
    }

    public XSSFWorkbook importToProductFile(ShopifyFile shopifyFile) throws IOException {
        return importFromShopifyFile(shopifyFile, magaluProductsImporter);
    }

    private XSSFWorkbook importFromShopifyFile(ShopifyFile shopifyFile, ImporterType importerType) throws IOException {

        XSSFSheet magaluProductSheet = workbook.getSheet(TITLE_SHEET_NAME);

        CSVParser csvParser = shopifyFile.getCsvParser();

        List<CSVRecord> shopifyLineRecords = csvParser.getRecords();

        XSSFRow lastInsertedMagaluRow = null;

        for (int indexRow = 1; indexRow <= shopifyLineRecords.size() - 1; indexRow++) {

            magaluProductSheet.createRow(indexRow + TITLE_ROW_NUM);
            XSSFRow magaluRow = magaluProductSheet.getRow(indexRow + TITLE_ROW_NUM);

            CSVRecord shopifyRow = shopifyLineRecords.get(indexRow);

            importRow(magaluRow, shopifyRow, lastInsertedMagaluRow, importerType);

            lastInsertedMagaluRow = magaluRow;

        }
        return workbook;
    }

    private void importRow(XSSFRow magaluRow, CSVRecord shopifyRow, XSSFRow lastInsertedMagaluRow, ImporterType importerType) {

        for (int columnIndex = 0; columnIndex <= MAXIMUM_NUMBER_OF_TITLES; columnIndex++) {

            magaluRow.createCell(columnIndex);

            XSSFCell cell = magaluRow.getCell(columnIndex);

            FromTo fromTo = importerType.getConfigByFrom(importerType.getMagaluTitle(columnIndex));

            if(fromTo == null) continue;

            String shopifyValue = getValueToBeInserted(shopifyRow, fromTo);

            if (fromTo == null) continue;

            shopifyValue = fromTo.getValue(shopifyValue);

            if(shopifyValue != null
                    && !shopifyValue.trim().equalsIgnoreCase("")) {
                cell.setCellValue(shopifyValue);
            }else{
                cell.setCellValue(lastInsertedMagaluRow.getCell(columnIndex).getStringCellValue());
            }
        }
    }

    private String getValueToBeInserted(CSVRecord shopifyRow, FromTo fromTo) {
        ImporterFieldType fieldType = fromTo.getFieldType();
        if(fieldType != null && fieldType.equals(HARDCODED)) return fromTo.getValue();
        return shopifyRow.get(fromTo.getShopifyProductTitle().index);
    }

}