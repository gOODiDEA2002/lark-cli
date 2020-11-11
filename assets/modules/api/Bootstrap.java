package {{.Package}};

import lark.api.boot.ApiApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) {
        ApiApplication app = new ApiApplication(Bootstrap.class);
        app.run(args);
    }
}