pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mp2602"
include(":app")
include(":mp0301")
include(":mp0302")
include(":mp0303")
include(":mp0304")
include(":mp0305")
include(":mp0306")
include(":mp0401")
include(":mp0402")
include(":mp0403")
include(":mp0404")
include(":mp0501")
include(":mp0502")
include(":mp0503")
include(":mp0504")
include(":mp0505")
include(":mp0601")
include(":mp0602")
include(":mp0603")
include(":mp0604")
include(":mp0605")
