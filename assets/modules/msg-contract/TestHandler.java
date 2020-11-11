package {{.Package}}.handler;

import {{.Package}}.msg.TestMessage;
import org.apache.rocketmq.spring.core.RocketMQListener;

public abstract class TestHandler implements RocketMQListener<TestMessage> {

    @Override
    public abstract void onMessage(TestMessage orderMsg );
}