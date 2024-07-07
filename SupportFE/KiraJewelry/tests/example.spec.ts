// import { test, expect } from '@playwright/test';
const { test, expect } = require('@playwright/test')
//*********** TEST UI/UX ***********/

/* 1. Login */

test('check login UI', async ({ page }) => {
  await page.goto('http://localhost:8081/login');

  // Check if the login image is visible
  const loginImage = page.locator('img[alt="LoginBackground"]');
  const isImageVisible = await loginImage.isVisible();

  if (!isImageVisible) {
    throw new Error('Login image is not visible');
  } else {
    await page.screenshot({ path: 'screenshots/LoginBGPassesd.png' });
    console.log('Login image is visible');
  }
})

/********* Check Register *********/
test('successful registration with valid data', async ({ page }) => {
  await page.goto('http://localhost:8081/registration');

  // VALID data test
  await page.fill('#fullname', 'Sayuri');
  await page.fill('#email', 'sayuri@gmail.com');
  await page.fill('#phone', '0908123123');
  await page.fill('#password', 'H@ello218');
  await page.fill('#address', 'ABC 204 Road');

  await Promise.all([
    page.waitForNavigation(), // Ensure navigation completes
    page.click('.btn-register')
  ]);

  // Assert redirection to success page or message
  const url = page.url();
  expect(url).toContain('/registration?success');

  // Check for success message in toast
  const successToast = page.locator('.mess-content:has-text("Registered Successfully")');
  await successToast.waitFor({ state: 'visible' });
  console.log('Registered Successfully with successful message visible');
  await page.screenshot({ path: 'screenshots/successRegister.png' });
});

test('registration with existing email', async ({ page }) => {
  await page.goto('http://localhost:8081/registration');

  // Fill form with existing email
  await page.fill('#fullname', 'An Dao');
  await page.fill('#email', 'customer2@example.com');
  await page.fill('#phone', '9876543210');
  await page.fill('#password', '@Hello218');
  await page.fill('#address', '18B ABC Road');

  await Promise.all([
    page.waitForNavigation(),
    page.click('.btn-register')
  ]);

  // Assert redirection to error page or message
  const url = page.url();
  expect(url).toContain('/registration?error');

  // Check for error message in toast
  const errorToast = page.locator('.mess-content:has-text("Registered Failed: Email is Taken")');
  await errorToast.waitFor({ state: 'visible' });
  console.log('Registration failed with error message visible');
  await page.screenshot({ path: 'screenshots/errorRegister.png' });
});

/********* Test Full Name Format ***********/
test('registration with invalid Full Name', async ({ page }) => {
  await page.goto('http://localhost:8081/registration');

  // Fill form test data
  await page.fill('#fullname', '123AnDao');
  await page.fill('#email', 'hello@gmail.com');
  await page.fill('#phone', '0908123123');
  await page.fill('#password', '@Hello218');
  await page.fill('#address', '18B ABC Road');

  await Promise.all([
    page.waitForNavigation(),
    page.click('.btn-register')
  ]);

  // Assert redirection to error page or message
  const url = page.url();
  expect(url).toContain('/registration?error');

  // Check for error message in toast
  const errorToast = page.locator('.mess-content:has-text("Invalid Full Name: Full Name cannot contain numbers!")');
  await errorToast.waitFor({ state: 'visible' });
  await page.waitForTimeout(1000);
  console.log('Registration failed with error message visible');
  await page.screenshot({ path: 'screenshots/errorRegister.png' });
});

/********* Test Email Format ***********/
// test('registration with invalid Email', async ({ page }) => {
//   await page.goto('http://localhost:8081/registration');

//   // Fill form test data
//   await page.fill('#fullname', 'An Dao');
//   await page.fill('#email', 'ilo@veugmail.com');
//   await page.fill('#phone', '0908123123');
//   await page.fill('#password', 'P@ssw0rd');
//   await page.fill('#address', '18B ABC Road');

//   await Promise.all([
//     page.waitForNavigation(),
//     page.click('.btn-register')
//   ]);

//   // Assert redirection to error page or message
//   const url = page.url();
//   expect(url).toContain('/registration?error');

//   // Check for error message in toast
//   const errorToast = page.locator('.mess-content:has-text("Invalid Email")');
//   await errorToast.waitFor({ state: 'visible' });
//   await page.waitForTimeout(500);
//   console.log('Registration failed with error message visible');
//   await page.screenshot({ path: 'screenshots/errorRegister.png' });
// });

/********* Test Phone Format ***********/
test('registration with invalid Phone', async ({ page }) => {
  await page.goto('http://localhost:8081/registration');

  // Fill form test data
  await page.fill('#fullname', 'An Dao');
  await page.fill('#email', 'iloveu@gmail.com');
  await page.fill('#phone', '09081223');
  await page.fill('#password', 'P@ssw0rd');
  await page.fill('#address', '18B ABC Road');

  await Promise.all([
    page.waitForNavigation(),
    page.click('.btn-register')
  ]);

  // Assert redirection to error page or message
  const url = page.url();
  expect(url).toContain('/registration?error');

  // Check for error message in toast
  const errorToast = page.locator('.mess-content:has-text("Invalid Phone Number: Must start with 0 and have 10 digits")');
  await errorToast.waitFor({ state: 'visible' });
  await page.waitForTimeout(500);
  console.log('Registration failed with error message visible');
  await page.screenshot({ path: 'screenshots/errorRegister.png' });
});


/********* Test Password Format ***********/
test('registration with invalid Password', async ({ page }) => {
  await page.goto('http://localhost:8081/registration');

  // Fill form test data
  await page.fill('#fullname', 'An Dao');
  await page.fill('#email', 'iloveu@gmail.com');
  await page.fill('#phone', '0908123123');
  await page.fill('#password', 'Holla');
  await page.fill('#address', '18B ABC Road');

  await Promise.all([
    page.waitForNavigation(),
    page.click('.btn-register')
  ]);

  // Assert redirection to error page or message
  const url = page.url();
  expect(url).toContain('/registration?error');

  // Check for error message in toast
  const errorToast = page.locator('.mess-content:has-text("Invalid Password: Password must be 8 - 6 characters, 1 special character, 1 digit, and 1 uppercase")');
  await errorToast.waitFor({ state: 'visible' });
  await page.waitForTimeout(500);
  console.log('Registration failed with error message visible');
  await page.screenshot({ path: 'screenshots/errorRegister.png' });
});

/********* Check Login *********/
test('check login', async ({ page }) => {
  await page.goto('http://localhost:8081/login');

  // //VALID email and password
  // await page.locator('input[name="username"]').type('customer1@example.com');
  // await page.locator('input[name="password"]').type('Cust1#Secure');

  //INVALID email and password
  await page.locator('input[name="username"]').type('hoola@gmail.com');
  await page.locator('input[name="password"]').type('12345');

  await page.locator('input[name="btn-login"]').click();

  // Check for success or error message in toast
  try {
    const successToast = page.locator('.mess-content:has-text("Login Successfully")');
    const errorToast = page.locator('.mess-content:has-text("Incorrect Username or Password")');

    const firstVisibleToast = await Promise.race([
      successToast.waitFor({ state: 'visible' }).then(() => 'success'),
      errorToast.waitFor({ state: 'visible' }).then(() => 'error')
    ]);

    if (firstVisibleToast === 'success') {
      console.log('Login successful message is visible');
      await page.waitForTimeout(1000);
      await page.screenshot({ path: 'screenshots/successLogin.png' });
      await page.waitForURL('http://localhost:8081/', { timeout: 10000 });
    } else if (firstVisibleToast === 'error') {
      console.log('Login failed message is visible');
      await page.waitForTimeout(1000);
      await page.screenshot({ path: 'screenshots/errorLogin.png' });
    } else {
      throw new Error('Neither success nor error toast is visible');
    }
  } catch (e) {
    console.error('Error during login process:', e);
  }
});

/*********** 2. Making a Request ************/
test('check making request', async ({ page }) => {
  await page.goto('http://localhost:8081/login');

  // VALID email and password
  await page.locator('input[name="username"]').type('customer4@example.com');
  await page.locator('input[name="password"]').type('Cust4%Tough');

  await page.locator('input[name="btn-login"]').click();

  // Success Message
  await page.locator('.mess-content:has-text("Login Successfully").open').waitFor({ state: 'visible', timeout: 15000 });

  // Homepage first
  await page.waitForURL('http://localhost:8081/', { timeout: 15000 });

  // Request next
  await page.goto('http://localhost:8081/request');

  //Test Data | VALID
  await page.locator('select[name="category"]').selectOption({ index: 0 }); // Select RING
  await page.locator('input[name="productSize"]').type('13');
  await page.locator('textarea[name="description"]').type('I want to order a beautiful diamond ring for my hubby');
  // await page.locator('input[name="file"]').setInputFiles('https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/Test%2Fadjustable-1-rose-mkr147-ring-myki-original-imaghfvvhtdwr3fc.png?alt=media&token=cc6f4e59-34c0-4dc0-be54-96d173016b99');

  await page.setInputFiles('#file', './testImage/ringTest.png')
  await page.locator('button[type="submit"]').click();

  // Check for success message after form submission
  await page.locator('.mess-content:has-text("Sending Request Successfully").open').waitFor({ state: 'visible', timeout: 15000 });

  await page.waitForTimeout(1000);
  await page.screenshot({ path: 'screenshots/request_success.png' });

});


test('get started link', async ({ page }) => {
  await page.goto('https://playwright.dev/');

  // Click the get started link.
  await page.getByRole('link', { name: 'Get started' }).click();

  // Expects page to have a heading with the name of Installation.
  await expect(page.getByRole('heading', { name: 'Installation' })).toBeVisible();
});
