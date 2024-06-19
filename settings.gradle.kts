rootProject.name = "microservice-webflux"

include(
        "api-gateway",
        "services:user",
        "services:notification",
        "services:common",
)

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}