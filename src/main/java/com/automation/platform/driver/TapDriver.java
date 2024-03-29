package com.automation.platform.driver;

import com.automation.platform.config.Configvariable;
import com.automation.platform.exception.TapException;
import com.automation.platform.exception.TapExceptionType;
import com.epam.healenium.SelfHealingDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */
@Component
public class TapDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(TapDriver.class);

    @Autowired
    Configvariable configvariable;

    public WebDriver driver = null;
    protected static Properties lobConfigProp = new Properties();
    public static Properties localeConfigProp = new Properties();
    protected FileInputStream configFis;
    protected FileInputStream lobConfigFis;
    protected FileInputStream localeConfigFis;
    private Properties configProp = new Properties();
    public DesiredCapabilities capabilities = new DesiredCapabilities();
    public ChromeOptions chromeOptions = new ChromeOptions();
    public FirefoxOptions firefoxOptions = new FirefoxOptions();
    public InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();

    private static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private static int DEFAULT_IMPLICIT_WAIT = 2;
    public static boolean NO_RESET = false;
    private static String PROXY_USER = System.getProperty("proxy.user");
    private static String PROXY_PASS = System.getProperty("proxy.pass");
    private static String PROXY_URL = System.getProperty("proxy.url");


    /**
     * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
     *
     * @throws Exception Unable to start appium server
     */
    //@BeforeSuite
    public void invokeAppium() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        try {
            startAppiumServer(OS);
            LOGGER.info("Appium server started successfully");
        } catch (Exception e) {
            LOGGER.warn("Unable to start appium server");
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Unable to start appium server");
        }
    }

    /**
     * this method stops Appium server.Calls stopAppiumServer method to
     * stop session depending upon your OS.
     *
     * @throws Exception Unable to stop appium server
     */
    //@AfterSuite
    public void stopAppium() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        try {
            stopAppiumServer(OS);
            LOGGER.info("Appium server stopped successfully");
        } catch (Exception e) {
            LOGGER.warn("Unable to stop appium server");
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Unable to stop appium server");
        }
    }


    /**
     * this method quit the driver after the execution of test(s)
     */
    //@AfterMethod
    public void teardown() {
        LOGGER.info("Shutting down driver");
        driver.quit();
    }


    /**
     * this method creates the android driver
     *
     * @param buildPath - path to pick the location of the app
     * @param appPkg    - Android app package name
     * @param appAct    - Android app activity name
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void androidDriver(String buildPath, String appPkg, String appAct) throws MalformedURLException {
        if (buildPath != "") {
            File app = new File(buildPath);
            capabilities.setCapability("app", app.getAbsolutePath());
        }

        capabilities.setCapability("deviceName", "Android-Test");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", appPkg);
        capabilities.setCapability("appActivity", appAct);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10000);
        capabilities.setCapability("noReset", NO_RESET);
        capabilities.setCapability("automationName", "UiAutomator2");
        if (System.getProperty("device.udid") != null) {
            capabilities.setCapability("udid", System.getProperty("device.udid"));
        }
        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT, TimeUnit.SECONDS);

    }

    /**
     * this method creates the iOS driver
     *
     * @param buildPath-  path to pick the location of the app
     * @param iosBundleId - ios app bundle id
     * @param iosUDID     - device UDID
     * @param iosVersion  - device version
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void iOSDriver(String buildPath, String iosBundleId, String iosUDID, String iosVersion) throws MalformedURLException {
        if (buildPath != "") {
            File app = new File(buildPath);
            capabilities.setCapability("app", app.getAbsolutePath());
        }
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iOS-Test");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("bundleId", iosBundleId);
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("noReset", NO_RESET);
        if (System.getenv("DEVICEFARM_LOG_DIR") == null && System.getProperty("device.udid") == null) {
            if (iosUDID.isEmpty() || iosVersion.isEmpty()) {
                throw new TapException(TapExceptionType.PROCESSING_FAILED, "Please supply ios udid and platform version to move forward");
            }
            capabilities.setCapability("udid", iosUDID);
            capabilities.setCapability("platformVersion", iosVersion);
        } else if (System.getProperty("device.udid") != null) {
            capabilities.setCapability("udid", System.getProperty("device.udid"));
            capabilities.setCapability("platformVersion", System.getProperty("device.version"));
        }
        driver = new IOSDriver(new URL(APPIUM_SERVER_URL), capabilities);
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public WebDriver chromeBrowserDriver() {
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities;
        WebDriver driver = null;
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "chrome-Test");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("chromedriverExecutable", WebDriverManager.chromedriver().getBinaryPath());

        try {
            driver = new RemoteWebDriver(new URL(APPIUM_SERVER_URL), capabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Not able to create driver");
        }

        return driver;
    }

    public WebDriver createHTMLUnitDriver(String Url) {
        WebDriver driver = new HtmlUnitDriver();
        driver.get(Url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver createPhantomJsDriver() {
        if (configvariable.isProxyRequired()) {
            WebDriverManager.phantomjs().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.phantomjs().setup();
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("locationContextEnabled", true);
        caps.setCapability("applicationCacheEnabled", true);
        caps.setCapability("browserConnectionEnabled", true);
        caps.setCapability("localToRemoteUrlAccessEnabled", true);
        caps.setCapability("locationContextEnabled", true);
        String[] phantomArgs = new String[]{
                "--webdriver-loglevel=NONE"
        };
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        WebDriver driver = new PhantomJSDriver(caps);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver createChromeDriver() {
        if (configvariable.isProxyRequired()) {
            WebDriverManager.chromedriver().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }
        Map<String, Object> prefs = new HashMap<String, Object>();
        //to switch off browser notification Pass the argument 1 to allow and 2 to block
        prefs.put("profile.default_content_setting_values.notifications", 1);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", configvariable.getStringVar("web.browser.download"));

        chromeOptions.setExperimentalOption("prefs", prefs);
        if ("true".equalsIgnoreCase(configvariable.getStringVar("web.browser.incognito"))) {
            chromeOptions.addArguments("--incognito");
        }

        if ("true".equalsIgnoreCase(configvariable.getStringVar("web.driver.headless"))) {
            LOGGER.debug("Running in headless mode");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--verbose");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--window-size=1920,1080");
        }
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);

        chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

//        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver delegate = new ChromeDriver(chromeOptions);
        //create Self-healing driver
        SelfHealingDriver driver = SelfHealingDriver.create(delegate);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }


    /**
     * this method starts the appium  server depending on your OS.
     *
     * @param os your machine OS (windows/linux/mac)
     * @throws IOException          Signals that an I/O exception of some sort has occurred
     * @throws ExecuteException     An exception indicating that the executing a subprocesses failed
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public void startAppiumServer(String os) throws ExecuteException, IOException, InterruptedException {
        if (os.contains("windows")) {
            CommandLine command = new CommandLine("cmd");
            command.addArgument("/c");
            command.addArgument("C:/Program Files/nodejs/node.exe");
            command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");
            command.addArgument("--address", false);
            command.addArgument("127.0.0.1");
            command.addArgument("--port", false);
            command.addArgument("4723");
            command.addArgument("--full-reset", false);

            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000);
        } else if (os.contains("mac")) {
            CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
            command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js", false);
            command.addArgument("--address", false);
            command.addArgument("127.0.0.1");
            command.addArgument("--port", false);
            command.addArgument("4723");
            command.addArgument("--full-reset", false);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000);
        } else if (os.contains("linux")) {
            //Start the appium server
            System.out.println("ANDROID_HOME : ");
            System.getenv("ANDROID_HOME");
            //	System.out.println("PATH :" +System.getenv("PATH"));
            CommandLine command = new CommandLine("/bin/bash");
            command.addArgument("-c");
            command.addArgument("~/.linuxbrew/bin/node");
            command.addArgument("~/.linuxbrew/lib/node_modules/appium/lib/appium.js", true);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000); //Wait for appium server to start

        } else {
            LOGGER.info(os + "is not supported yet");
        }
    }

    /**
     * this method stops the appium  server.
     *
     * @param os your machine OS (windows/linux/mac).
     * @throws IOException      Signals that an I/O exception of some sort has occurred.
     * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
     */
    public void stopAppiumServer(String os) throws ExecuteException, IOException {
        if (os.contains("windows")) {
            CommandLine command = new CommandLine("cmd");
            command.addArgument("/c");
            command.addArgument("Taskkill /F /IM node.exe");

            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
        } else if (os.contains("mac os x")) {
            String[] command = {"/usr/bin/killall", "-KILL", "node"};
            Runtime.getRuntime().exec(command);
            LOGGER.info("Appium server stopped");
        }
    }


    public WebDriver getWebDriver() {
        return this.driver;
    }

    public WebDriver createIEDriver() {
        if (configvariable.isProxyRequired()) {
            WebDriverManager.iedriver().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.iedriver().setup();
        }

        internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        internetExplorerOptions.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");

        WebDriver driver = new InternetExplorerDriver(internetExplorerOptions);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver createFirefoxDriver() {
        if (configvariable.isProxyRequired()) {
            WebDriverManager.firefoxdriver().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.firefoxdriver().setup();
        }
        firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        firefoxOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        WebDriver driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver createSafariDriver() {
        WebDriver driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver createEdgeDriver() {
        if (configvariable.isProxyRequired()) {
            WebDriverManager.edgedriver().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
        } else {
            WebDriverManager.edgedriver().setup();
        }

        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(configvariable.getStringVar("web.implicit.wait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}

