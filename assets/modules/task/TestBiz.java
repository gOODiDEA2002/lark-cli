package {{.Package}}.biz;

import {{.CleanPackage}}.service.contract.constant.TestType;
import {{.CleanPackage}}.service.contract.dto.TestDto;
import {{.CleanPackage}}.service.contract.iface.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBiz {
    @Autowired
    TestService testService;

    public String hello( int id ) {
        TestDto.HelloRequest helloRequest = new TestDto.HelloRequest();
        helloRequest.setId( id );
        helloRequest.setType( TestType.GOOD );
        TestDto.HelloResponse helloResponse = testService.hello( helloRequest );
        //
        return helloResponse.getResult();
    }
}
