package com.automation.platform.tapsteps;

import com.automation.platform.config.Configvariable;
import com.automation.platform.config.TapBeansLoad;
import com.automation.platform.cucumberUtils.ScenarioUtils;
import com.automation.platform.filehandling.AzureStorageUtils;
import io.cucumber.java.en.*;

public class AzureStorageSteps {

    private AzureStorageUtils azureStorageUtils = (AzureStorageUtils) TapBeansLoad.getBean(AzureStorageUtils.class);

    private Configvariable configvariable = (Configvariable) TapBeansLoad.getBean(Configvariable.class);

    private ScenarioUtils scenarioUtils = (ScenarioUtils) TapBeansLoad.getBean(ScenarioUtils.class);
    public static int STORAGE_WAIT_TIME = 300;

    @Then("^I download azure storage file \"([^\"]*)\" from storage folder \"([^\"]*)\" to location \"([^\"]*)\"$")
    public void downloadAzureFile(String fileName, String folderName, String downloadFilePath) {
        String formattedFileName = configvariable.expandValue(fileName);
        String formattedFolderName = configvariable.expandValue(folderName);
        String formattedFilePath = configvariable.expandValue(downloadFilePath);
        scenarioUtils.write("Azure storage file name is " + formattedFileName);
        scenarioUtils.write("Azure storage folder name is " + formattedFolderName);
        scenarioUtils.write("File Download Path is " + formattedFilePath);
        azureStorageUtils.downloadFileFromAzureStorageToLocal(formattedFolderName, formattedFileName, formattedFilePath);
    }


    @Then("^I upload local file \"([^\"]*)\" to storage folder \"([^\"]*)\" from location \"([^\"]*)\"$")
    public void uploadAzureFile(String fileName, String folderName, String downloadFilePath) {
        String formattedFileName = configvariable.expandValue(fileName);
        String formattedFolderName = configvariable.expandValue(folderName);
        String formattedFilePath = configvariable.expandValue(downloadFilePath);
        scenarioUtils.write("Azure storage file name is " + formattedFileName);
        scenarioUtils.write("Azure storage folder name is " + formattedFolderName);
        scenarioUtils.write("File upload Path is " + formattedFilePath);
        azureStorageUtils.uploadFileToAzureStorageFromLocal(formattedFolderName, formattedFileName, formattedFilePath);
    }

    @Then("^I wait for storage file \"([^\"]*)\" to be processed from storage folder \"([^\"]*)\"$")
    public void waitForAzureFileToProcess(String fileName, String folderName) throws InterruptedException {
        String formattedFileName = configvariable.expandValue(fileName);
        String formattedFolderName = configvariable.expandValue(folderName);
        int waitTime = STORAGE_WAIT_TIME;
        scenarioUtils.write("Azure storage file name is " + formattedFileName);
        scenarioUtils.write("Azure storage folder name is " + formattedFolderName);

        azureStorageUtils.waitForFileToProcess(formattedFolderName, formattedFileName, waitTime);
    }

    @Then("^I get azure storage file from storage folder \"([^\"]*)\" for request \"([^\"]*)\" and timestamp \"([^\"]*)\"$")
    public void getFileName(String folderName, String actionType, String timeStamp) {
        String formattedFolderName = configvariable.expandValue(folderName);
        String formattedActionType = configvariable.expandValue(actionType);
        String formattedDate = configvariable.expandValue(timeStamp);
        scenarioUtils.write("Azure storage folder name is " + formattedFolderName);
        scenarioUtils.write("Action type is " + formattedActionType);
        scenarioUtils.write("Timestamp is " + formattedDate);
        azureStorageUtils.getFileFromAzureStorageToLocal(formattedFolderName, formattedActionType, formattedDate);
    }

    @Then("^I get azure storage file list from storage folder \"([^\"]*)\" for request \"([^\"]*)\" with in timestamp1 \"([^\"]*)\" and timestamp2 \"([^\"]*)\"$")
    public void getFileList(String folderName, String actionType, String timeStamp1, String timeStamp2) {
        String formattedFolderName = configvariable.expandValue(folderName);
        String formattedActionType = configvariable.expandValue(actionType);
        String formattedDate1 = configvariable.expandValue(timeStamp1);
        String formattedDate2 = configvariable.expandValue(timeStamp2);
        int waitTime = STORAGE_WAIT_TIME;
        scenarioUtils.write("Azure storage folder name is " + formattedFolderName);
        scenarioUtils.write("Action type is " + formattedActionType);
        scenarioUtils.write("Timestamp1 is " + formattedDate1);
        scenarioUtils.write("Timestamp2 is " + formattedDate2);
        azureStorageUtils.getFileUsingLocalUsingSchedulerTimeStamp(formattedFolderName, formattedActionType, formattedDate1, formattedDate2, waitTime);
    }
}


