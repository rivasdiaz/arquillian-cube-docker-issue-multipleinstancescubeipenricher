package org.arquillian.cube.docker;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.arquillian.cube.CubeIp;
import org.arquillian.cube.containerobject.Cube;
import org.arquillian.cube.containerobject.Image;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MultipleInstanceCubeIpEnricherTest {

    @Cube
    private TomcatContainer tomcat;

    @CubeIp(containerName = TomcatContainer.CONTAINER_NAME)
    private String tomcatAddr;

    @After
    public void resetTomcatAddr() {
        tomcatAddr = null;
    }

    @Test
    public void tomcat_container_instantiated() {
        assertThat(tomcat, notNullValue());
    }

    @Test
    public void tomcat_incontainer_cubeip_assigned() throws IOException {
        assertThat(tomcat.getContainerAddr(), notNullValue());
    }

    @Test
    public void tomcat_intest_cubeip_assigned() throws IOException {
        assertThat(tomcatAddr, notNullValue());
    }

    @Cube(TomcatContainer.CONTAINER_NAME)
    @Image("tomcat:jre8")
    public static class TomcatContainer {

        public static final String CONTAINER_NAME = "tomcat";

        @CubeIp
        private String addr;

        public String getContainerAddr() {
            return addr;
        }
    }
}
