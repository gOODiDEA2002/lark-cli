package {{.Package}};

import lark.task.boot.TaskApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class {{.ModuleName}}Bootstrap {
    public static void main(String[] args) {
        TaskApplication app = new TaskApplication({{.ModuleName}}Bootstrap.class);
        app.run( args );
    }
}