# Telia Exam Daniell Algar

## Project details

Main key take aways regarding the architecture of this project

1. In short it's an MVVM, LiveData, Coroutine, Room, Retrofit, DataBinding, Navigation, Koin project.
1. The project is build with Android Studio 4.0 Beta 5. I'm not sure if you can run it with an AS version less than 4.0. Easiest is to just run it with AS 4.0 Beta 5.
1. It's a multi-module project (mainly) broken down into a `data` and a `features` module. The other modules primarily have a supporting role. The `app` module is kept lightweight to mainly act as a starting point for the app.
1. It uses DataBinding so you might need to build the app twice to get all generated classes.
1. It exposes data from network/database through a `NetworkBoundResource.kt` abstraction.
1. It wraps network calls in a `retry`-block with exponential back off.