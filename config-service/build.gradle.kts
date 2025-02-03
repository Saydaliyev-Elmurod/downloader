
apply(plugin = "org.springframework.boot")
extra["springCloudVersion"] = "2024.0.0"

dependencies {
	implementation("org.springframework.cloud:spring-cloud-config-server")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

