package io.github.czeffik.queues.performance.interfaces;

import io.github.czeffik.queues.performance.interfaces.rest.RestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RestConfiguration.class})
@Configuration
public class InterfacesConfiguration {}
