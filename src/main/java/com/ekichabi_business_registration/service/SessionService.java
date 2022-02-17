package com.ekichabi_business_registration.service;


import com.ekichabi_business_registration.screens.stereotype.Screen;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final HashMap<String, Session> sessions = new HashMap<>();

    public Session getOrCreateSession(String id, Supplier<Session> supplier) {
        if (!sessions.containsKey(id)) {
            sessions.put(id, supplier.get());
        }
        return sessions.get(id);
    }

    public boolean contains(String id) {
        return sessions.containsKey(id);
    }

    public Session get(String id) {
        return sessions.get(id);
    }

    public void put(String id, Session session) {
        sessions.put(id, session);
    }

    public Session getSessionFromIdAndCommand(
            String id, String command, Supplier<Screen> defaultScreen)
            throws IllegalArgumentException {
        Session session;
        if (this.contains(id)) {
            session = get(id);
            if (session.getCommand().length() + 1 != command.length()) {
                throw new IllegalArgumentException("Inconsistent command history");
            }
            session.setCommand(command);
            val screen = session.getScreen().doAction(command.charAt(command.length() - 1));
            session.setScreen(screen);
        } else {
            if (!command.equals("")) {
                // fresh command should correspond to fresh session
                throw new IllegalArgumentException("Inconsistent command history");
            }
            session = new Session(id, defaultScreen.get(), command);
            put(id, session);
        }
        return session;
    }
}
