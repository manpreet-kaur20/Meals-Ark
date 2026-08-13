# 🍽️ Meal Arks — AI Meal Planner

**Meal Arks** is a modern, premium Android application built with **Kotlin** and **Jetpack Compose**. It serves as an AI-powered meal planner, nutrition tracker, food scanner, and smart grocery assistant — featuring a highly interactive user interface built with **Material Design 3** and **Clean Architecture**.

> _⛵ Eat Smart, Live Well._

---

<p align="center">
  <img src="./app/src/main/res/drawable/meal_ark_gif.gif" width="280" alt="Meal Arks App Showcase">
</p>

## 📱 Features & Core Screens

### 1. ✨ Onboarding & Preference Profiler

- **Visual Splash Carousel:** 4-page `HorizontalPager` with Captain Gus mascot illustrations, animated page dot indicators, turquoise gradient backgrounds, and smooth `animateDpAsState` transitions.
- **Glassmorphic "Get Started" Button:** Glowing CTA navigating to the onboarding wizard.
- **Interactive 12-Step Onboarding Questionnaire:**
  - **Goals:** Select up to 3 fitness goals (Fat Loss, Build Muscle, Boost Energy, etc.) with toast notification on overflow.
  - **Activity Level:** Sedentary, Lightly Active, Moderately Active, Very Active, or Extra Active.
  - **About You:** Interactive cards opening `ModalBottomSheet` pickers for Sex, Age (14–99), Height (ft/in), and Weight (kg).
  - **Progress Motivation:** Captain Gus mascot graphics with motivational cards.
  - **Cuisines:** Multi-select cuisine chips with custom cuisine text input.
  - **Dietary Approach:** No specific diet, Vegetarian, Vegan, Keto, Low-carb, or Flexitarian.
  - **Allergies:** Multi-select allergy chips + "No allergies" toggle + custom allergy input field.
  - **Budget:** Low, Medium, or High selection.
  - **Variety Preferences:** Toggles for same lunch/dinner, pantry-first planning, and meal variety levels.
  - **Nutrition Targets:** Calorie goal slider (1000–3500 cal) with auto-calculated daily macro breakdown cards for Protein, Carbs, and Fat.
  - **Special Requests:** Free-text field with 200-character limit.
  - **Attribution:** "Where did you hear about us?" options (Google, Social Media, App Store, Ads, Friends, etc.).
- Animated progress bar with back navigation. Preferences persisted to DataStore.

---

### 2. 🔐 Authentication & Session Management

- **Sign In:** Email/password with `Patterns.EMAIL_ADDRESS` regex validation, trailing eye icon for password visibility toggle, and "Forgot Password?" link.
- **Sign Up:** First Name, Last Name, Email, Password, Confirm Password with live password requirement checklist (8+ chars, uppercase, lowercase, digit, special character) — green check / grey cross indicators.
- **Email Verification:** 6-digit OTP code dialog with "Verify" and "Resend" actions.
- **Forgot Password:** Email input with validation, and a confirmation card state ("Reset Link Sent! 📩") showing the target email address.
- **Google Sign-In:** Real OAuth authentication flow using `GoogleSignInOptions` via a reusable `rememberGoogleSignInLauncher` Compose helper with `ActivityResultContracts`.
- **Persistent Sessions:** State saved using **SharedPreferences** and **DataStore**, keeping the user logged in across app relaunches.

---

### 3. 🏠 Smart Home Dashboard

- **Floating 5-Item Navigation Bar:** Tabs for Home, Saved, Track, Analytics, plus a prominent center floating **Camera/Barcode Scanner FAB**.
- **Home Tab:**
  - User avatar with greeting ("Hi, android"), streak fire button, settings gear button, and mascot graphic.
  - Hero welcome card with **"Generate Daily Plan"** CTA. Swaps to `DailyMealPlanView` when a plan is generated.
  - Quick tool cards for **Meal Tracker**, **Food Scanner**, **Preferences**, and **Saved Plans**.
- **Saved Tab:** Displays saved daily meal plans or an empty state screen with calendar graphics.
- **Track Tab:** Horizontal day-selector pills (Mon–Sun), "Today's Nutrition" progress card (Calories, Protein, Carbs, Fat with target ranges), and an empty meals card with "Add Your First Meal".
- **Analytics Tab:** Weekly average daily intake macro grid, Weight tracking section (e.g., 74 kg with date), visibility toggle, "Update" button, and a custom **Canvas bar chart** with dashed threshold lines.

---

### 4. 🥗 Daily Meal Plan View

- Plan type toggle bar ("Daily" active vs "Weekly" locked with Premium badge).
- 3 Action buttons: **"Save Plan"**, **"Clear"**, **"Regenerate"**.
- **Grocery & Pantry** banner card.
- **Macro Summary Card:** Custom `Canvas` drawing a circular arc gauge for Calories alongside 3 progress bars for Protein, Carbs, and Fat.
- **4 Meal Cards** (Breakfast, Lunch, Dinner, Snack): Category badge, dish image, serving weight, macro chips, and 3 buttons (View Details, Log/Add to Tracker, Regenerate Meal).
- Additional Tips card with color-coded advice chips.
- Daily Usage limits card (Generations 2/3, Regenerations 0/3) with Premium CTA.
- **Dialogs:** Save Meal Plan (custom plan name input), Clear Meal Plan (unsaved data warning), and Regenerate Daily Meal Plan (unsaved changes warning).

---

### 5. 📸 AI Food Scanner

- **Multi-Step Food Logging Wizard:**
  - **Search Landing:** Search bar with voice mic input and scan tiles.
  - **AI Photo Recognition Intro:** Daily scan quota display (0/3 for free plan).
  - **Photo Guide:** "Good" vs "Avoid" food photo comparison examples.
  - **Camera Viewfinder:** Live camera frame for food image capture.
  - **Barcode Scanner:** Viewfinder overlay with toggle for manual numeric barcode input.
- Gallery photo picking via `ActivityResultContracts.GetContent()`.

---

### 6. 📅 Calendar Screen

- Monthly grid calendar view with day-of-week header row (Sun–Sat).
- **7-Column `LazyVerticalGrid`** rendering calendar days with `java.time.LocalDate` calculations.
- Highlights today's date with primary container color.
- **Dot indicators** under dates with assigned breakfast, lunch, or dinner dishes.
- Month navigation (previous/next).

---

### 7. 🍳 Dish Repository

- `LazyColumn` of dish cards showing dish name, category badge, and optional memo notes.
- **Floating Action Button** to add new dishes.
- **Add Dish Dialog:** Input fields for Dish Name, Category, Memo, and URL with save validation.

---

### 8. 📆 Weekly Planner

- Computes current week's dates from Monday to Sunday using `TemporalAdjusters`.
- `LazyColumn` of `DayPlanCard` items showing day of week and date.
- **3 meal slots per day:** Breakfast, Lunch, Dinner.
- Tapping a slot opens a **Dish Picker dialog** listing dishes from the repository.

---

### 9. ⚙️ Settings & Customization

- **Account Card:** Navigates to detailed Account screen.
- **General Section:** Preferences row, Push Notifications toggle, and Theme toggle (Light/Dark mode).
- **Plus Section:** "Upgrade to Premium" tile.
- **Legal & Support:** Privacy Policy, Terms of Service, Disclaimer, Help & Support.
- **Log Out:** Red button with confirmation `AlertDialog` that clears session and resets backstack.

---

### 10. 👤 User Profile & Account

- **3-Tab Segment Selector:**
  - **Overview:** Avatar card with user initials, personal info (Email, Member since), editable Full Name field with "Update Name" button, Plan Status badge.
  - **Plan:** Current plan metrics (Meal plans generated, Meals regenerated, Food images scanned) with limits, and "Upgrade to Premium" card with 5 bullet features.
  - **Security:** Password change form (Current, New, Confirm) with eye visibility toggles, Security Requirements checklist card, and a red "Danger Zone" card with "Delete Account" button.

---

### 11. 🎯 Preferences Wizard

- **4-Step Preference Configuration:**
  - **Personal Info:** Age, Sex, Height (ft/in), Weight (kg), Activity Level with descriptive text.
  - **Fitness Goals:** Plan type selector (Daily vs Weekly Premium) and 2-column selectable fitness goal chips.
  - **Diet Preferences:** Dietary Approach and Budget text input, interactive removable chips for Cuisine Preferences and Allergies.
  - **Nutrition:** Daily Calorie Goal slider (1200–3500 cal) with reset button, auto-calculated daily macro ranges, and Special Requests text field.

---

### 12. 💎 Premium Paywall

- **Dark-themed design** (`0xFF0D1520`).
- Headline: *"Your plan is ready"*.
- **4 Feature Cards (2×2 grid):** Meals That Fit You, Spend Smarter, Cook Confidently, Track Faster.
- **Pricing Plan Selector:** Segmented toggle between Annual (-50% badge @ ₹1,499.00/yr vs ~~₹2,999.99~~) and Monthly (@ ₹299.00/mo vs ~~₹499.00~~).
- **Testimonial Card** with user avatar, 5-star rating, quote, and pagination dots.
- Golden **"Subscribe"** button and Terms & Privacy disclaimer.

---

### 13. 📊 Nutrition Tracking & Analytics

- **Daily Streak Dialog:** Streak counter (Current / Best days), monthly freezes remaining, time-left timer, and "Add Meal" button.
- **Weight Update Dialog:** Numeric input with up/down arrow steppers and kg badge.
- **Canvas Bar Chart:** Custom-drawn weight tracking chart with dashed threshold lines.
- **Weekly Averages:** Macro grid showing daily intake summaries.

---

### 14. 🌐 Network Awareness

- **`NetworkObserver`:** Registers `ConnectivityManager.NetworkCallback` to emit `NetworkStatus.Available` or `NetworkStatus.Unavailable` as a Kotlin `Flow`.
- **`NoInternetBottomSheet`:** Non-dismissible `ModalBottomSheet` with red Wifi-off icon, "Retry Connection" button, status Toast feedback, and `BackHandler(enabled = true)` to block back navigation when offline.

---

## 🛠️ Architecture & Tech Stack

The application follows **Clean Architecture** with a strict separation into three layers:

```
┌────────────────────────────────┐
│      Presentation Layer        │  ViewModels, Compose UI, Navigation
├────────────────────────────────┤
│         Domain Layer           │  Use Cases, Models, Repository Interfaces
├────────────────────────────────┤
│          Data Layer            │  Room DB, Retrofit API, DataStore, Mappers
└────────────────────────────────┘
```

| Component              | Library / Framework                                                                                | Description                                                           |
|------------------------|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| **UI Framework**       | [Jetpack Compose](https://developer.android.com/jetpack/compose) + Material3                       | Declarative UI rendering with Material Design 3 theming.              |
| **Navigation**         | [Navigation3](https://developer.android.com/jetpack/compose/navigation)                            | Type-safe Compose navigation with `@Serializable` `NavKey` instances. |
| **Dependency Injection** | [Dagger Hilt](https://dagger.dev/hilt/)                                                          | Compile-time dependency injection framework for Android.              |
| **Local Database**     | [Room](https://developer.android.com/training/data-storage/room)                                   | SQLite persistence layer with reactive Flow-based queries.            |
| **Preferences**        | [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore)      | Key-value persistent storage for auth tokens, settings, and state.    |
| **Networking**         | [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)       | REST API communication with logging interceptor.                      |
| **JSON Parsing**       | [Moshi](https://github.com/square/moshi)                                                          | Kotlin-friendly JSON serialization/deserialization with codegen.      |
| **Image Loading**      | [Coil](https://coil-kt.github.io/coil/)                                                           | Async image loading optimized for Compose.                            |
| **Camera**             | [CameraX](https://developer.android.com/training/camerax)                                          | Camera integration for AI food scanning and barcode reading.          |
| **Location**           | [Play Services Location](https://developers.google.com/android/reference/com/google/android/gms/location/package-summary) | Location-based meal suggestions.                     |
| **Authentication**     | [Play Services Auth](https://developers.google.com/identity) + CredentialManager                   | Google Sign-In with OAuth credential management.                      |
| **Coroutines**         | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)                                | Asynchronous programming with structured concurrency.                 |
| **Serialization**      | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)                          | Type-safe navigation arguments and data serialization.                |
| **Adaptive UI**        | [Compose Adaptive](https://developer.android.com/jetpack/compose/adaptive)                         | Responsive layouts for different screen sizes and form factors.       |
| **Permissions**        | [Accompanist Permissions](https://google.github.io/accompanist/permissions/)                       | Simplified runtime permission handling for Compose.                   |

---

## 🚀 How to Run the Application

### Prerequisites

- **Android Studio** Hedgehog or newer (with Kotlin & Compose plugin)
- **JDK 17+**
- **Android SDK** with `compileSdk 37` / `minSdk 24`

### 🤖 Running the Android App

Run the following Gradle command from the root directory:

```bash
./gradlew :app:installDebug
```

Or open the root project in **Android Studio**, select `app` in the configuration selector, and click **Run** (▶️).

---

## 📂 Project Structure

```
.
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/aimealplanners/
│           │   ├── data/                          # Data Layer
│           │   │   ├── local/
│           │   │   │   ├── dao/                   # Room DAOs (MealPlanDao, DishDao, ShoppingItemDao)
│           │   │   │   ├── database/              # AppDatabase & Type Converters
│           │   │   │   ├── entity/                # Room Entities (MealPlanEntity, DishEntity, ShoppingItemEntity)
│           │   │   │   └── TokenManager.kt        # DataStore-based token & session manager
│           │   │   ├── mapper/                    # Entity ↔ Domain model mappers
│           │   │   ├── remote/
│           │   │   │   └── dto/                   # API DTOs (MealPlanDto, ShoppingDto)
│           │   │   └── repository/                # Repository implementations
│           │   │
│           │   ├── di/                            # Hilt Dependency Injection Modules
│           │   │   ├── AppModule.kt               # DataStore, TokenManager, app singletons
│           │   │   ├── DatabaseModule.kt          # Room database & DAOs
│           │   │   ├── NetworkModule.kt           # OkHttpClient, Retrofit, Moshi
│           │   │   └── RepositoryModule.kt        # Repository interface bindings
│           │   │
│           │   ├── domain/                        # Domain Layer
│           │   │   ├── model/                     # Domain models (MealPlan, Dish, ShoppingItem, etc.)
│           │   │   ├── repository/                # Repository interfaces
│           │   │   └── usecase/                   # Business logic use cases (7 total)
│           │   │
│           │   ├── presentation/                  # Presentation Layer
│           │   │   └── viewmodel/                 # 10+ ViewModels (Home, Auth, Dish, Planner, etc.)
│           │   │
│           │   ├── ui/                            # Compose UI Screens
│           │   │   ├── auth/                      # SignIn, SignUp, ForgotPassword, GoogleSignInHelper
│           │   │   ├── calendar/                  # Monthly calendar planner
│           │   │   ├── components/                # NetworkObserver, NoInternetBottomSheet
│           │   │   ├── dish/                      # Dish repository management
│           │   │   ├── home/                      # Dashboard, DailyMealPlan, Settings, Account, Preferences
│           │   │   ├── navigation/                # Type-safe Routes (NavKey)
│           │   │   ├── onboarding/                # 12-step onboarding questionnaire
│           │   │   ├── paywall/                   # Premium subscription screen
│           │   │   ├── planner/                   # Weekly meal planner
│           │   │   ├── splash/                    # Animated splash carousel
│           │   │   ├── theme/                     # Color, Theme, Typography definitions
│           │   │   └── MainScaffold.kt            # Navigation host & bottom nav scaffold
│           │   │
│           │   ├── utils/                         # PromptBuilder (AI prompts), Constants
│           │   ├── MainActivity.kt                # Single-activity entry point (@AndroidEntryPoint)
│           │   └── MealPlannerApplication.kt      # Hilt application class (@HiltAndroidApp)
│           │
│           ├── res/                               # Android resources (drawables, strings, themes)
│           └── AndroidManifest.xml
│
├── build.gradle.kts                               # Project-level build script
├── settings.gradle.kts                            # Project settings
├── gradle/                                        # Gradle wrapper & version catalog
└── gradlew                                        # Gradle wrapper script
```

---

## 🔒 Form Validation Logic

All fields on Login, Sign Up, and Password forms have strict validations:

- **Email:** Asserts non-empty state and standard email regex matching via `Patterns.EMAIL_ADDRESS`.
- **Password Policy:** Real-time checklist validation — at least 8 characters, uppercase, lowercase, digit, and special character — with green check / grey cross indicators.
- **Password Confirmation:** Match check between Password and Confirm Password fields.
- **Required Fields:** Visual validation borders and error texts are shown on submit if left blank.

---

## 📡 API Layer & AI Integration

- **Retrofit** client configured with **OkHttp** and `HttpLoggingInterceptor` for debug-level request/response logging.
- **Moshi** handles JSON serialization with Kotlin code generation (`moshi-kotlin-codegen`).
- **AI Prompt Builder** (`PromptBuilder.kt`) constructs structured prompts for AI meal plan generation — incorporating user preferences (dietary restrictions, allergies, household size, cooking skill, calorie targets, cuisine preferences) to generate contextual, personalized meal suggestions.

---

## 🗄️ Database Schema

| Table            | Key Columns                                                                 |
|------------------|-----------------------------------------------------------------------------|
| `meal_plans`     | date (PK), breakfastDishId, lunchDishId, dinnerDishId                       |
| `dishes`         | id (PK), name, category, memo, url, photoUri                                |
| `shopping_items` | id (PK), name, isChecked, order                                             |

All queries return **Kotlin Flow** for reactive, real-time UI updates. Custom `Converters` handle complex type serialization (lists of strings, `LocalDate`).

---

## 🧭 Navigation Flow

```
Splash (4-page carousel)
  ├── [Get Started] ──→ OnboardingQuestionnaire (12 steps) ──→ SignUp
  └── [Sign In] ────────→ SignIn
                            ├── [Forgot Password] ──→ ForgotPassword ──→ SignIn
                            ├── [Sign Up] ──────────→ SignUp
                            └── [Success] ──────────→ Paywall ──→ Main Dashboard

SignUp
  ├── [Sign In] ────────→ SignIn
  └── [Success/Verify] ─→ Paywall ──→ Main Dashboard

Main Dashboard (MainAppContainerScreen)
  ├── Home (Generate Plan, Quick Tools, Food Scanner)
  ├── Saved (Saved Meal Plans)
  ├── 📸 Scanner FAB (AI Food Scanner)
  ├── Track (Nutrition Tracking, Day Selector)
  ├── Analytics (Weight Chart, Macro Averages)
  ├── Settings → Account / Preferences / Premium
  └── [Log Out] ──→ Splash
```

---

## 📄 License

This project is for educational and portfolio purposes.