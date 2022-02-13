package com.ekichabi_business_registration.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final HashMap<Long, Session> sessions = new HashMap<>();

    public Session getOrCreateSession(Long id, Supplier<Session> supplier) {
        if (!sessions.containsKey(id)) {
            sessions.put(id, supplier.get());
        }
        return sessions.get(id);
    }

    public boolean contains(Long id) {
        return sessions.containsKey(id);
    }

    public Session get(Long id) {
        return sessions.get(id);
    }

    public void put(Long id, Session session) {
        sessions.put(id, session);
    }
}
