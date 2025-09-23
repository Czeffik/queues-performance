package io.github.czeffik.queues.performance.interfaces.rest;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Import({SwaggerAutoConfiguration.class})
@ImportAutoConfiguration({
  DispatcherServletAutoConfiguration.class,
  ServletWebServerFactoryAutoConfiguration.class,
  EmbeddedWebServerFactoryCustomizerAutoConfiguration.class
})
@Configuration
@EnableWebMvc
public class RestConfiguration {}
