package {{.Package}}.iface;

import {{.Package}}.dto.TestDto.*;
import lark.net.rpc.annotation.RpcMethod;
import lark.net.rpc.annotation.RpcService;

/**
 * 测试服务
**/
@RpcService(description = "测试服务")
public interface TestService {
	/**
	 * 测试
	**/
	@RpcMethod(description = "测试")
	HelloResponse hello(HelloRequest request);
}