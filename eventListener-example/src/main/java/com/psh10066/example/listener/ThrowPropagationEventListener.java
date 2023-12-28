package com.psh10066.example.listener;

import com.psh10066.example.event.AFTER_COMMIT_ThrowEvent;
import com.psh10066.example.event.BEFORE_COMMIT_ThrowEvent;
import com.psh10066.example.event.EventListener_ThrowEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ThrowPropagationEventListener {

    @EventListener
    public void eventHandler(EventListener_ThrowEvent event) {
        throw new RuntimeException();
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void eventHandler(BEFORE_COMMIT_ThrowEvent event) {
        throw new RuntimeException();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void eventHandler(AFTER_COMMIT_ThrowEvent event) {
        throw new RuntimeException();
    }
}
