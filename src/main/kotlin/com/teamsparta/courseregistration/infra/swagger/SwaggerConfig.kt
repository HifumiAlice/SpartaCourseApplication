package com.teamsparta.courseregistration.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI() : OpenAPI = OpenAPI()
        .addSecurityItem(
            SecurityRequirement().addList("Bearer Authentication")
        )
        .components(
            Components().addSecuritySchemes(
                "Bearer Authentication",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT")
                    .`in`(SecurityScheme.In.HEADER)
                    .name("Authorization")
            )
        )
        .info(
            Info()
//                .title("Course API")
//                .description("Course API schema")
//                .version("1.0.0")
                .title("내가 만든 제목")
                .description("설명 따윈 없다")
                .version("1.0.0")
        )

//    @Bean
//    fun openAPI() : OpenAPI {
//        val openAPI = OpenAPI()
//        openAPI.components(Components())
////        openAPI.info(
////                Info()
////                .title("Swagger API")
////                .description("몰라")
////                .version("1.0.0")
////        )
//
//        val info : Info = Info()
//        info.title = "내가 작명한다."
//        info.description = "${openAPI.components}"
//        info.version = "1.1.0"
//
//        return openAPI
//    }

}