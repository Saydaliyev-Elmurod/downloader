rootProject.name = "microservice-webflux"

include(
        "api-gateway",
        "config-service",
        "service-registry",
        "services:download",
        "services:bot",
        "services:jms",
        "services:common",
)
