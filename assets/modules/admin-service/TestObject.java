package {{.Package}}.entity;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TestObject {
    private int id;
    private String name;
}