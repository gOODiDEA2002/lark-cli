package {{.Package}}.config;

import lark.net.rpc.ServiceProxy;
import {{.Package}}.iface.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceAutoConfiguration {
    private final String SERVER = "{{.ServiceName}}";

    private ServiceProxy serviceProxy;

    @Autowired
    public ServiceAutoConfiguration(ServiceProxy serviceProxy) {
        this.serviceProxy = serviceProxy;
    }

    @Bean
    @ConditionalOnMissingBean
    public TestService testService() {
        return serviceProxy.get(SERVER, TestService.class);
    }
}