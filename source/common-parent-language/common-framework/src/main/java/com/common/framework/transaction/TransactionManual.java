package com.common.framework.transaction;

import cn.hutool.core.util.RandomUtil;
import com.common.framework.web.SpringBeanLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.function.Supplier;

public class TransactionManual {
    private static final Logger log = LoggerFactory.getLogger(TransactionManual.class);

    public TransactionManual() {
    }

    public static TransactionStatus newTransaction() {
        DefaultTransactionAttribute attr = new DefaultTransactionAttribute();
        attr.setName("rb-txm-" + RandomUtil.randomNumbers(12));
        return getTxManager().getTransaction(attr);
    }

    public static TransactionStatus currentTransaction() {
        return TransactionAspectSupport.currentTransactionStatus();
    }

    public static void commit(TransactionStatus status) {
        getTxManager().commit(status);
        status.flush();
    }

    public static void rollback(TransactionStatus status) {
        getTxManager().rollback(status);
        status.flush();
    }

    protected static DataSourceTransactionManager getTxManager() {
        return (DataSourceTransactionManager) SpringBeanLoader.getSpringBean(DataSourceTransactionManager.class);
    }

    public static <T> void afterCommit(final Supplier<T> supplier) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            public void afterCommit() {
                log.debug("TransactionManual afterCommit supplier execute. ", new Object[0]);
                supplier.get();
            }
        });
    }
}
