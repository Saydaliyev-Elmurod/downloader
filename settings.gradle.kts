rootProject.name = "microservice-webflux"

include(
        "api-gateway",
        "services:user",
        "services:notification",
        "services:jms",
        "services:common",
)
