const config = {
    reporter: [['html', {
        outputFolder: 'my-report',
        open: 'never',
        host: '0.0.0.0',
        port: 9223,
    }]],
};

module.exports = config;


//npx playwright test --config=playwright.html.config.js