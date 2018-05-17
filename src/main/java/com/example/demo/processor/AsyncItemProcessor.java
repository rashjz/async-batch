package com.example.demo.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Slf4j
public class AsyncItemProcessor<I, O> implements ItemProcessor<I, Future<O>>, InitializingBean {

    private ItemProcessor<I, O> delegate;

    private TaskExecutor taskExecutor = new SyncTaskExecutor();

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(delegate, "The delegate must be set.");
    }

    public void setDelegate(ItemProcessor<I, O> delegate) {
        this.delegate = delegate;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public Future<O> process(final I item) {
        final StepExecution stepExecution = getStepExecution();

        FutureTask<O> task = new FutureTask<>(() -> {
            if (stepExecution != null) {
                StepSynchronizationManager.register(stepExecution);
            }

            try {
                return delegate.process(item);
            } finally {
                if (stepExecution != null) {
                    StepSynchronizationManager.close();
                }
            }
        });

        taskExecutor.execute(task);

        return task;
    }

    private StepExecution getStepExecution() {
        StepContext context = StepSynchronizationManager.getContext();
        if (context == null) {
            return null;
        }

        return context.getStepExecution();
    }

}
