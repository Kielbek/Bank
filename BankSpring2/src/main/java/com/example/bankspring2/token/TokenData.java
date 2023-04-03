package com.example.bankspring2.token;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TokenData implements Serializable {
    private Long id;
    private LocalDateTime time;

    public TokenData(Long id, LocalDateTime time) {
        this.id = id;
        this.time = time;
    }

    public TokenData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
