package com.amigoscode;

import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class SharedPostgresContainer extends PostgreSQLContainer<SharedPostgresContainer> {
    private static final DockerImageName IMAGE_NAME
            = DockerImageName.parse("postgres:17-alpine");

    private static volatile SharedPostgresContainer sharedPostgresContainer;

    public SharedPostgresContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.withReuse(true)
                .withUsername("amigoscode")
                .withDatabaseName("amigos")
                .withLabel("name", "amigscode")
                .withPassword("password");
    }

    public static SharedPostgresContainer getInstance() {
        if(sharedPostgresContainer == null) {
            synchronized (SharedPostgresContainer.class) {
                sharedPostgresContainer = new SharedPostgresContainer(
                        IMAGE_NAME
                );
                sharedPostgresContainer.start();
                Flyway flyway = Flyway.configure()
                        .dataSource(
                                sharedPostgresContainer.getJdbcUrl(),
                                sharedPostgresContainer.getUsername(),
                                sharedPostgresContainer.getPassword()
                        )
                        .load();
                flyway.migrate();
                System.out.println("flyway applied migrations");
            }
        }
        return sharedPostgresContainer;
    }
}
