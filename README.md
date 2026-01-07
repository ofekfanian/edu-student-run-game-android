<div align="center">

<img src="https://github.com/user-attachments/assets/1298150c-5e5e-4359-b68b-4c5dd4211d85" alt="ic_guy" height="250" />

# Student Run 🏃‍♂️🎓

### *Step into the shoes of a student and race through a world of challenges!*

> A high-performance Android arcade game featuring tilt controls, dynamic grid logic, and location-based leaderboards.

<p>
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=flat" alt="MVC" />
  <img src="https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?style=flat&logo=android-studio&logoColor=white" alt="Android Studio" />
</p>

</div>

---

## 📌 About The Project
**Student Run** is a fast-paced survival game where players navigate a student through obstacles on an expanded grid. This project demonstrates advanced Android development concepts, including real-time sensor integration, hardware feedback, and geographic data management.

---

## 📸 Screenshots

<div align="center">
  <table style="width:100%">
    <tr>
      <td align="center"><b>Home Screen</b><br><img src="https://github.com/user-attachments/assets/9fe5775c-1fef-4b5e-9563-4726af8d8e39" width="250" /></td>
      <td align="center"><b>Location Permission</b><br><img src="https://github.com/user-attachments/assets/84597981-5485-460f-9863-7192e8782ca5" width="250" /></td>
      <td align="center"><b>Gameplay (Arrows)</b><br><img src="https://github.com/user-attachments/assets/06fb0b56-1760-4e05-8f63-fbfb279fd271" width="250" /></td>
    </tr>
    <tr>
      <td align="center"><b>Gameplay (Sensors)</b><br><img src="https://github.com/user-attachments/assets/3a6d5763-8a19-46e7-a7db-16c85d4ea312" width="250" /></td>
      <td align="center"><b>Game Over Screen</b><br><img src="https://github.com/user-attachments/assets/1df7f4ea-cbd9-4fb0-8ea1-5efd8862f805" width="250" /></td>
      <td align="center"><b>Leaderboard (Maps)</b><br><img src="https://github.com/user-attachments/assets/dea03f27-806f-4892-b8e5-947a40ab397e" width="250" /></td>
    </tr>
  </table>
</div>

---

## ✨ Key Features
* **Expanded Grid Engine:** A custom logic engine managing a 7x5 matrix (7 rows, 5 lanes).
* **Dual Control Schemes:** Switch between UI buttons or accelerometer-based (Tilt) navigation.
* **Location-Aware Scores:** Captures GPS coordinates during high scores and displays them on an interactive map.
* **Enhanced Health System:** Visual heart management where lives are depleted from left to right for better tracking.
* **Immersive Feedback:** Integrated SFX for collisions and life refills, background music, and haptic (vibration) feedback.
* **Speed Difficulty:** Multiple speed modes including a "Fast Mode" and an "Endless" survival mode.

---

## 🛠️ Technical Implementation

### 1. Game Logic & Concurrency
* **`GameManager`**: The core controller handling obstacle spawning, bonus items, and collision physics.
* **`GameTicker`**: Manages the game loop using `Handler` and `Runnable` for consistent tick timing.
* **`TiltDetector`**: Interprets raw accelerometer data into smooth lane transitions.

### 2. UI & Data Persistence
* **ViewBinding**: Clean and type-safe access to UI components across all activities.
* **Google Play Services**: Utilizes `FusedLocationProviderClient` for precise player positioning.
* **Shared Preferences**: Persists Top 10 records using JSON serialization for offline access.

---

## ⚙️ How to Run
1. Clone this repository.
2. Open the project in **Android Studio**.
3. Sync Gradle and ensure all dependencies are downloaded.
4. Hit **Run** to launch on an emulator or physical device.

---
<div align="center">
    <b>Created by Ofek Fanian</b>
</div>
