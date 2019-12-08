package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Scanner;

/**
 * Created by hfc on 2019/12/8.
 * 1.3章节的部分练习题
 */
public class Practise {

    public static void main(String[] args) {

        String defaultValue;
        Scanner input = new Scanner(System.in);
        // 1.3.4
//        defaultValue = "[()]{}{[()()]()}";
//        parentheses(input, defaultValue);

        // 1.3.9
        defaultValue = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
        completeExpression(input, defaultValue);

    }

    /**
     * 1.3.4 编写一个Stack的用例Parentheses，从标准输入中读取一个文本流
     * 并使用栈判定其中的括号是否配对完整且正确。
     * 例如，对于[()]{}{[()()]()}程序应该打印true，对于[(])则打印false
     */
    private static void parentheses(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.next()) != null) {
            if ("end".equals(value)) {
                break;
            }
            Stack<String> stack = new Stack<>();
            String[] array = value.split("");
            boolean wrong = false;
            for (String str : array) {
                if ("(".equals(str) || "[".equals(str) || "{".equals(str))
                    stack.push(str);
                else if (")".equals(str) && !"(".equals(stack.pop()))
                    wrong = true;
                else if ("]".equals(str) && !"[".equals(stack.pop()))
                    wrong = true;
                else if ("}".equals(str) && !"{".equals(stack.pop()))
                    wrong = true;

                if (wrong) {
                    break;
                }
            }

            if (wrong || !stack.isEmpty()) {
                System.out.println("false");
            } else {
                System.out.println("true");
            }
            defaultValue = null;
        }
    }

    /**
     * 1.3.9 编写一段程序，从标准输入得到一个缺少左括号的表达式并打印出补全括号之后的中序表达式
     * 例如，给定输入：1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )
     * 你的程序应该输出：
     * ( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )
     *
     * 思路：不完整的表达式一个一个入栈，数字入数字栈，符号入符号栈
     * 当遇到“)”时，先pop出符号栈的一个符号
     * 1、当前符号不是“)”，则先向符号栈push入一个“(”,再把pop出的符号push回来
     * 2、当前符号是“)”，先pop一个符号f1，在pop一个符号f2
     * ---- 2.1 如果f2不是“(”，则push回去，然后push一个“(”
     * ---- 2.2 如果f2是“(”，则表明当前pop出的符号组成了一个符号的右半数值部分（把括号内的表达式计算之后）
     */
    private static void completeExpression(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.next()) != null) {
            if ("end".equals(value)) {
                break;
            }

            Stack<String> numbers = new Stack<>();
            Stack<String> symbols = new Stack<>();
            Stack<String> symbolsTMP = new Stack<>();

            String[] array = defaultValue.split(" ");
            for (String str : array) {
                if (")".equals(str)) {
                    boolean isOk = false;
                    while (!isOk) {
                        // @fixme
                        while (")".equals(symbols.peek()))
                            symbolsTMP.push(symbols.pop());

                        symbolsTMP.push(symbols.pop());
                        if (!"(".equals(symbols.peek())) {
                            symbols.push("(");
                            for (String symbol : symbolsTMP)
                                symbols.push(symbol);

                            isOk = true;
                        } else {
                            symbolsTMP.push(symbols.pop());
                        }
                    }
                    symbols.push(str);
                } else {
                    switch (str) {
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                            symbols.push(str);
                            break;
                        default:
                            numbers.push(str);
                    }
                }
            }

            String tmp;
            StringBuilder sb = new StringBuilder();
            while ((tmp = symbols.peek()) != null) {
                if (!"(".equals(tmp) && !")".equals(tmp)) {
                    sb.append(numbers.pop())
                            .append(" ")
                            .append(symbols.pop())
                            .append(" ")
                            .append(numbers.pop())
                            .append(" ");
                } else {
                    sb.append(symbols.pop())
                            .append(" ");
                }
            }
            System.out.println(sb.reverse().toString());
            System.out.println("---stop----");
            defaultValue = null;
        }
    }
}
