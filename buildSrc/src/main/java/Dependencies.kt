object ApplicationId {
    const val id = "com.algar.telia_exam"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val model = ":data:model"
}

object Versions {
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
    const val kotlin = "1.3.72"
    const val gradle = "4.0.0-beta05"
    const val appCompat = "1.1.0"
    const val coreKtx = "1.2.0"
    const val androidJunit = "1.1.1"
    const val constraintLayout = "1.1.3"
    const val espressoCore = "3.2.0"
    const val buildToolsVersion = "29.0.3"
    const val androidTestRunner = "1.3.0-alpha05"
    const val archCoreTest = "2.1.0"
    const val koin = "2.1.5"
    const val navigation = "2.3.0-alpha04"
    const val coroutines = "1.3.5"
    const val retrofit = "2.8.1"
    const val okHttp = "4.4.0"
    const val gson = "2.8.6"
    const val mockWebServer = "4.4.0"
    const val lifecycle = "2.3.0-alpha01"
    const val recyclerview = "1.2.0-alpha01"
    const val mockk = "1.9.3"
    const val fragmentTest = "1.2.3"
    const val databinding = "4.1.0-alpha04"
    const val glide = "4.11.0"
    const val testRules = "1.3.0-alpha05"
}

object Libraries {
    // Koin
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object AndroidLibraries {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"


    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object TestLibraries {
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val fragmentNav = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"

    // Koin
    const val koin = "org.koin:koin-test:${Versions.koin}"

    // Mock
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    // Coroutine
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // Data binding
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.databinding}"
}