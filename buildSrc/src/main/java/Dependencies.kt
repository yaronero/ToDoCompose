object Dependencies {

    object Compose {
        const val version = "1.4.2"

        const val ui = "androidx.compose.ui:ui:$version"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val material = "androidx.compose.material:material:$version"
        const val uiTestJUnit = "androidx.compose.ui:ui-test-junit4:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val uiToolingManifest = "androidx.compose.ui:ui-test-manifest:$version"
        const val navigation = "androidx.navigation:navigation-compose:2.5.3$version"
    }

    object Navigation {
        const val version = "2.5.3"

        const val navigationCompose = "androidx.navigation:navigation-compose:$version"
    }

    object Room {
        private const val version = "2.5.1"

        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val ktx = "androidx.room:room-ktx:$version"
    }

    object DataStorePreferences {
        private const val version = "1.0.0"

        const val dataStore = "androidx.datastore:datastore-preferences:$version"
    }

    object Hilt {
        private const val version = "2.44.2"

        const val android = "com.google.dagger:hilt-android:$version"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Test {
        const val jUnit = "junit:junit:4.13.2"
        const val androidJUnit = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.8.10"
        const val activityCompose = "androidx.activity:activity-compose:1.7.1"
    }

    object Lifecycle {
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    }
}