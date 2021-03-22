package {{.Package}};

import lark.service.boot.ServiceApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class {{.ModuleName}}Bootstrap {
    public static void main(String[] args) {
        ServiceApplication app = new ServiceApplication({{.ModuleName}}Bootstrap.class);
        app.run(args);
    }
}