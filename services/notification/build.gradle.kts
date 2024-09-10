apply(plugin = "org.springframework.boot")
dependencies {
    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.6")
    implementation("org.springframework.boot:spring-boot-starter-mail:${rootProject.extra.get("mail")}")
    api(project(":services:jms"))
    api(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.liquibase:liquibase-core")
    // MapStruct
    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapStructVersion")}")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${rootProject.extra.get("webflux-ui")}")

    //postgres
    implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}