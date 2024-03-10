package com.rapid7.integration;

import com.google.common.collect.Lists;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.rapid7.client.dcerpc.mssrvs.ServerService;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo502;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import com.rapid7.client.dcerpc.transport.SMBTransportFactories;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class ServerServiceIntegrationTests {

    @Container
    private static final GenericContainer<?> sambaContainer = SambaContainer.create();

    private static final ArrayList<String> expectedShareNames = Lists.newArrayList("public", "readonly", "dfs", "user", "IPC$");

    @Test
    void testGetShares0() {
        TestUtil.run(sambaContainer, (Session session, Connection connection) -> {
            try {
                final RPCTransport transport = SMBTransportFactories.SRVSVC.getTransport(session);
                final ServerService serverService = new ServerService(transport);

                final List<NetShareInfo0> shares0 = serverService.getShares0();
                final List<String> shareNames = shares0.stream().map(NetShareInfo0::getNetName).collect(Collectors.toList());

                assertThat(shareNames).containsExactlyInAnyOrderElementsOf(expectedShareNames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testGetShares502() {
        TestUtil.run(sambaContainer, (Session session, Connection connection) -> {
            try {
                final RPCTransport transport = SMBTransportFactories.SRVSVC.getTransport(session);
                final ServerService serverService = new ServerService(transport);

                final List<NetShareInfo502> shares502 = serverService.getShares502();
                final List<String> shareNames = shares502.stream().map(NetShareInfo0::getNetName).collect(Collectors.toList());

                assertThat(shareNames).containsExactlyInAnyOrderElementsOf(expectedShareNames);

                assertEquals(1, shares502.get(0).getSecurityDescriptor().getDacl().getAces().size());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testGetShares503() {
        TestUtil.run(sambaContainer, (Session session, Connection connection) -> {
            try {
                final RPCTransport transport = SMBTransportFactories.SRVSVC.getTransport(session);
                final ServerService serverService = new ServerService(transport);

                final List<NetShareInfo502> shares502 = serverService.getShares502();
                final List<String> shareNames = shares502.stream().map(NetShareInfo0::getNetName).collect(Collectors.toList());

                assertThat(shareNames).containsExactlyInAnyOrderElementsOf(expectedShareNames);

                assertEquals(1, shares502.get(0).getSecurityDescriptor().getDacl().getAces().size());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
