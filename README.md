# Android Application

This project is an Android application developed as part of the **Android Mobile Development** course at LaSalle College, Fall 2024. The application demonstrates core Android development principles, including user registration, interactive activities, and database integration.

## Features

1. **Splash Screen**  
   - Displays a welcome screen with an image or text before navigating to the main application.

2. **User Registration**  
   - Allows users to register with their username, name, and password.
   - Data is stored in a real-time database.

3. **Main Page**  
   - Includes two buttons:
     - **Game**: Leads to a color-matching game.
     - **Score**: Displays user scores.

4. **Game: Color Matching**  
   - Users match displayed color names with the correct color buttons within a time limit.
   - Score increases with correct matches.
   - After 5 rounds, the game ends, and the score is saved.

5. **Score Page**  
   - Displays all scores stored in the database.
   - Features include:
     - **Sign Out**: Logs out and returns to the registration screen.
     - **Delete Account**: Deletes user data and navigates back to the registration screen.

## Technical Details

- **Language**: Java/Kotlin
- **Framework**: Android SDK
- **Database**: Firebase Realtime Database
- **UI Design**: XML layouts with responsive design principles
- **Development Tools**: Android Studio
