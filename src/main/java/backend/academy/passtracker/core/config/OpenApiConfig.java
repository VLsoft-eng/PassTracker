package backend.academy.passtracker.core.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Autowired
    @Qualifier("unprotectedEndpoints")
    private List<String> unprotectedEndpoints;

    /**
     * Создает OpenAPI с пустым URL сервера.
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openApiWithEmptyServer() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().addServersItem(new Server().url(""))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    /**
     * Настраивает аутентификацию для ендпоинтов.
     *
     * @return OpenAPI
     */
    @Bean
    public OpenApiCustomizer addSecurityToProtectedEndpoints() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            final String securitySchemeName = "bearerAuth";

            for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
                String path = entry.getKey();
                PathItem pathItem = entry.getValue();

                if (isEndpointProtected(path)) {
                    applySecurityToOperations(pathItem, securitySchemeName);
                }
            }
        };
    }

    private boolean isEndpointProtected(String path) {
        for (String endpoint: unprotectedEndpoints) {
            if (path.startsWith(endpoint)) {
                return false;
            }
        }
        return true;
    }

    private void applySecurityToOperations(PathItem pathItem, String securitySchemeName) {
        for (Operation operation : pathItem.readOperations()) {
            operation.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
        }
    }
}
