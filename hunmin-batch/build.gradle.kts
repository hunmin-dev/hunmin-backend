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
    implementation(project(":hunmin-infrastructure:adapter"))
    testImplementation(testFixtures(project(":hunmin-common")))
    testImplementation(testFixtures(project(":hunmin-domain")))
    testImplementation(testFixtures(project(":hunmin-application")))
    testImplementation(testFixtures(project(":hunmin-infrastructure:persistence")))
    testImplementation(testFixtures(project(":hunmin-infrastructure:adapter")))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}
