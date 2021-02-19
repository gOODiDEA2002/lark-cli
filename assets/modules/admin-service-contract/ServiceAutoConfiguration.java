package {{.Package}}.config;

import lark.net.rpc.client.ServiceFactory;
import {{.Package}}.iface.TestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceAutoConfiguration {
    private final String SERVER_NAME = "{{.ServiceName}}";

    private ServiceFactory serviceFactory;

    public ServiceAutoConfiguration(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public TestService testService() {
        return serviceFactory.get(SERVER_NAME, TestService.class);
    }
}