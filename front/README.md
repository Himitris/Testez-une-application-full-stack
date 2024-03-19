# Yoga app

Yoga is a booking platform for Yoga sessions.

This is the Yoga app front-end.

## Installation

To install the necessary dependencies for the app, run the following command in your terminal:

npm install

This will install all dependencies listed in the package.json file

## Tests

### Unitary and Integration Tests

Execute [npm run test] to launch unit and integration tests.

Tests report with code coverage is available at [coverage/jest/lcov-report/index.html].

### E2E Tests

Execute [npm run e2e] to launch the e2e tests.

Select a browser and run ONLY [all.cy.ts] file in order to launch all the tests in one time and get the global code coverage (run another test file will erase the previous code coverage data).

Then execute [npm run e2e:coverage] to calculate the code coverage.

Tests report with code coverage is available at [coverage/lcov-report/index.html].

## Run

To run the app in dev mode, execute [npm run start].

In a browser go to [http://localhost:4200] to use the app (start the back-end before !).

## Folder structure