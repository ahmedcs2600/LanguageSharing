# Language Sharing Application
A simple Language Sharing Application.

[![N|Solid](https://i.postimg.cc/3xnsPncL/Screenshot-1646168927.png)]

## Features
* Fetch Community Members through pagination
* Like Community Member

## Architecture
* Built with Modern Android Development practices
* Multi Module Application demonstrates SOLID principles and Clean Architecture
* Utilized Remote, Cache, Data, domain and presentation Layer
* Includes unit tests of each module

## 📱 Demo App
Download the [APK file from here](https://github.com/ahmedcs2600/LanguageSharing/blob/main/app/build/intermediates/apk/debug/app-debug.apk) on your Android phone

## Tech Stack
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
    - - [RoomDatabase](https://developer.android.com/training/data-storage/room) - For caching network data and storing Like status
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
    - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - For loading Data in more robust way
- [MockK](https://mockk.io) - For Mocking and Unit Testing
- [Espresso](https://developer.android.com/training/testing/espresso) -For UI Testing