package org.arquillian.cube.docker;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.arquillian.cube.ContainerObjectFactory;
import org.arquillian.cube.CubeController;
import org.arquillian.cube.CubeIp;
import org.arquillian.cube.containerobject.Cube;
import org.arquillian.cube.containerobject.Image;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DynamicallyCreatedMultipleInstanceCubeIpEnricherTest {

    @ArquillianResource
    private ContainerObjectFactory factory;

    @ArquillianResource
    private CubeController ccontroller;

    private TomcatContainer tomcat;

    @Before
    public void createTomcatContainer() {
        tomcat = factory.createContainerObject(TomcatContainer.class);
    }

    @After
    public void destroyTomcatContainer() {
        tomcat = null;
        ccontroller.stop(TomcatContainer.CONTAINER_NAME);
        ccontroller.destroy(TomcatContainer.CONTAINER_NAME);
    }

    @Test
    public void tomcat_container_instantiated_and_cubeip_assigned_1() {
        assertThat(tomcat, notNullValue());
        assertThat(tomcat.getContainerAddr(), notNullValue());
    }

    @Test
    public void tomcat_container_instantiated_and_cubeip_assigned_2() {
        assertThat(tomcat, notNullValue());
        assertThat(tomcat.getContainerAddr(), notNullValue());
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
