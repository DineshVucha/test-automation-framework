package com.automation.platform.csvcomparator;

import org.junit.Test;

public class CsvDataCompareTest {

    @Test
    public void test_compareCSVFiles() {
        String file1 = "src/test/resources/csvfile/csv1.csv";
        String file2 = "src/test/resources/csvfile/csv2.csv";
        String resultPath = "src/test/resources/csvfile/Result.csv";

        CsvDataCompare.compareCSVFiles(file1, file2, resultPath);
    }
}