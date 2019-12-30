package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/25.
 *
 * 1.3.44 文本编辑器的缓冲区。为文本编辑器的缓冲区设计一个数据类型并实现下表中的API
 * ---------------------------------------------------------------
 * public class Buffer
 * ---------------------------------------------------------------
 *          Buffer()            创建一块空缓冲区
 * void     insert(char c)      在光标位置插入字符c
 * char     delete()            删除并返回光标位置的字符
 * void     left(int k)         将光标向左移动k个位置
 * void     right(int k)        将光标向右移动k个位置
 * int      size()              缓冲区中的字符数量
 * ---------------------------------------------------------------
 * 提示：使用两个栈。
 */
public class Buffer {

    private Stack<Character> leftStack;
    private Stack<Character> rightStack;

    public Buffer() {
        leftStack = new Stack<>();
        rightStack = new Stack<>();
    }

    public void insert(char c) {
        leftStack.push(c);
    }

    public char delete() {
        return leftStack.pop();
    }

    public void left(int k) {
        for (int i=0; i<k; i++) {
            if (!leftStack.isEmpty()) {
                rightStack.push(leftStack.pop());
            }
        }
    }

    public void right(int k) {
        for (int i=0; i<k; i++) {
            if (!rightStack.isEmpty()) {
                leftStack.push(rightStack.pop());
            }
        }
    }

    public int size() {
        return leftStack.size();
    }

    public static void main(String[] args) {

    }
}
