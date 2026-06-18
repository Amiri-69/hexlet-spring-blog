plugins {
    java
    jacoco
    id("org.sonarqube") version "6.2.0.5505"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

group = "io.hexlet"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
    implementation("net.datafaker:datafaker:2.0.2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    runtimeOnly("com.h2database:h2")


    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    annotationProcessor(
        "org.mapstruct:mapstruct-processor:1.5.5.Final"
    )

    testAnnotationProcessor(
        "org.mapstruct:mapstruct-processor:1.5.5.Final"
    )
}

tasks.test {
    useJUnitPlatform()
}

sonarqube {
    properties {
        property("sonar.projectKey", "Amiri-69_hexlet-spring-blog")
        property("sonar.organization", "amiri-69")
        property("sonar.host.url", "https://sonarcloud.io")

        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}