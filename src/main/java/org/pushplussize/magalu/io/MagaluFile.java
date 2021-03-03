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
import org.pushplussize.magalu.io.generate.image.ProductImagesFromTo;
import org.pushplussize.magalu.io.generate.type.ImporterType;
import org.pushplussize.magalu.io.generate.type.product.MagaluProductsImporter;
import org.pushplussize.shopify.io.ShopifyFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.pushplussize.magalu.io.ImporterFieldType.*;
import static org.pushplussize.shopify.io.ShopifyProductTitle.*;

public class MagaluFile {

    private final static Logger LOGGER = Logger.getLogger(MagaluFile.class.getName());
    private static final int MAXIMUM_NUMBER_OF_TITLES = 17;
    private static final int TITLE_ROW_NUM = 2;
    private static final String PRODUCT_TITLE_SHEET_NAME = "Produtos";

    private ProductImagesFromTo productImagesFromTo;
    private MagaluProductsImporter magaluProductsImporter;
    private XSSFWorkbook workbook;

    public MagaluFile(File File) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(File);
        this.magaluProductsImporter = new MagaluProductsImporter();
        this.productImagesFromTo = new ProductImagesFromTo();
    }

    public boolean isValid() {
        XSSFSheet productsSheet = workbook.getSheet(PRODUCT_TITLE_SHEET_NAME);
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
        return importProductFromShopifyFile(shopifyFile, magaluProductsImporter, TITLE_ROW_NUM, MAXIMUM_NUMBER_OF_TITLES);
    }

    public XSSFWorkbook importToImageProductFile(ShopifyFile shopifyFile) throws IOException {
        return importImageProductFromShopifyFile(shopifyFile, 3);
    }

    private XSSFWorkbook importProductFromShopifyFile(ShopifyFile shopifyFile, ImporterType importerType, int titleRowNum, int maximumNumberOfTitles) throws IOException {

        XSSFSheet magaluProductSheet = workbook.getSheetAt(0);

        CSVParser csvParser = shopifyFile.getCsvParser();

        Predicate<CSVRecord> csvRecordPredicate = record -> !record.get(VARIANT_SKU.index).isBlank();

        List<CSVRecord> shopifyLineRecords = csvParser
                .getRecords()
                .stream().filter(csvRecordPredicate)
                .collect(Collectors.toList());

        XSSFRow lastInsertedMagaluRow = null;

        for (int indexRow = 1; indexRow <= shopifyLineRecords.size() - 1; indexRow++) {

            magaluProductSheet.createRow(indexRow + titleRowNum);
            XSSFRow magaluRow = magaluProductSheet.getRow(indexRow + titleRowNum);

            CSVRecord shopifyRow = shopifyLineRecords.get(indexRow);

            importRow(magaluRow, shopifyRow, lastInsertedMagaluRow, importerType, maximumNumberOfTitles);

            lastInsertedMagaluRow = magaluRow;

        }
        return workbook;
    }

    private XSSFWorkbook importImageProductFromShopifyFile(ShopifyFile shopifyFile, int titleRowNum) throws IOException {

        XSSFSheet magaluProductSheet = workbook.getSheetAt(0);

        List<CSVRecord> records = shopifyFile.getCsvParser().getRecords();
        Map<String, List<String>> csvRecordListHashMap = getHandleImageListMap(records);

        Map<String, String> handleSkuMap = getHandleSkuMap(records);

        int indexRow = 0;
        for (String handle: csvRecordListHashMap.keySet()) {

            magaluProductSheet.createRow(indexRow + titleRowNum);
            XSSFRow magaluRow = magaluProductSheet.getRow(indexRow + titleRowNum);

            AtomicInteger columnIndex = new AtomicInteger();
            magaluRow.createCell(columnIndex.get());
            magaluRow.getCell(columnIndex.getAndIncrement()).setCellValue(handleSkuMap.get(handle));

            csvRecordListHashMap
                    .get(handle).forEach(cell -> {
                magaluRow.createCell(columnIndex.get());
                magaluRow.getCell(columnIndex.getAndIncrement()).setCellValue(cell);
            });
            indexRow++;
        }
        return workbook;
    }

    private Map<String, List<String>> getHandleImageListMap(List<CSVRecord> shopifyLineRecords) {

        Map<String, List<String>> csvRecordListHashMap = new HashMap<>();

        for (int indexRow = 1; indexRow <= shopifyLineRecords.size() - 1; indexRow++) {

            CSVRecord shopifyRow = shopifyLineRecords.get(indexRow);

            String handle = shopifyRow.get(HANDLE.index);

            List<String> urlImageList = csvRecordListHashMap.get(handle);
            if(urlImageList == null) {
                urlImageList = new ArrayList();
                csvRecordListHashMap.put(handle, urlImageList);
            }

            if(urlImageList.size() < 5){
                urlImageList.add(shopifyRow.get(IMAGE_SRC.index));
            }
        }
        return csvRecordListHashMap;
    }

    private Map<String, String> getHandleSkuMap(List<CSVRecord> shopifyLineRecords) {

        Map<String, String> handleSkuMap = new HashMap<>();

        for (int indexRow = 1; indexRow <= shopifyLineRecords.size() - 1; indexRow++) {

            CSVRecord shopifyRow = shopifyLineRecords.get(indexRow);

            String handle = shopifyRow.get(HANDLE.index);
            String sku = shopifyRow.get(VARIANT_SKU.index);

            if(sku != null
                    && !sku.isBlank()
                        && !sku.isEmpty()) {
                handleSkuMap.put(handle, sku);
            }
        }
        return handleSkuMap;
    }

    private void importRow(XSSFRow magaluRow,
                           CSVRecord shopifyRow,
                           XSSFRow lastInsertedMagaluRow,
                           ImporterType importerType,
                           int maximumNumberOfTitles) {

        for (int columnIndex = 0; columnIndex <= maximumNumberOfTitles; columnIndex++) {

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