apply(plugin = "org.springframework.boot")

dependencies {
    api(project(":services:jms"))
    api(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${rootProject.extra.get("openApiVersion")}")

    //
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    implementation("org.liquibase:liquibase-core")
    // lombok and mapstruct
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.kafka:spring-kafka:3.2.4")

    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    //postgres
    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}

