plugins {
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    java
}

allprojects {
    apply(plugin = "io.spring.dependency-management")

    group = "org.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    dependencyManagement {
        imports {
            // https://github.com/spring-projects/spring-boot/releases
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.1.0")

            // https://spring.io/projects/spring-cloud
            // https://github.com/spring-cloud/spring-cloud-release/tags
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")

            // To avoid specifying the version of each dependency, use a BOM or Bill Of Materials.
            // https://github.com/testcontainers/testcontainers-java/releases
            mavenBom("org.testcontainers:testcontainers-bom:1.18.3")

            //https://immutables.github.io/
            mavenBom("org.immutables:bom:2.9.2")
        }

        dependencies {
            // https://github.com/apache/logging-log4j2/tags
            dependencySet("org.apache.logging.log4j:2.20.0") {
                entry("log4j-core")
                entry("log4j-api")
                entry("log4j-web")
            }
        }
    }
}

subprojects {
    apply(plugin = "java")


    dependencies{
        annotationProcessor("org.immutables:value")
        compileOnly("org.immutables:builder")
        compileOnly("org.immutables:value-annotations")
    }
    configurations {
        all {
            exclude("org.springframework.boot", "spring-boot-starter-logging")

            // Can't exclude because of this: https://github.com/testcontainers/testcontainers-java/issues/970
            // exclude("junit", "junit")
        }
    }
}
