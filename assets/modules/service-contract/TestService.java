package {{.Package}}.iface;

import {{.Package}}.dto.TestDto.*;
import org.springframework.web.bind.annotation.*;

/**
 * 测试服务
**/
@RpcService
public interface TestService {
	/**
	 * 测试
	**/
	@RpcMethod
	HelloResponse hello(HelloRequest request);
}