# PlaceLab Module Roads - Road Analysis testing

This README provides information and guidelines for testing the PlaceLab Module Roads: Road Analysis.

## Table of Contents
- [Introduction](#introduction)
- [Testing Scope](#testing-scope)
- [Prerequisites](#prerequisites)
- [Test Plan](#test-plan)
- [Test Execution](#test-execution)
- [Test Data](#test-data)
- [Contributing](#contributing)
- [Resources](#resources)
- [Contact Information](#contact-information)

## Introduction

The purpose of this documentation is to guide testers in performing effective testing on the PlaceLab Module ROADS: Road Analysis. By following the guidelines provided, testers can ensure the quality and reliability of this module.

## Testing Scope

The testing will encompass the following areas:

- Functional testing
- Smoke testing
- Regression testing
- API testing

## Prerequisites

Before testing the software, ensure the following prerequisites are met:

- Supported web browsers and versions: 
   
    Chrome Version 114.0.5735.91 
   
    Edge Version 113.0.1774.57
- Valid credentials for login

## Test Plan

Testing strategy and objectives:

- The goal of this testing is to validate creating Road Analysis report and deleting it
- These tests are created for covering the creation of report with valid file, checking if it is created, deleting it, the creation of report with invalid file, checking the status of created report and deleting it adn trying to create report with empty file.
- Types of tests to be performed
- Testing methodology white-box and black-box
- White-box tests are executed as a Maven project using TestNG and Selenium dependencies
- Black-box tests are executed as a Postman collection

## Test Execution

To execute the white-box automation test cases, follow these steps:

1. Open Terminal
2. Enter mvn clean verify command into terminal
3. Pass parameters -D (browser, email, password) values along with the command above
4. Press enter to start execution of test cases

## Test Data

For specific test data refer to the [Files](#src/test/java/com/qamp/placelab/ui/utilities/files) directory.

## Contributing

To contribute to the testing efforts, follow these steps:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your test cases or improvements.
3. Commit and push your changes to your branch.
4. Submit a pull request with a detailed description of your changes.

## Resources

- [Testing Framework](#src/test/resources/testng.xml)

## Contact Information

For any questions, feedback, or issues related to testing, feel free to contact me:

- Email: lejla_m96@live.com

I appreciate your involvement in the testing process and value your contributions.
