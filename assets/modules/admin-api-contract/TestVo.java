package {{.Package}}.vo;

import lombok.*;

public class TestVo {
    /**
     * 请求参数
     */
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HelloRequest {
        /**
         * ID
         */
        private int id;
        /**
         * 姓名
         */
        private String name;
    }

    /**
     * 响应结果
     */
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class HelloResponse {
        /**
         * 结果
         */
        private String result;
        /**
         * 时间
         */
        private long time;
    }
}
