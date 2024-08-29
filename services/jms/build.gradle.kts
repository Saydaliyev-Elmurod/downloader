
apply(plugin = "org.springframework.boot")
dependencies {
    implementation(project(":services:common"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.6")

}