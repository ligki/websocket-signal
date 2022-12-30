package com.example.messagingstompwebsocket.client.signalimpl;

import com.example.messagingstompwebsocket.client.Client;
import lombok.AllArgsConstructor;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.SessionRecord;
import org.whispersystems.libsignal.state.SessionStore;

import java.util.List;

@AllArgsConstructor
public class MySessionStore implements SessionStore {
    private Client client;
    @Override
    public SessionRecord loadSession(SignalProtocolAddress address) {
        return null;
    }

    @Override
    public List<Integer> getSubDeviceSessions(String name) {
        return null;
    }

    @Override
    public void storeSession(SignalProtocolAddress address, SessionRecord record) {

    }

    @Override
    public boolean containsSession(SignalProtocolAddress address) {
        return false;
    }

    @Override
    public void deleteSession(SignalProtocolAddress address) {

    }

    @Override
    public void deleteAllSessions(String name) {

    }
}
