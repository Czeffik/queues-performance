package io.github.czeffik.queues.performance.infrastructure.metrics;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class MessageMetricsAspect {

  private final MessageMetrics messageMetrics;

  @Pointcut("execution(* io.github.czeffik.queues.performance.domain.Producer.produceTo(..))")
  public void producerMethod() {}

  @Pointcut("execution(* io.github.czeffik.queues.performance.domain.Consumer.consumeFrom(..))")
  public void consumerMethod() {}

  @AfterReturning("producerMethod()")
  public void afterProduced() {
    messageMetrics.incrementProduced();
  }

  @AfterReturning("consumerMethod()")
  public void afterConsumed() {
    messageMetrics.incrementConsumed();
  }
}
