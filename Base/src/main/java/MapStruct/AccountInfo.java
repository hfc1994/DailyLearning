package MapStruct;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by hfc on 2024/7/7.
 */
@Data
@Builder
public class AccountInfo {

    private Long id;

    private String name;

    private Integer value;

    private List<String> skills;

}
