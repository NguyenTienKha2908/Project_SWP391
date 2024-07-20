// playwright.config.js
const { devices } = require('@playwright/test');

module.exports = {
  timeout: 30000,
  retries: 2,
  reporter: [
    ['list'], // List reporter for console output
    ['html', { outputFolder: 'playwright-report' }]
  ],
  use: {
    headless: true,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
  ],
};

//npx playwright test --reporter=list
