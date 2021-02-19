package {{.Package}}.iface;

import {{.Package}}.vo.TestVo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
/**
 * 测试接口
**/
@RestController
@RequestMapping("/test")
@Api(tags="测试接口")
public interface TestApi {

    @ApiOperation("Hello")
    @ApiResponse(responseCode="200", description="Hello")
    @PostMapping("/hello.api")
    public HelloResponse hello(@ApiParam("HelloRequest") HelloRequest hello );
}