package com.example.messagingstompwebsocket.client.signalimpl;

import com.example.messagingstompwebsocket.client.Client;
import lombok.AllArgsConstructor;
import org.whispersystems.libsignal.InvalidKeyIdException;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.PreKeyStore;

@AllArgsConstructor
public class MyPreKeyStore implements PreKeyStore {
    private Client client;
    @Override
    public PreKeyRecord loadPreKey(int preKeyId) throws InvalidKeyIdException {
        return null;
    }

    @Override
    public void storePreKey(int preKeyId, PreKeyRecord record) {

    }

    @Override
    public boolean containsPreKey(int preKeyId) {
        return false;
    }

    @Override
    public void removePreKey(int preKeyId) {

    }
}
