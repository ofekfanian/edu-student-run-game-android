# Student Run 🏃‍♂️🎓

> An educational arcade game combining logic, reflexes, and dynamic UI management.

![Android](https://img.shields.io/badge/Platform-Android-green)
![Language](https://img.shields.io/badge/Language-Kotlin-purple)
![Tools](https://img.shields.io/badge/Tools-Android_Studio-blue)

## 📌 About The Project
**Student Run** is a lane-based survival game developed to demonstrate **clean architecture** and **logic separation** in Android. The player must navigate obstacles on a 7x3 grid, managing lives and reacting to increasing speeds.

---

## 🎥 Demo

https://github.com/user-attachments/assets/78cf0727-a4d5-4ee7-82cf-a5a911ae75f9

---

## ✨ Key Features
* **Dynamic Grid Engine:** A custom logic engine managing a 7x3 matrix.
* **MVC Principles:** Separates the Game Logic (`GameManager`) from the UI (`GameActivity`) for better code maintainability.
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
