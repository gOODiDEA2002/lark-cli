package {{.Package}}.publisher;

import {{.Package}}.message.TestMessage;
import {{.Package}}.topic.TestTopic;
import lark.msg.Publisher;

public class TestPublisher {

    private Publisher publisher;

    public TestPublisher( Publisher publisher ) {
        this.publisher = publisher;
    }

    public void createOrder(TestMessage orderMessage ) {
        publisher.publish( TestTopic.CREATE_ORDER, orderMessage );
    }

}