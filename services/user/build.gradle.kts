plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
    }
}

dependencies {
    implementation(project(":services:jms"))
    implementation(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-bus-amqp")
//mapstruct
//    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.6")

    implementation("org.liquibase:liquibase-core:4.28.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    // jwt)
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    implementation("io.jsonwebtoken:jjwt:0.12.5")
//security
    implementation("org.springframework.boot:spring-boot-starter-security")
// redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.8.0")
//    implementation("org.springframework.boot:spring-boot-starter-amqp")
//    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'
    implementation ("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
    implementation ("org.postgresql:postgresql")
//    runtimeOnly 'io.projectreactor.tools:blockhound:1.0.9.RELEASE'
    developmentOnly ("org.springframework.boot:spring-boot-devtools")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("io.projectreactor:reactor-test")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
}

