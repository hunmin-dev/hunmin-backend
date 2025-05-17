import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation(project(":hunmin-common"))
    implementation(project(":hunmin-domain"))
    implementation(project(":hunmin-application"))
    implementation(project(":hunmin-infrastructure:persistence"))
    testImplementation(testFixtures(project(":hunmin-common")))
    testImplementation(testFixtures(project(":hunmin-domain")))
    testImplementation(testFixtures(project(":hunmin-application")))
    testImplementation(testFixtures(project(":hunmin-infrastructure:persistence")))

    // security-crypto
    api("org.springframework.security:spring-security-crypto")

    // webflux
    api("org.springframework.boot:spring-boot-starter-webflux:3.3.3")
    api("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
}
