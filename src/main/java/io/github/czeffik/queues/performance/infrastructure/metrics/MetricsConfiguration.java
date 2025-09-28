package io.github.czeffik.queues.performance.infrastructure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

  @Bean
  MessageMetricsAspect messageMetricsAspect(MessageMetrics messageMetrics) {
    return new MessageMetricsAspect(messageMetrics);
  }

  @Bean
  public MessageMetrics messageMetrics(MeterRegistry meterRegistry) {
    return new MessageMetrics(meterRegistry);
  }
}
