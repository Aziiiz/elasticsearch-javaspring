package elasitcsearch.client.io.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EsDocument {
    private String firstName;
    private String lastName;
    private int age;
    private String descpiption;

}
