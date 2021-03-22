package {{.Package}};

import lark.api.boot.ApiApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class {{.ModuleName}}Bootstrap {
    public static void main(String[] args) {
        ApiApplication app = new ApiApplication({{.ModuleName}}Bootstrap.class);
        app.run(args);
    }
}