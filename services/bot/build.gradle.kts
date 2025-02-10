apply(plugin = "org.springframework.boot")

dependencies {
    api(project(":services:jms"))
    api(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//    implementation("org.springframework.cloud:spring-cloud-starter-config")

    implementation("org.telegram:telegrambots:6.5.0")
// https://mvnrepository.com/artifact/org.bytedeco/javacv
    implementation("org.bytedeco:javacv:1.5.11")
// https://mvnrepository.com/artifact/org.bytedeco/javacv-platform
    implementation("org.bytedeco:javacv-platform:1.5.11")

    //
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.liquibase:liquibase-core")
    // lombok and mapstruct
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")


    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${rootProject.extra.get("webflux-ui")}")

    //postgres
    implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}

