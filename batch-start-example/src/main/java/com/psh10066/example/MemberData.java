package com.psh10066.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class MemberData {

    @Id
    private UUID id;

    private Long memberId;

    private String data;

    protected MemberData() {
    }

    public MemberData(UUID id, Long memberId, String data) {
        this.id = id;
        this.memberId = memberId;
        this.data = data;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getData() {
        return data;
    }
}
