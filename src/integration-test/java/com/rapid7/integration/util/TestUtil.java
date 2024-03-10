package com.rapid7.integration.util;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.security.bc.BCSecurityProvider;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import org.testcontainers.containers.GenericContainer;

public class TestUtil {

    private static final SmbConfig SMB_CONFIG = SmbConfig.builder().withSecurityProvider(new BCSecurityProvider()).withDialects(SMB2Dialect.SMB_2_1).withEncryptData(false).build();

    public static void run(GenericContainer<?> sambaContainer, ThrowingBiConsumer<Session, Connection> runnable) {
        try (
                final SMBClient smbClient = new SMBClient(SMB_CONFIG);
                final Connection smbConnection = smbClient.connect("localhost", sambaContainer.getFirstMappedPort());
                final Session session = smbConnection.authenticate(SambaContainer.getAuthenticationContext())
        ) {
            runnable.accept(session, smbConnection);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
