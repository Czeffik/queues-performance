package io.github.czeffik.queues.performance.interfaces.rest;

import org.springdoc.core.configuration.*;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.configuration.MultipleOpenApiSupportConfiguration;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
  SpringDocConfiguration.class,
  SpringDocConfigProperties.class,
  SpringDocJavadocConfiguration.class,
  SpringDocGroovyConfiguration.class,
  SpringDocSecurityConfiguration.class,
  SpringDocFunctionCatalogConfiguration.class,
  SpringDocHateoasConfiguration.class,
  SpringDocPageableConfiguration.class,
  SpringDocSortConfiguration.class,
  SpringDocDataRestConfiguration.class,
  SpringDocKotlinConfiguration.class,
  SpringDocKotlinxConfiguration.class,
  SpringDocWebMvcConfiguration.class,
  SpringDocSpecPropertiesConfiguration.class,
  SpringDocUIConfiguration.class,
  MultipleOpenApiSupportConfiguration.class,
  SwaggerConfig.class,
  SwaggerUiConfigProperties.class,
  SwaggerUiConfigParameters.class,
  SwaggerUiOAuthProperties.class
})
@Configuration
public class SwaggerAutoConfiguration {}
