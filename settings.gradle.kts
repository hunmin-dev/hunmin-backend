plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "hunmin-backend"
include("hunmin-api")
include("hunmin-domain")
include("hunmin-persistence")
include("hunmin-common")
include("hunmin-application")
include("hunmin-adapter")
include("hunmin-batch")
