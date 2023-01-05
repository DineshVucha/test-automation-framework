@test_web
Feature: Login to web app

  Background:
    Given I load environment property file "uat" into global property map for lbu "sg"

  Scenario: Login to Web portal
    Given I launch browser application "https://ultimateqa.com/automation"
#    Then I verify element "//span[@id='Automation_Practice']" is displayed
#    And I get browser title into variable "BROWSER_TITLE"
#    Then I verify expected values with actual values as below
#      | ${BROWSER_TITLE} | ${app.title} |