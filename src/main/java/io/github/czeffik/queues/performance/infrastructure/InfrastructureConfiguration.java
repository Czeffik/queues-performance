package io.github.czeffik.queues.performance.infrastructure;

import io.github.czeffik.queues.performance.infrastructure.metrics.MetricsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({MetricsConfiguration.class, ApplicationProperties.class})
@Configuration
public class InfrastructureConfiguration {}
