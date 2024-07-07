package MapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by hfc on 2024/7/7.
 *
 * 每次编译时都会生成当前接口的实现类
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    // 用于自定义映射关系
    @Mappings({
            @Mapping(source = "gender", target = "sex")
    })
    UserDTO convert(User user);

    // 从多个对象中提取属性
    // 所有重复的属性都必须指定来源对象（比如 id 和 name），即使是唯一属性也得指定来源对象（比如 gender）
    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.gender", target = "sex"),
            @Mapping(source = "account.id", target = "accountId"),
            @Mapping(source = "account.name", target = "accountName"),
            @Mapping(source = "account.value", target = "accountValue"),
    })
    UserDTO mixConvert(User user, AccountInfo account);

    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.gender", target = "sex"),
            @Mapping(source = "account.id", target = "accountId"),
            @Mapping(source = "account.name", target = "accountName"),
            @Mapping(source = "account.value", target = "accountValue"),
            // 如果 source 和 target 的属性名相同，则会出现如下报错
            // java: Can't map property "List<String> skills" to "String skills". Consider to declare/implement a mapping method: "String map(List<String> value)".
            @Mapping(source = "account.skills", target = "strSkills", qualifiedByName = "listTranslateToString")
//            @Mapping(expression = "java(listTranslateToString(account.getSkills()))", target = "strSkills")
    })
    UserDTO mixConvertDetail(User user, AccountInfo account);

    @Named("listTranslateToString")
    default String listTranslateToString(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return null;
        }

        return String.join(";", skills);
    }

}
