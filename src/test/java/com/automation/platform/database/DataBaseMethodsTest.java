package com.automation.platform.database;

import com.automation.platform.config.Configvariable;
import com.automation.platform.config.TapBeansLoad;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.sql.Connection;

@ComponentScan(basePackages = {"com.automation.platform"})
@Configuration
public class DataBaseMethodsTest {
    private DatabaseMethods databaseMet = new DatabaseMethods();
    private Configvariable configvariable;

    @BeforeSuite
    public void setup() {
        TapBeansLoad.setConfigClass(DataBaseMethodsTest.class);
        TapBeansLoad.init();
        configvariable = (Configvariable) TapBeansLoad.getBean(Configvariable.class);
        databaseMet = (DatabaseMethods) TapBeansLoad.getBean(DatabaseMethods.class);
    }

    @Test
    public void testConnectToSqlite() {
        String filePath = "target/test-classes/testFile/test";
        Connection con = databaseMet.connectToSqlite(filePath);
        Assert.assertNotNull(con);
    }

//    @Test
//    public void testSelectFromSqliteDB() {
//        String filePath = "target/test-classes/testFile/test";
//        Map<String, String> dataMap = databaseMethods.selectFromSqliteDB(filePath, "SELECT value as FORGOT_PASSWORD FROM catalystLocalStorage WHERE key in (\"persist:forgotPassword\")");
//        Assert.assertNotNull(dataMap);
//    }


}
