package {{.Package}}.config;

import {{.Package}}.*;
import {{.Package}}.iface.*;
import lark.net.rpc.client.ServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceAutoConfiguration {
    private final String PACKAGE_NAME = "{{.Package}}.iface";
    private final String SERVER_NAME = "{{.CleanArtifactID}}";

    private ServiceFactory serviceFactory;

    public ServiceAutoConfiguration(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public TestService testService() {
        return serviceFactory.get(SERVER_NAME, TestService.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public {{.ServiceName}}Manager manager() {
        {{.ServiceName}}Manager manager = new {{.ServiceName}}Manager( serviceFactory );
        manager.registry( PACKAGE_NAME, SERVER_NAME );
        return manager;
    }
}