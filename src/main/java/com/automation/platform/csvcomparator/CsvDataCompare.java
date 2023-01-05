package com.automation.platform.csvcomparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * This is CSV comparator.. ensure that column 1 is keycolum
 */
public class CsvDataCompare {

    public static final String CSV_SPLITTER = ",";

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataCompare.class);

    public static void compareCSVFiles(String path1, String path2, String resultPath) {
        try {
            HashMap<String, String> mapFile1 = readFileAndConvertMapper(path1);
            HashMap<String, String> mapFile2 = readFileAndConvertMapper(path2);

            writeToResultFile(resultPath, mapFile1, mapFile2);
        } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            LOGGER.error("Error encountered in running csv comparision");
        }
    }

    private static void writeToResultFile(String resultPath, HashMap<String, String> mapper1,
                                          HashMap<String, String> mapper2) throws IOException {
        FileWriter resultFile = new FileWriter(resultPath);
        for (String y : mapper1.keySet()) {
            if (!mapper2.containsKey(y)) {
                String val = mapper1.get(y);
                LOGGER.info("The Value mapped to Key is:" + y + " " + val);
                resultFile.append(y).append(",");
                resultFile.append(val);
                resultFile.append("\n");
                resultFile.flush();
            }
        }
        resultFile.close();
    }

    private static HashMap<String, String> readFileAndConvertMapper(String filePath) throws IOException {
        HashMap<String, String> mapper = new HashMap<>();
        String[] columns;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            columns = line.split(CSV_SPLITTER);
            extractMapper(columns, mapper, columns[0]);
        }
        return mapper;
    }

    private static void extractMapper(String[] columnsFile1, HashMap<String, String> fileMapper, String keyColumn) {
        for (int i = 1; i < columnsFile1.length; i++) {
            if (fileMapper.containsKey(keyColumn)) {
                String appendValue = fileMapper.get(keyColumn) + CSV_SPLITTER + columnsFile1[i];
                fileMapper.put(keyColumn, appendValue);
            }  else {
                fileMapper.put(keyColumn, columnsFile1[i]);
            }
        }
    }
}