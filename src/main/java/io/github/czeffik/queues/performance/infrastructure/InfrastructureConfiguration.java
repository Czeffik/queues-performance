package io.github.czeffik.queues.performance.infrastructure;

import io.github.czeffik.queues.performance.infrastructure.metrics.MetricsConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({MetricsConfiguration.class, ApplicationProperties.class})
@Configuration
public class InfrastructureConfiguration {

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
      ApplicationProperties applicationProperties) {
    return registry -> registry.config().commonTags(applicationProperties.toTags());
  }
}
