package MapStruct;

import java.util.Arrays;

/**
 * Created by hfc on 2024/7/7.
 */
public class UserTest {

    public static void main(String[] args) {
        User user = User.builder()
                .id(1)
                .name("zhangsan")
                .address("xyyy")
                .age(18)
                .gender("male").build();

        UserDTO dto = UserConvert.INSTANCE.convert(user);
        System.out.println(dto.toString());

        AccountInfo account = AccountInfo.builder()
                .id(112233L)
                .name("xyz")
                .value(10).build();
        dto = UserConvert.INSTANCE.mixConvert(user, account);
        System.out.println(dto.toString());

        account.setSkills(Arrays.asList("sing", "jump", "rap", "basketball"));
        dto = UserConvert.INSTANCE.mixConvertDetail(user, account);
        System.out.println(dto.toString());
    }

}
