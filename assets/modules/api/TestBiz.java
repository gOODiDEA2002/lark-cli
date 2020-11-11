package {{.Package}}.biz;

import {{.Package}}.contract.vo.TestVo;
import {{.CleanPackage}}.service.contract.constant.TestType;
import {{.CleanPackage}}.service.contract.dto.TestDto;
import {{.CleanPackage}}.service.contract.iface.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestBiz {
	@Autowired
	private TestService testService;

    public TestVo.HelloResponse hello( TestVo.HelloRequest request ) {
        TestDto.HelloRequest helloRequest = new TestDto.HelloRequest();
        helloRequest.setId( request.getId() );
        helloRequest.setType( TestType.GOOD );
        TestDto.HelloResponse helloResponse = testService.hello( helloRequest );
        //
        TestVo.HelloResponse response = new TestVo.HelloResponse();
        response.setResult( helloResponse.getResult() );
        response.setTime( helloResponse.getTime() );
        return response;
    }
}