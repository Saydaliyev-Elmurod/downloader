apply(plugin = "org.springframework.boot")

dependencies {
//    api(project(":services:jms"))
    api(project(":services:common"))
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    implementation("org.telegram:telegrambots:6.5.0")
// https://mvnrepository.com/artifact/org.bytedeco/javacv
    implementation("org.bytedeco:javacv:1.5.11")
// https://mvnrepository.com/artifact/org.bytedeco/javacv-platform
    implementation("org.bytedeco:javacv-platform:1.5.11")

// https://mvnrepository.com/artifact/org.telegram/telegrambots
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.liquibase:liquibase-core")
    // lombok and mapstruct
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")


    // redis
//    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
//    implementation("org.springdoc:springdoc-openapi-webflux-ui:${rootProject.extra.get("webflux-ui")}")

    //postgres
    runtimeOnly("org.postgresql:postgresql")
//    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}

