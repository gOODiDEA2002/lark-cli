package {{.Package}}.handler;

import {{.Package}}.message.TestMessage;
import {{.Package}}.topic.TestTopic;
import lark.msg.AbstractHandler;
import lark.msg.MsgHandler;

@MsgHandler(topic = TestTopic.CREATE_ORDER, threads = 2)
public abstract class TestHandler extends AbstractHandler<TestMessage> {

}