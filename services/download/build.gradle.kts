apply(plugin = "org.springframework.boot")

dependencies {
    api(project(":services:jms"))
    api(project(":services:common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

//    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.6")

//    implementation("org.springframework.cloud:spring-cloud-starter-config")
    //
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.liquibase:liquibase-core")
    // lombok and mapstruct
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapStructVersion")}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    implementation("io.jsonwebtoken:jjwt:0.12.5")
    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${rootProject.extra.get("webflux-ui")}")

    //postgres
    implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
    implementation("org.postgresql:postgresql")

    implementation(platform("it.tdlight:tdlight-java-bom:3.3.1+td.1.8.25"))
    implementation ( "it.tdlight",  "tdlight-java") // Java 8 is supported if you use the following dependency classifier: `jdk8`
    implementation ( "it.tdlight",  "tdlight-natives",null,null,"linux_amd64_gnu_ssl1")
//    implementation("it.tdlight:tdlight-java:jdk8")
//    implementation("it.tdlight:tdlight-natives:linux_amd64_gnu_ssl1")
//    compileOnly("it.tdlight:tdlight-natives:linux_amd64_gnu_ssl1")
//    implementation("it.tdlight:tdlight-natives:linux_amd64_clang_ssl3")
//    implementation("it.tdlight:tdlight-natives:linux_amd64_gnu_ssl3")
//    implementation("it.tdlight:tdlight-natives:windows_amd64")
//    implementation("it.tdlight:tdlight-natives:macos_amd64")
//    implementation("it.tdlight:tdlight-natives:macos_arm64")

    // Include other native clas

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation("org.springframework.boot:spring-boot-starter-log4j2")

}

