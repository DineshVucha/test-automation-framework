package com.automation.platform.pdfcompare;

import org.junit.Test;
import org.testng.Assert;

public class PdfCompareUtilTest {

    @Test
    public void testpdfCompare() {
        String afp = "\\src\\test\\resources\\pdfValidation\\" ;
        String rfp = "\\src\\test\\resources\\pdfValidation\\" ;
        Assert.assertTrue(PdfCompareUtil.pdfCompare(afp, rfp, "popm_practiceTest-text", "popm_practiceTest-textEdit" ));
    }
}