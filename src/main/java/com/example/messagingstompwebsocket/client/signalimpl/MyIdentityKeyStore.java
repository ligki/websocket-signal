package com.example.messagingstompwebsocket.client.signalimpl;

import com.example.messagingstompwebsocket.client.Client;
import lombok.AllArgsConstructor;
import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.IdentityKeyStore;

@AllArgsConstructor
public class MyIdentityKeyStore implements IdentityKeyStore {

    private Client client;
    @Override
    public IdentityKeyPair getIdentityKeyPair() {
        return null;
    }

    @Override
    public int getLocalRegistrationId() {
        return 0;
    }

    @Override
    public boolean saveIdentity(SignalProtocolAddress address, IdentityKey identityKey) {
        return false;
    }

    @Override
    public boolean isTrustedIdentity(SignalProtocolAddress address, IdentityKey identityKey, Direction direction) {
        return false;
    }

    @Override
    public IdentityKey getIdentity(SignalProtocolAddress address) {
        return null;
    }
}
