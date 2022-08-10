$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("classpath:features/Web.feature");
formatter.feature({
  "name": "Login to web app",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@test_web"
    }
  ]
});
formatter.background({
  "name": "",
  "description": "",
  "keyword": "Background"
});
formatter.step({
  "name": "I load environment property file \"uat\" into global property map for lbu \"sg\"",
  "keyword": "Given "
});
formatter.match({
  "location": "FileHandlingSteps.loadEnvironmentPropertyFile(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Login to Web portal",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@test_web"
    }
  ]
});
formatter.step({
  "name": "I launch browser application \"https://ultimateqa.com/automation\"",
  "keyword": "Given "
});
formatter.match({
  "location": "UISteps.launchBrowserApplication(String)"
});
formatter.result({
  "status": "passed"
});
});