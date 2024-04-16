package danila.mediasoft.test.warehouse.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAspect {

    @Around("@annotation(danila.mediasoft.test.warehouse.aspects.LogExecutTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("ClassName: " + point.getSignature().getDeclaringTypeName()
                + " MethodName: " +  point.getSignature().getName()
        + " WorkTime: " + (endTime-startTime) +"ms");
        return object;
    }
}
