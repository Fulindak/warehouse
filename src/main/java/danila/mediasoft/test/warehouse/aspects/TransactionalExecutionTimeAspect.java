package danila.mediasoft.test.warehouse.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class TransactionalExecutionTimeAspect {
    private long startTime;

    @Before("@annotation(jakarta.transaction.Transactional)")
    public void startTime(JoinPoint point) {
        startTime = System.currentTimeMillis();
        log.info("ClassName: " + point.getSignature().getDeclaringTypeName()
                + " MethodName: " +  point.getSignature().getName()
                + " Start: " + (new Date(startTime)));
    }

    @After("@annotation(jakarta.transaction.Transactional)")
    public void logExecutionTime(JoinPoint point) {
        long endTime = System.currentTimeMillis();
        log.info("ClassName: " + point.getSignature().getDeclaringTypeName()
                + " MethodName: " +  point.getSignature().getName()
                + " WorkTime: " + (endTime - startTime) + "ms");
    }
}
