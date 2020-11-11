package {{.Package}}.executor;

import {{.Package}}.biz.TestBiz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestExecutor {
    @Autowired
    TestBiz testBiz;

    @XxlJob("TestHandler")
    public ReturnT<String> testHandler(String param) throws Exception {
        int[] userIds = new int[]{123,1,0,124};
        for (int i = 0, count = userIds.length; i < count; i++) {
            String result = testBiz.hello( userIds[i] );
            XxlJobLogger.log("result:{}", result );
        }
        return ReturnT.SUCCESS;
    }
}
