package {{.Package}}.executor;

import {{.CleanPackage}}.msg.contract.message.TestMessage;
import {{.CleanPackage}}.msg.contract.handler.TestHandler;
import {{.CleanPackage}}.msg.contract.topic.TestTopic;
import {{.Package}}.biz.TestBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lark.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 发送代码：
 * TestMessage msg = new TestMessage();
 * msg.setOrderId( 123 );
 * msg.setUserId( 123 );
 * testPublisher.createOrder( msg );
 */
@Component
public class TestExecutor extends TestHandler {
    Logger log = LoggerFactory.getLogger( TestHandler.class );

    @Autowired
    TestBiz testBiz;

    @Override
    public void process(TestMessage msg, Message raw ) {
        String result = testBiz.hello(msg.getUserId());
        log.info( ">>> CreateOrderMessage-:> orderId：{}, userId:{}, result:{}", msg.getOrderId(), msg.getUserId(), result );
    }
}