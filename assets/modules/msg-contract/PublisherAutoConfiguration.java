package {{.Package}}.config;

import {{.Package}}.publisher.TestPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TestPublisher getTestPublisher() {
        return new TestPublisher();
    }
}