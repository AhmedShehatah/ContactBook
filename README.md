# Contact Book
An android app stores contacts in an online database.


## Architecture pattern Used
[MVVM](https://developer.android.com/jetpack/guide)
![alt text][logo]

[logo]: https://developer.android.com/topic/libraries/architecture/images/final-architecture.png "MVVM"

## Libraries & Tools Used
- Architecture - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
- Data Binding - Declaratively bind observable data to UI elements.
- Lifecycles - Create a UI that automatically responds to lifecycle events.
- LiveData - Build data objects that notify views when the underlying database changes.
- Navigation - Handle everything needed for in-app navigation.
- ViewModel - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
- Repository - A Module that handle data operations, You can consider repositories to be mediators between different data sources.
- Third party and miscellaneous libraries.
- 
 ...Retrofit - A simple library that is used for network transaction.
 
 ...Glide For image loading.
- Kotlin Coroutines For managing background threads with simplified code and reducing needs for callbacks.

