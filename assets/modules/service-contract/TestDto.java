package {{.Package}}.dto;

import {{.Package}}.constant.TestType;
import lombok.*;

public class TestDto {
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
         * 类型
         */
        private TestType type;       

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
         * 类型
         */
        private TestType type;
        /**
         * 时间
         */
        private long time;
    }
}