plugins {
    java
}

allprojects {
    group = "org.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    if(this.name != "common") {
        dependencies {
//            testImplementation("org.springframework.boot:spring-boot-starter-test")
//            testImplementation("io.projectreactor:reactor-test")
//            testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        }
    }
}
