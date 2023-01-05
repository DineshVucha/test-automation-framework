package com.automation.platform.runner;

/*
@ComponentScan(basePackages = {"com.mobile.template", "com.automation.platform"})
@Configuration
@CucumberOptions(
        monochrome = true,
        features = "classpath:features",
        glue = "com.automation.platform.tapsteps",
        tags = "@test_web",

        plugin = {"pretty",
                "html:reports/cucumber/cucumber-html/report.html",
                "json:target/cucumber/cucumber.json",
                "usage:reports/cucumber-usage.json",
                "junit:reports/newrelic-report/cucumber-junit.xml"
//                "com.epam.reportportal.cucumber.ScenarioReporter" //for reportPortal.io
        })

public class CucumberRunner extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberRunner.class);

    private Configvariable configvariable;
    private SeleniumBase seleniumBase;
//    private HelperMethods helperMethods;

    @BeforeSuite(alwaysRun = true)
    public void setUpEnvironmentToTest() throws Exception {
        // write if anything needs to be set up once before tests run. e.g. connection to database
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TapBeansLoad.setConfigClass(CucumberRunner.class);
        TapBeansLoad.init();
        configvariable = (Configvariable) TapBeansLoad.getBean(Configvariable.class);
        seleniumBase = (SeleniumBase) TapBeansLoad.getBean(SeleniumBase.class);
        seleniumBase.initializeSeleniumFramework();
//        helperMethods = (HelperMethods) TapBeansLoad.getBean(HelperMethods.class);
//        LOGGER.info("Setting environment file....");
//        configvariable.setupEnvironmentProperties(System.getProperty("app.env"), System.getProperty("app.lbu"));
//        LOGGER.info("Setting localization file....");
//        helperMethods.loadLocalizationFile(System.getProperty("app.lbu"), System.getProperty("app.language"), System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME"));
//
        //Test Execution screen recording
        ScreenRecording.startRecording("ProjectName");
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUp() throws Exception {
        // close if something enabled in @before suite. e.g. closing connection to DB
        LOGGER.info("Copying and generating reports....");
        String deviceFarmLogDir = System.getenv("DEVICEFARM_LOG_DIR");
        TapReporting.generateReportForJsonFiles(deviceFarmLogDir);
        // so it might get exception
        LOGGER.info("Quiting driver if needed....");
        if (SeleniumBase.driver != null) {
//            SeleniumBase.driver.quit();
        }
        ScreenRecording.stopRecording();
        FileReaderUtil.deleteFile("reports/mobile-app-test-results.pdf");
        TapReporting.detailedReport("reports/cucumber/cucumber.json", "mobile-app");

    }

} */