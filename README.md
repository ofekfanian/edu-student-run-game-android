# Student Run 🏃‍♂️🎓

> An educational arcade game combining logic, reflexes, and dynamic UI management.

![Android](https://img.shields.io/badge/Platform-Android-green)
![Language](https://img.shields.io/badge/Language-Kotlin-purple)
![Tools](https://img.shields.io/badge/Tools-Android_Studio-blue)

## 📌 About The Project
**Student Run** is a lane-based survival game developed to demonstrate **MVC architecture** in Android. The player must navigate obstacles on a 7x3 grid, managing lives and reacting to increasing speeds.

---

## 🎥 Demo

https://github.com/user-attachments/assets/b8b2dd61-9feb-4bd4-910c-45d0206d2002



https://github.com/user-attachments/assets/2a3cd701-9aed-4b41-8fec-a0f301e34e40

---

## ✨ Key Features
* **Dynamic Grid Engine:** A custom logic engine managing a 7x3 matrix.
* **MVC Pattern:** Strict separation between Game Logic, UI, and Data.
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

**Created by Ofek Fanian**
