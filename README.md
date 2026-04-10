# Obilet E2E Test Automation Framework

This repository contains an end-to-end (E2E) test automation project for booking bus tickets on the Obilet platform. The framework is built using Java, Selenium WebDriver, and TestNG, and it follows the Page Object Model (POM) design pattern.
---

## 🚀 Features

* **Page Object Model (POM):** Separates UI elements and test logic for easier maintenance.
* **Centralized Logging:** Uses Log4j2 for tracking test execution (INFO, WARN, ERROR, DEBUG).
* **Driver Factory:** Manages WebDriver in a single place for consistent browser sessions.
* **Dynamic Data Handling:** Handles past dates and adjusts them to valid future dates when needed.
* **Robust Assertions:** Checks search results, filters (Company, Seat Type, Terminal), and page redirects.
* **Flexible Seat Selection:** Selects seats based on gender and availability rules.

---

## 🛠️ Tech Stack

* **Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Logging:** Log4j2
* **Driver Management:** WebDriverManager

---

## 📂 Project Structure

```plaintext

src/test
├── java
│   ├── pages          # Page Object classes (Locators and Actions)
│   ├── utils          # DriverFactory, LogHelper, and configurations
│   └── tests          # Test execution classes (BaseTest and E2ETests)
└── resources          # log4j2.xml, configuration.properties and other files
```


## 🌍 Domain Specific Knowledge: Gender-Based Seating

In the Turkish intercity bus industry, there is a special rule that affects the user flow and test automation:

* **Gender Segregation:** Passengers usually cannot sit next to someone of the opposite gender unless tickets are bought together.
* **The Challenge:** This makes E2E automation harder. A seat may look empty, but the system may not allow selection based on gender rules. If we always click a fixed seat (e.g. “Seat 5”), tests can fail often.
* **The Solution:** This framework uses a dynamic Seating Intelligence logic to make tests more stable:
    * **Dynamic DOM Scanning:** Finds available seats using the obilet:available attribute in real time.
    * **Context-Aware Matching:** Matches seat availability with the targetGender used in the test.
    * **Resilient Execution:** Only clicks seats that are allowed by the system, which helps prevent errors like “ElementClickIntercepted” or “Selection Denied”.

## 📝 Test Scenarios

The **E2ETest** covers a full user journey for booking a bus ticket:

1. **Navigate:** Opens the browser and goes to the [Obilet.com](https://www.obilet.com) homepage.

2. **Search:** Enters origin, destination, and selects a travel date (also fixes past dates if needed).

3. **Filter:** Applies filters like bus company, seat type, and origin terminal.

4. **Verify Listing:** Checks that all results match the selected filters.

5. **Journey Selection:** Selects the first available valid journey.

6. **Seat Selection & Gender Logic:**
    - Finds an available seat based on passenger gender.
    - Handles gender popups when they appear.

7. **Package Handling:** Closes optional popups or extra service screens.

8. **Redirection Check:** Verifies that the user is successfully redirected to the Checkout page.


## 📊 Sample Log Output

The framework shows detailed logs during execution. Example:
```plaintext
INFO - STEP 1: Searching for bus tickets from Ankara to Samsun
WARN - Target date is in the past. Adjusted to default: 2026-04-12
INFO - STEP 2: Applying filters for Company: Ali Osman Ulusoy
INFO - Successfully verified 41 journeys. All match the filters.
INFO - SUCCESS: E2E Bus Booking Flow completed successfully.
```


##