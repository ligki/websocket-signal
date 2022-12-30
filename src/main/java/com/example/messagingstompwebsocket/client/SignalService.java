package com.example.messagingstompwebsocket.client;

import com.example.messagingstompwebsocket.client.signalimpl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.whispersystems.libsignal.*;
import org.whispersystems.libsignal.protocol.CiphertextMessage;
import org.whispersystems.libsignal.state.*;
import org.whispersystems.libsignal.util.KeyHelper;

import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
@Getter
public class SignalService {
    /**
     * Store identityKeyPair somewhere durable and safe.
     */
    private IdentityKeyPair identityKeyPair;

    /**
     * Store registrationId somewhere durable and safe.
     */
    private int registrationId;

    /**
     * Store preKeys in PreKeyStore.
     */
    private List<PreKeyRecord> preKeys;

    /**
     * Store signed prekey in SignedPreKeyStore.
     */
    private SignedPreKeyRecord signedPreKey;

    public SignalService(int id) throws Exception {
        identityKeyPair = KeyHelper.generateIdentityKeyPair();
        registrationId = KeyHelper.generateRegistrationId(false);
        preKeys = KeyHelper.generatePreKeys(id, 100);
        signedPreKey = KeyHelper.generateSignedPreKey(identityKeyPair, 5);
    }

    /**
     * @param client          Client
     * @param recipientId     id of recipient
     * @param deviceId        device id of recipient
     * @param retrievedPreKey retrieved from the server.
     * @return SessionCipher
     * @throws Exception
     */
    public SessionCipher buildSignalSession(Client client, String recipientId, int deviceId, PreKeyBundle retrievedPreKey) throws Exception {
        SessionStore sessionStore = new MySessionStore(client);
        PreKeyStore preKeyStore = new MyPreKeyStore(client);
        SignedPreKeyStore signedPreKeyStore = new MySignedPreKeyStore(client);
        IdentityKeyStore identityStore = new MyIdentityKeyStore(client);

        var signalProtocolAddress = new SignalProtocolAddress(recipientId, deviceId);

        // Instantiate a SessionBuilder for a remote recipientId + deviceId tuple.
        SessionBuilder sessionBuilder = new SessionBuilder(sessionStore, preKeyStore, signedPreKeyStore,
                identityStore, signalProtocolAddress);

        // Build a session with a PreKey retrieved from the server.
        sessionBuilder.process(retrievedPreKey);

        SignalProtocolStore signalProtocolStore = new MySignalProtocolStore(identityStore, preKeyStore, sessionStore, signedPreKeyStore);
        return new SessionCipher(signalProtocolStore, signalProtocolAddress);
    }

    public CiphertextMessage encryptMessage(SessionCipher sessionCipher, String message) throws Exception {
        return sessionCipher.encrypt(message.getBytes(StandardCharsets.UTF_8));
    }
}
