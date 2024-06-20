plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    java
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("org.springframework.boot:spring-boot-starter-security:3.3.0")

    implementation ("org.springdoc:springdoc-openapi-webflux-ui:1.8.0")

//    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("io.projectreactor:reactor-test")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
}

