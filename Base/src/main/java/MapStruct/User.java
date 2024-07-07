package MapStruct;

import lombok.Builder;
import lombok.Data;

/**
 * Created by hfc on 2024/7/7.
 */
@Data
@Builder
public class User {

    private Integer id;

    private String name;

    private Integer age;

    private String address;

    private String gender;

}
