plugins {
    kotlin("jvm")
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":hunmin-common"))
    implementation(project(":hunmin-domain"))
    implementation(project(":hunmin-persistence"))
    testImplementation(testFixtures(project(":hunmin-common")))
    testImplementation(testFixtures(project(":hunmin-domain")))
    testImplementation(testFixtures(project(":hunmin-persistence")))

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
