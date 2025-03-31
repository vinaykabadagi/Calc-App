# Calculator App

A modern, feature-rich calculator application for Android.

## Features

- Basic arithmetic operations (addition, subtraction, multiplication, division)
- Memory functions (MC, MR, M+, M-)
- Calculation history with ability to reuse previous results
- Haptic feedback for button presses
- Error handling for division by zero and other calculation errors
- Automatic text size adjustment for large numbers
- Dark theme UI

## Architecture

The app follows the Model-View-Controller (MVC) pattern:
- **Model**: Calculator and CalculatorHistory classes handle the calculation logic and history storage
- **View**: XML layouts define the UI
- **Controller**: MainActivity and HistoryActivity handle user interactions

## Requirements

- Android 8.1 (API level 27) or higher
- Android Studio 4.0 or higher

## Installation

1. Clone this repository
2. Open the project in Android Studio
3. Build and run the app on your device or emulator

