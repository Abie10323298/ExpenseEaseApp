
# ExpenseEase: Personal Finance Tracker App

---

## Overview

**ExpenseEase** is a comprehensive Android application designed to streamline expense tracking and budget management. Featuring a clean and intuitive interface, ExpenseEase empowers users to monitor spending habits, set financial goals, and gain actionable insights into their finances. The application is fully developed, user-friendly, and ready for immediate deployment.

---

## Key Features

- **Dashboard:**  
  Get a real-time financial overview with insightful spending analytics.

- **Expense Logging:**  
  Seamlessly record expenses including amount, description, category, and attach photos for detailed tracking.

- **Expense History:**  
  Browse, filter, and review your complete expense history with ease.

- **Budget Goals:**  
  Set customizable monthly spending targets to stay on track financially.

- **Category Management:**  
  Create, edit, and manage custom spending categories tailored to your needs.

- **Debt Management:**  
  Set and monitor debt payoff goals to achieve financial freedom.

- **Achievements:**  
  Earn badges and celebrate financial milestones as you progress.

- **Educational Resources:**  
  Access curated financial tips and guides to enhance your financial literacy.

- **Secure Authentication:**  
  Robust login and account protection for peace of mind.

---

## Technical Requirements

- **Android Studio:** Narwhal (2023.2.1) or newer  
- **Android SDK:** API Level 33 (Android 13) or higher  
- **Java:** Version 17+  
- **Kotlin:** Version 1.9+  

---

## Installation Guide (Android Studio Narwhal)

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ExpenseEase.git
cd ExpenseEase
```

### 2. Open the Project

- Launch **Android Studio Narwhal**
- Select **"Open"** from the Welcome screen
- Navigate to the cloned `ExpenseEase` directory
- Click **"OK"**

### 3. Configure Project Settings

- **Sync Gradle Dependencies:**  
  Click **"Sync Project with Gradle Files"** in the toolbar and wait for dependencies to download (first sync may take 5-10 minutes).

- **Set JDK Version:**  
  Navigate to `File > Project Structure > SDK Location`  
  Ensure **"JDK 17"** is selected under Gradle Settings.

- **Update Build Configuration:**  
  Open `build.gradle (Module: app)` and verify the following:
  ```gradle
  compileSdk 33
  minSdk 24
  targetSdk 33
  ```

### 4. Configure Virtual Device (Emulator)

- Go to **Tools > Device Manager**
- Create a new device:
  - Select **Phone** category > **Pixel 7**
  - System Image: **"Tiramisu" (API 33) with Google Play**
  - Complete setup with recommended settings

### 5. Build and Run the Application

- Select your virtual device from the toolbar dropdown
- Click **"Run"** (green triangle) or press `Shift+F10`
- Note: The first build may take 3-5 minutes

---

## Getting Started

1. **Sign Up:**  
   Create your account using your email and password.

2. **Set Budget Goals:**  
   Configure your monthly spending targets.

3. **Add Expenses:**  
   Log transactions, assign categories, and attach receipts.

4. **Track Progress:**  
   Monitor your spending through the interactive dashboard.

5. **Manage Debt:**  
   Set and track your debt payoff goals.

6. **Earn Badges:**  
   Achieve milestones and celebrate your financial progress.

---

## Application Screens

- **Login/Signup:** Secure account creation and access
- **Dashboard:** Visual financial summaries and analytics
- **Expense Management:** Add/view expenses categorized for clarity
- **Budget Goals:** Set and monitor your monthly targets
- **Debt Manager:** Plan and track debt repayment progress
- **Achievements:** Celebrate your financial milestones

---

Empower your financial journey with **ExpenseEase**—your all-in-one personal finance tracker.
