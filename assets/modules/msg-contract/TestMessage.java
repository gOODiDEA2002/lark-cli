package {{.Package}}.message;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TestMessage {
    private long orderId;
    private int userId;
}