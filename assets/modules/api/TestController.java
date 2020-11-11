package {{.Package}}.controller;

import {{.Package}}.biz.TestBiz;
import {{.Package}}.contract.iface.TestApi;
import {{.Package}}.contract.vo.TestVo;
import lark.core.util.Strings;
import lark.api.response.ApiFaultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestApi {
    @Autowired
    TestBiz testBiz;

    @Override
    public TestVo.HelloResponse hello(TestVo.HelloRequest hello) {
        //check
        if ( hello.getId() == 0 ) {
            throw new ApiFaultException( 1000, "ID must >= 0" );
        }
        if (Strings.isEmpty( hello.getName() ) ) {
            throw new ApiFaultException( 1001, "Name is empty" );
        }
        return testBiz.hello( hello );
    }
}