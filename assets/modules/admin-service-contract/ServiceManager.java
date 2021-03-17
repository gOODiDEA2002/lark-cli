package {{.Package}};

import lark.net.rpc.client.ServiceFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class {{.ServiceName}}Manager {
    private final Map<Class, Object> services = new ConcurrentHashMap<>();
    ServiceFactory serviceFactory;

    public {{.ServiceName}}Manager(ServiceFactory serviceFactory ) {
        this.serviceFactory = serviceFactory;
    }

    public void registry( String packageName, String serverName ) {
        Set<Class<?>> classes = this.serviceFactory.getServices( packageName );
        if ( classes != null && classes.size() > 0 ) {
            for ( Class clazz : classes ) {
                setService( clazz, serviceFactory.get( serverName, clazz ) );
            }
        }
    }

    void setService( Class cls, Object service ) {
        services.put( cls, service );
    }

    public <T> T getService( Class<T> cls ) {
        return (T) services.get( cls );
    }
}
