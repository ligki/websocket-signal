package com.example.messagingstompwebsocket.client.signalimpl;

import com.example.messagingstompwebsocket.client.Client;
import lombok.AllArgsConstructor;
import org.whispersystems.libsignal.InvalidKeyIdException;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyStore;

import java.util.List;

@AllArgsConstructor
public class MySignedPreKeyStore implements SignedPreKeyStore {
    private Client client;

    @Override
    public SignedPreKeyRecord loadSignedPreKey(int signedPreKeyId) throws InvalidKeyIdException {
        return null;
    }

    @Override
    public List<SignedPreKeyRecord> loadSignedPreKeys() {
        return null;
    }

    @Override
    public void storeSignedPreKey(int signedPreKeyId, SignedPreKeyRecord record) {

    }

    @Override
    public boolean containsSignedPreKey(int signedPreKeyId) {
        return false;
    }

    @Override
    public void removeSignedPreKey(int signedPreKeyId) {

    }
}
