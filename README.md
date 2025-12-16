<div align="center">

  <img src="app/src/main/res/drawable/ic_guy_end.png" alt="Student Run Logo" width="280">
  <br/>

  # Student Run 🏃‍♂️🎓

  > An educational arcade game combining logic, reflexes, and dynamic UI management.

  <p>
    <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white" alt="Android" />
    <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=flat" alt="MVC" />
    <img src="https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?style=flat&logo=android-studio&logoColor=white" alt="Android Studio" />
  </p>
</div>

---

## 📌 About The Project
**Student Run** is a lane-based survival game developed to demonstrate **clean code principles** in Android. The player must navigate obstacles on a 7x3 grid, managing lives and reacting to increasing speeds.

---

## 🎥 Demo

<div align="center">
  <video src="https://github.com/user-attachments/assets/2a3cd701-9aed-4b41-8fec-a0f301e34e40" width="400" />
</div>

---

## ✨ Key Features
* **Dynamic Grid Engine:** A custom logic engine managing a 7x3 matrix.
* **MVC Principles:** Logic (`GameManager`) is decoupled from the UI (`GameActivity`) for better maintainability.
* **Haptic Feedback:** Vibrator service integration for collisions.
* **Custom Game Loop:** Uses `Handler` and `Runnable` for precise timing (500ms tick).

---

## 🛠️ Technical Implementation

### 1. The Logic (Model & Controller)
Instead of relying on unity-like engines, the game logic is pure Kotlin:
* **`GameManager`**: The brain. Handles spawning, movement, and collision checks.
* **`Player`**: Simple data class tracking lives and lane position.
* **`Constants`**: Global configuration (Grid size, Game speed).

### 2. The User Interface (View)
The app flows through three main stages:
* **Entry (`HomeActivity`)**: Initialization and ViewBinding setup.
* **Gameplay (`GameActivity`)**: 
    * Updates the UI based on `GameManager` state.
    * Manages the Life-Cycle (`onPause`/`onResume`).
* **Summary (`ResultActivity`)**: Handles high scores and navigation back to the start.

---

## ⚙️ How to Run
1.  Clone this repo.
2.  Open in **Android Studio**.
3.  Sync Gradle and hit **Run**.

---
**Created by Ofek Fanian**
