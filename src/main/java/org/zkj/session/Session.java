package org.zkj.session;

import lombok.Data;

@Data
public class Session {
    private String userName;
    private String userId;

    public Session() {
    }

    public Session(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return userId+":"+userName;
    }
}
