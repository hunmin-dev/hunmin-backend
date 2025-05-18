plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "hunmin-backend"
include("hunmin-bootstrap")
include("hunmin-domain")
include("hunmin-common")
include("hunmin-application")
include("hunmin-batch")
include("hunmin-consumer")
include("hunmin-infrastructure")
include("hunmin-infrastructure:adapter")
include("hunmin-infrastructure:persistence")
