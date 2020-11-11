package {{.Package}}.impl;

import {{.Package}}.biz.TestBiz;
import {{.Package}}.contract.dto.TestDto;
import {{.Package}}.contract.constant.TestType;
import {{.Package}}.entity.TestObject;
import {{.Package}}.contract.iface.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import lark.core.util.Times;

@Service("测试服务")
public class TestServiceImpl implements TestService {
    @Autowired
    private TestBiz testBiz;

    @Override
    public TestDto.HelloResponse hello(TestDto.HelloRequest request) {
        TestObject object = testBiz.getObject(request.getId());
        TestDto.HelloResponse response = new TestDto.HelloResponse();
        response.setTime( Times.toEpochMilli( LocalDateTime.now().minusDays(-1) ) );
        response.setType(TestType.GOOD);
        response.setResult(object.getName());
        return response;
    }
}