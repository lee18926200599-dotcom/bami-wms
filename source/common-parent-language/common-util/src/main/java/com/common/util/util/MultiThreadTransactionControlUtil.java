package com.common.util.util;

import com.common.util.inteface.BusinessWorkService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

@Component
public class MultiThreadTransactionControlUtil<T> {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadTransactionControlUtil.class);

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Lazy
    @Autowired
    private BusinessWorkService<T> businessWorkService;

    private volatile AtomicBoolean canCommit = null;

    private volatile CountDownLatch count = null;

    public void run(List<T> taskList, Executor executor) {
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        AtomicBoolean needRelease = new AtomicBoolean(true);
        canCommit = new AtomicBoolean(true);
        count = new CountDownLatch(taskList.size());
        taskList.forEach(t -> {
            executor.execute(() -> {
                String threadName = Thread.currentThread().getName();
                DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
                TransactionStatus state = transactionManager.getTransaction(definition);
                try {
                    businessWorkService.handle(t);
                    count.countDown();
                    needRelease.set(false);
                    LockSupport.park();
                    while (Objects.equals(0, count.getCount())) {
                        LockSupport.unpark(Thread.currentThread());
                    }
                    if (canCommit.get()) {
                        transactionManager.commit(state);
                        logger.info("transaction has commit");
                    } else {
                        transactionManager.rollback(state);
                        logger.info("transaction has been rollback by other thread");
                    }
                    System.out.print(threadName);
                } catch (Exception e) {
                    logger.error("business work handle exception case:{}", e.getMessage());
                    canCommit.set(false);
                    transactionManager.rollback(state);
                } finally {
                    if (needRelease.get()) {
                        count.countDown();
                    }
                }
            });
        });
    }

    public void run(List<T> Task) {
        this.run(Task, Executors.newFixedThreadPool(5));
    }

}
