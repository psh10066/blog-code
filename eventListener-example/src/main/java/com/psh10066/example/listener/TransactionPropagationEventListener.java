package com.psh10066.example.listener;

import com.psh10066.example.domain.User;
import com.psh10066.example.domain.UserRepository;
import com.psh10066.example.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TransactionPropagationEventListener {

    private final UserRepository userRepository;

    @EventListener
    public void eventHandler(EventListener_TransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }

    @Transactional
    @EventListener
    public void eventHandler(EventListener_NewTransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void eventHandler(BEFORE_COMMIT_TransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void eventHandler(BEFORE_COMMIT_NewTransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void eventHandler(AFTER_COMPLETION_TransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void eventHandler(AFTER_COMPLETION_NewTransactionEvent event) {
        User user = event.getUser();
        System.out.println(">>>>>>>>>>>>>>> dirty checking 수정");
        user.updateUser("dirty checking 수정");

        System.out.println(">>>>>>>>>>>>>>> listener 생성");
        userRepository.save(new User("listener 생성"));
    }
}
