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
//        defaultValue = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
//        completeExpression(input, defaultValue);

        // 1.3.10
        defaultValue = "5 + 60 * ( 3 - 1 ) / ( 6 - 4 ) + 3";
        InfixToPostfix(input, defaultValue);
    }

    /**
     * 1.3.4 编写一个Stack的用例Parentheses，从标准输入中读取一个文本流
     * 并使用栈判定其中的括号是否配对完整且正确。
     * 例如，对于[()]{}{[()()]()}程序应该打印true，对于[(])则打印false
     */
    private static void parentheses(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
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
     * 注意：输入的式子有格式限制，比如2 - 3 + 4 )就没有正确的结果
     *
     * 思路：不完整的表达式一个一个入栈，数字入数字栈，符号入符号栈
     * 当遇到“)”时，先pop出符号栈的一个符号
     * 1、当前符号不是“)”，则先向符号栈push入一个“(”,再把pop出的符号push回来
     * 2、当前符号是“)”，先pop一个符号f1，在pop一个符号f2
     * ---- 2.1 如果f2不是“(”，则push回去，然后push一个“(”
     * ---- 2.2 如果f2是“(”，则表明当前pop出的符号组成了一个符号的右半数值部分（把括号内的表达式计算之后）
     *
     * @fixme 不完美，只能部分正确
     */
    private static void completeExpression(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            Stack<String> numbers = new Stack<>();
            Stack<String> symbols = new Stack<>();
            Stack<String> symbolsTMP = new Stack<>();

            String[] array = value.split(" ");
            for (String str : array) {
                if (")".equals(str)) {
                    boolean isOk = false;
                    // right表示“)”出现的个数，出现一个“(”能使right - 1
                    // couple表示“()”配对成功的个数
                    int right = 0, couple = 0;
                    while (!isOk) {
                        while (")".equals(symbols.peek())) {
                            symbolsTMP.push(symbols.pop());
                            right++;
                        }

                        symbolsTMP.push(symbols.pop());
                        if (!"(".equals(symbols.peek())) {
                            symbols.push("(");
                            while (null != symbolsTMP.peek())
                                symbols.push(symbolsTMP.pop());

                            isOk = true;
                        } else {
                            symbolsTMP.push(symbols.pop());
                            right--;
                            couple++;
                            if (null != symbols.peek()
                                    && (right == 0 || couple % 2 == 1))
                                symbolsTMP.push(symbols.pop());
                            else {
                                symbols.push("(");
                                couple++;
                                while (null != symbolsTMP.peek())
                                    symbols.push(symbolsTMP.pop());

                                isOk = true;
                            }
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

            String symbol;
            StringBuilder sb = new StringBuilder();
            while ((symbol = symbols.pop()) != null) {
                StringBuilder sb2 = new StringBuilder();
                if (!"(".equals(symbol) && !")".equals(symbol)
                        && !")".equals(symbols.peek())) {
                    String firstNumber = numbers.pop();
                    sb = sb2.append(" ")
                            .append(numbers.pop())
                            .append(" ")
                            .append(symbol)
                            .append(" ")
                            .append(firstNumber)
                            .append(sb);
                    continue;
                }
                sb = sb2.append(" ")
                        .append(symbol)
                        .append(sb);
            }
            System.out.println(sb.toString());
            defaultValue = null;
        }
    }

    /**
     * 1.3.10 编写一个过滤器InfixToPostfix，将算术表达式由中序表达式转为后序表达式。
     * 后序表达式从前往后，遇到op( + - * / )就对该符号之前的两个数值进行相应计算
     * 直到最后一个op( + - * / )的计算执行完毕。
     * 运算符优先级：先 * / 后 + -
     *              中序表达式                     |     后序表达式
     * 例子1：     2 * ( 5 - 1 )                   |   2 5 1 - *
     * 例子2： 5 + 60 * ( 3 - 1 ) / (6 - 4) + 3    |   5 60 3 1 - * 6 4 - / 3 + +
     */
    private static void InfixToPostfix(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            String[] array = value.split(" ");

            Stack<String> exp = new Stack<>();  // 表达式栈
            Stack<String> op = new Stack<>();   // 运算符栈
            int right = 0;  // 右括号出现的个数
            StringBuilder chain;
            int nextIndex = 0;
            for (String str : array) {
                nextIndex++;
                switch (str) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "(":
                        op.push(str);
                        break;
                    case ")":
                        right++;
                        do {
                            String firstPop = exp.pop();
                            chain = new StringBuilder();
                            chain.append(exp.pop())
                                    .append(" ")
                                    .append(firstPop)
                                    .append(" ")
                                    .append(op.pop())
                                    .append(" ");
                            exp.push(chain.toString());
                            if ("(".equals(op.peek())) {
                                if (right > 0) {
                                    right--;
                                    op.pop();
                                } else {
                                    break;
                                }
                            }
                        } while (("*".equals(op.peek()) || "/".equals(op.peek()))
                                    || (nextIndex < array.length && exp.size() >= 2
                                            && ("+".equals(array[nextIndex]) || "-".equals(array[nextIndex]))));
                        break;
                    default:
                        exp.push(str);
                        // @fixme  当数字是最后一个字符时，还应该再触发一个后续表达式拼接
                }
            }

            System.out.println("postfix = " + exp.pop());
            System.out.println("exp.isEmpty = " + exp.isEmpty() + ", op.isEmpty = " + op.isEmpty());
            defaultValue = null;
        }
    }

    /**
     * 读取输入值的模板方法
     * 该方法仅为了方便复制，别无它用
     */
    private void template(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            defaultValue = null;
        }
    }
}
