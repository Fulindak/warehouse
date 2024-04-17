package danila.mediasoft.test.warehouse.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@Slf4j
public class TransactionalExecutionTimeAspect extends TransactionSynchronizationAdapter {
    private long startTime;

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSyncrhonization() {
        TransactionSynchronizationManager.registerSynchronization(this);

    }
    public void beforeCommit(boolean readOnly) {
        startTime = (System.currentTimeMillis());
    }

    public void afterCommit(){
        log.info("Transaction time work: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
