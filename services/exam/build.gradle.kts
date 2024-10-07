apply(plugin = "org.springframework.boot")

dependencies {
    api(project(":services:jms"))
    api(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

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

    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${rootProject.extra.get("webflux-ui")}")

    // https://mvnrepository.com/artifact/io.projectreactor.kafka/reactor-kafka
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.23")

    //postgres
    implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}

