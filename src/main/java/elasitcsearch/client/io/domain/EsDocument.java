package elasitcsearch.client.io.domain;

import lombok.Data;

@Data
public class EsDocument {
    private String firstName;
    private String lastName;
    private int age;
    private String descpiption;
}
