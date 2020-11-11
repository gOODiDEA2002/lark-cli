package {{.Package}}.msg;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TestMessage {
    private long orderId;
    int userId;
}