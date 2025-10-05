package io.github.czeffik.queues.performance.infrastructure.metrics;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.infrastructure.ApplicationProperties;
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
  public MessageMetrics messageMetrics(
      MeterRegistry meterRegistry,
      MessageQueue messageQueue,
      ApplicationProperties applicationProperties) {
    return new MessageMetrics(meterRegistry, messageQueue, applicationProperties.toTags());
  }
}
