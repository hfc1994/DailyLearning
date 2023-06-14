
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hfc on 2023/6/7.
 *
 * 1）mock对象：调试期间真实对象的代理
 * 2）stub：打桩，为mock对象的方法指定返回值或指定行为
 * 3）verify：行为验证，验证指定方法调用情况等
 *
 */
public class MockitoTest {

    // 可以代替 mock(List.class) 来创建 mock 对象
    // 使用该种方法注入 mock 对象时，测试代码需要以 MockitoAnnotations.openMocks(this) 来开头
    @Mock
    private List basicList;

    // 若该注解修饰的对象有成员变量，@Mock 注解的成员变量会被自动注入
    @InjectMocks
    private List complexList;

    @SuppressWarnings("unchecked")
    @Test
    public void returnTest() {
        final List mockList = Mockito.mock(List.class);

        // 未打桩前，mock 对象的函数调用返回类型的默认值
        // Mockito 无法通过 when(...).thenReturn(...) 的方式对 static 和 final 方法进行 mock
        System.out.println(mockList.add("mock"));   // false
        System.out.println(mockList.get(0));    // null
        System.out.println(mockList.size());    // 0

        Mockito.when(mockList.get(0)).thenReturn("zero");
        Mockito.when(mockList.size()).thenReturn(996);

        System.out.println(mockList.get(0));    // zero
        System.out.println(mockList.size());    // 996

        // 设置方法调用抛出异常
        Mockito.when(mockList.get(123)).thenThrow(new RuntimeException("index out of bound"));
        Mockito.doThrow(new RuntimeException("index out of bound")).when(mockList).get(123);
//         System.out.println(mockList.get(123));

        // 无返回方法打桩？？？TODO
        Mockito.doNothing().when(mockList).clear();
        mockList.clear();

        final Answer<String> answer = new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                final List mock = (List) invocationOnMock.getMock();
                return "mock.size result = " + mock.size();
            }
        };
        Mockito.when(mockList.get(1)).thenAnswer(answer);
        Mockito.doAnswer(answer).when(mockList).get(1);
        System.out.println(mockList.get(1));    // mock.size result = 996

        // 对同一方法多次打桩，以最后一次为准
        Mockito.when(mockList.get(2)).thenReturn("test222");
        Mockito.when(mockList.get(2)).thenReturn("test333");
        System.out.println(mockList.get(2));    // test333
        System.out.println(mockList.get(2));    // test333

        // 设置多次调用的结果
        Mockito.when(mockList.get(3)).thenReturn("test222", "test333");
        Mockito.when(mockList.get(3)).thenReturn("test222").thenReturn("test333");
        System.out.println(mockList.get(3));    // test222
        System.out.println(mockList.get(3));    // test333
        System.out.println(mockList.get(3));    // test333

        Mockito.when(mockList.get(4)).thenReturn("test444").thenThrow(new RuntimeException("index out of bound"));
        Mockito.doReturn("test444").doThrow(new RuntimeException("index out of bound")).when(mockList).get(4);
        System.out.println(mockList.get(4));    // test444
//        System.out.println(mockList.get(4));    // RuntimeException
    }

    @Test
    public void verifyTest() {
        final List mockList = Mockito.mock(List.class);

        mockList.add("mock");
        mockList.get(0);
        mockList.size();
        mockList.clear();

        Mockito.verify(mockList).add("mock");
        // 验证方法被调用的次数，指定是 1 次
        Mockito.verify(mockList, Mockito.times(1)).get(0);
        // 验证方法被调用的次数，指定是 0 次
        Mockito.verify(mockList, Mockito.times(0)).get(1);
        // 验证方法至少被调用 1 次
        Mockito.verify(mockList, Mockito.atLeast(1)).size();
        // 验证方法最多被调用 2 次
        Mockito.verify(mockList, Mockito.atMost(2)).size();
        // 验证方法没有被调用
        Mockito.verify(mockList, Mockito.never()).remove(0);
        // 验证方法调用不超过 100ms，也就是指定超时时间
        Mockito.verify(mockList, Mockito.timeout(100)).clear();
        // 验证方法指定时间内最少调用 1 次
        Mockito.verify(mockList, Mockito.timeout(100).atLeastOnce()).size();
    }

    @Test
    public void argumentMatchersTest() {
        final Map mockMap = Mockito.mock(Map.class);

        when(mockMap.get("key1")).thenReturn("val1");
        System.out.println(mockMap.get("key1"));    // var1

        // 配置参数匹配器
        when(mockMap.get(anyString())).thenReturn("val");
        System.out.println(mockMap.get(anyString()));   // val
        System.out.println(mockMap.get("asdfggg")); // val
        System.out.println(mockMap.get("key1"));    // val
        System.out.println(mockMap.get(0)); // null

        // 多个入参时，要么都使用参数匹配器，要么都不使用，否则会发生异常
        when(mockMap.put(anyString(), anyInt())).thenReturn("valllll3");
        System.out.println(mockMap.put("key3", 3)); // valllll3
        System.out.println(mockMap.put(anyString(), anyInt())); // valllll3
//        System.out.println(mockMap.put("key3", anyInt()));  // Exception

        // 验证的时候，也支持使用参数匹配器
        verify(mockMap, atLeastOnce()).get(anyString());
        verify(mockMap).put(anyString(), eq(3));    // 验证前面只存在value=3的数据写入

        // TODO: 2023/6/15 咋写？
//        // 自定义参数匹配器
//        final ArgumentMatcher<Map> myArgumentMatcher = new ArgumentMatcher<Map>() {
//            @Override
//            public boolean matches(Map o) {
//                return o.containsKey("size") && o.size() >= 2;
//            }
//        };
    }

    @Test
    public void orderTest() {
        // 验证同一个对象多个方法的执行顺序
        List mockList = mock(List.class);
        mockList.add("1");
        mockList.add("2");
        mockList.add("3");
        InOrder inOrder = Mockito.inOrder(mockList);
        inOrder.verify(mockList).add("1");
//        inOrder.verify(mockList).add("3");  // Exception
        inOrder.verify(mockList).add("2");

        // 验证多个对象多个方法的执行顺序
        List list1 = mock(List.class);
        List list2 = mock(List.class);
        list1.get(0);
        list1.get(1);
        list2.get(0);
        list1.get(2);
        list2.get(1);
        inOrder = Mockito.inOrder(list1, list2);
        inOrder.verify(list1).get(0);
        inOrder.verify(list1).get(1);
        inOrder.verify(list2).get(0);
        inOrder.verify(list2).get(1);   // 跳过了 list1.get(2) 并不会异常，因为它确实在前面3个步骤的后面
//        inOrder.verify(list1).get(2);   // Execption，因为它应该在 list2.get(1) 前面
    }

    @Test
    public void interactionTest() {
        // 验证某个交互从未被执行
        List list = mock(List.class);
        list.add("1");
        verify(list, Mockito.never()).add("5");
//        verify(mock, Mockito.never()).add("1"); // Exception

        // 验证 mock 对象没有交互过
        List list1 = mock(List.class);
        List list2 = mock(List.class);
        verifyNoInteractions(list1);
        verifyNoInteractions(list1, list2);
        list1.get(0);
//        verifyNoInteractions(list1);    // Exception

        List list3 = mock(List.class);
//        verify(list3).add("1");   // 会导致 verifyNoInteractions 抛出异常
        verify(list3, Mockito.never()).add("1");
        verifyNoInteractions(list3);
    }

    // 通过监控（spy）对象，当使用 spy 对象时，真实的对象也会被调用，除非它的函数被打桩（stub）了
    // 使用监控对象就是部分 mock
    // Mockito 并不会为真实对象代理函数调用，实际上它会拷贝真实对象。因此如果保留了真实对象并且与之交互，
    // 不能从真实对象中得到正确的结果。
    @Test
    public void spyTest() {
        // stub 部分 mock，mock需要实现类而不能是抽象类或者接口
        Map oMap = mock(HashMap.class);
        when(oMap.get(anyString())).thenCallRealMethod();
        when(oMap.get("key1")).thenReturn("val1");
        System.out.println(oMap.get("key1"));   // val1
        System.out.println(oMap.get("key2"));   // null
        System.out.println(oMap.get("key3"));   // null

        // spy 部分 mock
        List<Integer> list = new ArrayList<Integer>();
        List spyList = spy(list);
        spyList.add(1); // 调用了真实方法
        spyList.add(2); // 调用了真实方法
        when(spyList.get(0)).thenReturn(33);
        doReturn(110).when(spyList).size();
        System.out.println(spyList.size()); // 110
        System.out.println(spyList.get(0)); // 33，打桩了，否则返回 1
        System.out.println(spyList.get(1)); // 2，调用了真实方法

        // TODO: 2023/6/15 为什么
        // spy 默认调用的是真实方法，第二种写法不等价于第一种
        doReturn("233").when(spyList).get(20);
//        when(spyList.get(20)).thenReturn("244");    // 会出现 IndexOutOfBoundsException 异常，不推荐这种写法
        System.out.println(spyList.get(20));
    }

}
