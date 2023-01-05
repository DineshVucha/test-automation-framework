package com.automation.platform.encryptdecrypt;

import org.junit.Test;
import org.testng.Assert;

import static com.automation.platform.dbtesting.constants.ComparatorConstants.COMPARE_SECRET_KEY;

public class AESEncryptDecryptUtilTest {

    @Test
    public void testEncryptAndDecrypt() {
        String originalString = "Secret Site: //Test";
        String encString = AESEncryptDecryptUtil.encrypt(originalString, COMPARE_SECRET_KEY);
        String decString = AESEncryptDecryptUtil.decrypt(encString, COMPARE_SECRET_KEY);
        Assert.assertEquals(decString, originalString);
    }

}