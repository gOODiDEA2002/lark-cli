package {{.Package}};

import {{.Package}}.contract.constant.TestType;
import {{.Package}}.contract.dto.TestDto;
import {{.Package}}.contract.iface.TestService;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class TestServiceTests {
    @Autowired
    private TestService testService;

    @Test
    public void testHello() {
        TestDto.HelloRequest request = new TestDto.HelloRequest();
        request.setId(123);

        TestDto.HelloResponse response = testService.hello(request);
        Assert.assertSame(TestType.GOOD, response.getType() );
    }
}