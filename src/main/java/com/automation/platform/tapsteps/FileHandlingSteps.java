package com.automation.platform.tapsteps;

import com.automation.platform.cucumberUtils.ScenarioUtils;
import com.automation.platform.filehandling.CsvUtils;
import com.automation.platform.config.Configvariable;
import com.automation.platform.config.TapBeansLoad;
import com.automation.platform.exception.TapException;
import com.automation.platform.exception.TapExceptionType;
import io.cucumber.java.en.*;

import java.io.InputStream;
import java.util.List;

public class FileHandlingSteps {
    private Configvariable configvariable = (Configvariable) TapBeansLoad.getBean(Configvariable.class);
    private CsvUtils csvUtils = (CsvUtils) TapBeansLoad.getBean(CsvUtils.class);
    private ScenarioUtils scenarioUtils = (ScenarioUtils) TapBeansLoad.getBean(ScenarioUtils.class);

    @Given("^I load property file \"([^\"]*)\" into global property map$")
    public void loadPropertyFile(String filePath) {
        scenarioUtils.write("Loading property file :" + filePath);
        InputStream initialStream = getClass().getResourceAsStream(filePath);
        configvariable.propertiesFileLoad(initialStream);
    }

    @Given("^I load environment property file \"([^\"]*)\" into global property map for lbu \"([^\"]*)\"$")
    public void loadEnvironmentPropertyFile(String env, String lbu) {
        scenarioUtils.write("Loading property file for environment :" + env);
        configvariable.setupEnvironmentProperties(env, lbu);
    }

    @Given("^I load csv file \"([^\"]*)\" with separator \"([^\"]*)\" into global property map$")
    public void loadCsvFile(String filePath, String separator) {
        scenarioUtils.write("Loading csv file :" + filePath);
        InputStream initialStream = getClass().getResourceAsStream(filePath);
        List<String[]> allCsvData = csvUtils.csvInputStreamReader(initialStream, separator.charAt(0));
        try {
            for (String[] row : allCsvData) {
                configvariable.setStringVariable(row[1], row[0]);
            }
        } catch (Exception e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Remove the blank lines from csv file");
        }
    }
}
