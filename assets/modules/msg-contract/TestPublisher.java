package {{.Package}}.publisher;

import {{.Package}}.msg.TestMessage;
import {{.Package}}.topic.TestTopic;
import lark.util.msg.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestPublisher {

    @Autowired
    MessageService messageService;

    public boolean createOrder(TestMessage orderMsg ) {
        return messageService.send( TestTopic.CREATE_ORDER, orderMsg );
    }

}