package io.github.czeffik.queues.performance.infrastructure;

import io.github.czeffik.queues.performance.domain.Producer;
import io.github.czeffik.queues.performance.infrastructure.producer.ConstantProducer;
import io.github.czeffik.queues.performance.infrastructure.producer.ProducerProperties;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

public class ProducersPostProcessor implements BeanDefinitionRegistryPostProcessor {
  private final ProducerProperties producerProperties;

  public ProducersPostProcessor(Environment environment) {
    var binder = Binder.get(environment);
    var properties = binder.bind("application.producers", Bindable.of(HashMap.class)).get();
    producerProperties =
        new ProducerProperties(
            Producer.Type.valueOf((String) properties.get("type")),
            (Integer) properties.get("number"));
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    var producers = new ArrayList<Producer>();
    for (int i = 0; i < producerProperties.getNumber(); i++) {
      var producer = producerProperties.create();
      producers.add(producer);
      var name = "producer" + i;
      var builder = BeanDefinitionBuilder.genericBeanDefinition(ConstantProducer.class);
      registry.registerBeanDefinition(name, builder.getBeanDefinition());
    }
  }
}
