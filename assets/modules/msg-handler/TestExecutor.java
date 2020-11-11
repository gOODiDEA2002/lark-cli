package {{.Package}}.executor;

import {{.CleanPackage}}.msg.contract.msg.TestMessage;
import {{.CleanPackage}}.msg.contract.handler.TestHandler;
import {{.CleanPackage}}.msg.contract.topic.TestTopic;
import {{.Package}}.biz.TestBiz;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@RocketMQMessageListener(topic = TestTopic.CREATE_ORDER, consumerGroup = "${message.test.consumer}")
public class TestExecutor extends TestHandler {
    Logger log = LoggerFactory.getLogger( TestHandler.class );

    @Autowired
    TestBiz testBiz;

    @Override
    public void onMessage(TestMessage msg ) {
        String result = testBiz.hello(msg.getUserId());
        log.info( ">>> CreateOrderMessage-:> orderId：{}, userId:{}, result:{}", msg.getOrderId(), msg.getUserId(), result );
    }
}