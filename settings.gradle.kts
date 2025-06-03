pluginManagement {
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
   /* // This is the crucial part that tells Gradle about your libs.toml
    versionCatalogs {
        create("libs") { // The name "libs" must match the 'libs' prefix in your build.gradle.kts
            from(files("../gradle/libs.toml")) // This path MUST be correct relative to settings.gradle.kts
        }
    }*/
}

rootProject.name = "ShopApp"
include(":app")
 