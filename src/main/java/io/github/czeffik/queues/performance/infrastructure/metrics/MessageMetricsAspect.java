package io.github.czeffik.queues.performance.infrastructure.metrics;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class MessageMetricsAspect {

  private final MessageMetrics messageMetrics;

  @Pointcut("execution(* io.github.czeffik.queues.performance.domain.MessageQueue.offer(..))")
  public void producerMethod() {}

  @Pointcut("execution(* io.github.czeffik.queues.performance.domain.MessageQueue.poll(..))")
  public void consumerMethod() {}

  @AfterReturning("producerMethod()")
  public void afterProduced() {
    messageMetrics.incrementProduced();
  }

  @AfterReturning(pointcut = "consumerMethod()", returning = "producedTime")
  public void afterConsumed(long producedTime) {
    messageMetrics.incrementConsumed();
    messageMetrics.recordLatency(producedTime);
  }
}
