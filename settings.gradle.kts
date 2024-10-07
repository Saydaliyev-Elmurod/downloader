rootProject.name = "microservice-webflux"

include(
        "api-gateway",
        "services:user",
        "services:notification",
        "services:exam",
        "services:jms",
        "services:common",
)
