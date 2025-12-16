package sn.esmt.tech;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import sn.esmt.tech.config.AsyncSyncConfiguration;
import sn.esmt.tech.config.EmbeddedKafka;
import sn.esmt.tech.config.EmbeddedSQL;
import sn.esmt.tech.config.JacksonConfiguration;
import sn.esmt.tech.config.TestSecurityConfiguration;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { FacturationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedSQL
@EmbeddedKafka
public @interface IntegrationTest {
}
