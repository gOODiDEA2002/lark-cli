package {{.Package}};

import {{.Package}}.contract.constant.TestType;
import {{.Package}}.contract.dto.TestDto;
import {{.Package}}.contract.iface.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Bootstrap.class)
@ActiveProfiles("playground")
public class TestServiceTests {
    @Autowired
    private TestService testService;

    @Test
    public void testHello() {
        TestDto.HelloRequest request = new TestDto.HelloRequest();
        request.setId(123);

        TestDto.HelloResponse response = testService.hello(request);
        Assertions.assertSame(TestType.GOOD, response.getType() );
    }
}