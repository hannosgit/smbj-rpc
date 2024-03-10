package com.rapid7.integration;

import com.hierynomus.smbj.auth.AuthenticationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

public class SambaContainer extends GenericContainer<SambaContainer> {

    public static String USERNAME = "smbj";

    public static char[] PASSWORD = "smbj".toCharArray();

    public static String DOMAIN = "";

    public static GenericContainer<?> create() {
        return new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromClasspath("public", "docker-image/public")
                        .withFileFromClasspath("smb.conf", "docker-image/smb.conf")
                        .withFileFromClasspath("entrypoint.sh", "docker-image/entrypoint.sh")
                        .withFileFromClasspath("supervisord.conf", "docker-image/supervisord.conf")
                        .withFileFromClasspath("Dockerfile", "docker-image/Dockerfile")
        )
                .withExposedPorts(445)
                .waitingFor(Wait.forLogMessage(".*nmbd entered RUNNING state.*\\n", 1));
    }

    public static AuthenticationContext getAuthenticationContext(){
        return new AuthenticationContext(USERNAME, PASSWORD, DOMAIN);
    }

}
