package com.psh10066.example.event;

import com.psh10066.example.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BEFORE_COMMIT_TransactionEvent {

    private User user;
}
