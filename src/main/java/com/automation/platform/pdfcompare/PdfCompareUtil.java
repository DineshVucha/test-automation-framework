package com.automation.platform.pdfcompare;

import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class PdfCompareUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfCompareUtil.class);

    private static final String COMPARE_PATH_DIRECTORY = "\\src\\test\\resources\\pdfValidation\\Compare\\";

    public static boolean pdfCompare(String actualFilePath, String referenceFilePath, String actualFile, String referenceFile){
        PDFUtil pdfUtil = new PDFUtil();
        LOGGER.info("Comparing 2 pdf method with actual path {}, reference-path {}", actualFilePath, referenceFile);

        String file1 = createFileName(actualFilePath, actualFile, ".pdf");
        String file2 = createFileName(referenceFilePath, referenceFile, ".pdf");
        pdfUtil.setCompareMode(CompareMode.TEXT_MODE);

        try {
            if (!pdfUtil.compare(file1, file2)) {
                compare(pdfUtil);
            }
            return pdfUtil.compare(file1, file2);
        }catch (IOException ex) {
            LOGGER.error("Error in reading pdf file");
            throw new IllegalArgumentException("Argument provided for comparision is not correct");
        }
   }

    private static void compare(PDFUtil pdfUtil) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
        pdfUtil.compareAllPages(true);
        pdfUtil.highlightPdfDifference(true);
        writeDifferenceToNewPdf(pdfUtil, sdf, timestamp);
    }

    private static void writeDifferenceToNewPdf(PDFUtil pdfUtil, SimpleDateFormat sdf, Timestamp timestamp) throws IOException {
        String imgDiffPath = createFileName(COMPARE_PATH_DIRECTORY, sdf.format(timestamp), "\\");
        Path filePath = Paths.get(imgDiffPath);
        Files.createDirectories(filePath);
        await().atMost(1, MINUTES)
                .ignoreExceptions()
                .until(() -> filePath.toFile().exists());
        pdfUtil.setImageDestinationPath(imgDiffPath);
    }

    @NotNull
    private static String createFileName(String filePath, String fileName, String extn) {
        return System.getProperty("user.dir") + filePath + fileName + extn;
    }
}
