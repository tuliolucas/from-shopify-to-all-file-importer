package org.pushplussize.shopify.io;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

public class ShopifyFile {

    private static final String SAMPLE_CSV_FILE_PATH = "./users.csv";

    private final static Logger LOGGER = Logger.getLogger(ShopifyFile.class.getName());

    private final CSVParser csvParser;

    public ShopifyFile(File file) throws IOException {
        Reader reader = newBufferedReader(get(file.getAbsolutePath()));
        this.csvParser = new CSVParser(reader, DEFAULT);
    }

    public CSVParser getCsvParser() {
        return csvParser;
    }

    public boolean isValid() throws IOException {

        CSVRecord headerRow = csvParser.getRecords().stream().findFirst().get();

        for(int index = 0; index < 48; index++) {
            String titleValue = headerRow.get(index);
            String magaluDescriptionTitle = ShopifyProductTitle.getShopifyDescriptionTitle(index);
            if (!titleValue.equals(magaluDescriptionTitle)) {
                LOGGER.log(Level.SEVERE, "Title [" + magaluDescriptionTitle + "] was not found at the index [" + index + "]");
                return false;
            }
        }
        return true;
    }
}

