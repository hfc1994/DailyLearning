package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.io.File;
import java.util.LinkedList;
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
//        Parentheses(input, defaultValue);

        // 1.3.9
//        defaultValue = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
//        CompleteExpression(input, defaultValue);

        // 1.3.10
//        defaultValue = "5 + 60 * ( 3 - 1 ) / ( 6 - 4 ) + 3";
//        InfixToPostfix(input, defaultValue);

        // 1.3.11
//        defaultValue = "5 60 3 1 - * 6 4 - / + 3 +";
//        EvaluatePostfix(input, defaultValue);

        // 1.3.40
//        defaultValue = "qeswrzdwsaeqzxsaq";
//        MoveToFront(input, defaultValue);

        // 1.3.43
        defaultValue = "D:\\";
        ToListFile(input, defaultValue);
    }

    /**
     * 1.3.4 编写一个Stack的用例Parentheses，从标准输入中读取一个文本流
     * 并使用栈判定其中的括号是否配对完整且正确。
     * 例如，对于[()]{}{[()()]()}程序应该打印true，对于[(])则打印false
     */
    private static void Parentheses(Scanner input, String defaultValue) {
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
    private static void CompleteExpression(Scanner input, String defaultValue) {
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
     * 网上的一种处理方法，相比我自己的，ta把符号和值拼成字符串再放入vals栈中，
     * 而我自己的存储的单个字符，逻辑复杂度高，我的InfixToPostfix有点类似该做法
     */
    public static String CompleteExpressionFromNetwork(String str) {
        java.util.Stack<String> ops = new java.util.Stack<>();
        java.util.Stack<String> vals = new java.util.Stack<>();

        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==')'){
                String v2 = vals.pop();
                String v1 = vals.pop();
                String t = "(" + v1 + ops.pop() + v2 + ")";
                vals.push(t);
            } else {
                if("+-*/".contains(String.valueOf(str.charAt(i)))){
                    ops.push(String.valueOf(str.charAt(i)));
                }
                else vals.push(String.valueOf(str.charAt(i)));
            }
        }
        return vals.pop();
    }

    /**
     * 1.3.10 编写一个过滤器InfixToPostfix，将算术表达式由中序表达式转为后序表达式。
     * 思路：后序表达式从前往后，遇到op( + - * / )就对该符号之前的两个数值进行相应
     * 计算直到最后一个op( + - * / )的计算执行完毕。
     * 运算符优先级：先 * / 后 + -
     *              中序表达式                     |     后序表达式
     * 例子1：     2 * ( 5 - 1 )                   |   2 5 1 - *
     * 例子2： 5 + 60 * ( 3 - 1 ) / ( 6 - 4 ) + 3  |   5 60 3 1 - * 6 4 - / + 3 +
     * 例子3： 6 + 5 * （ 7 - 2 ） / 3 + 6         |   6 5 7 2 - * 3 / + 6 +
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
            boolean doBuild = false, doJudge = false;
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
                        // pop掉(
                        op.pop();
                        doJudge = true;
                        break;
                    default:    // 数字
                        exp.push(str);
                        doJudge = true;
                }

                do {
                    if (doJudge && null != op.peek()) {
                        if ("*".equals(op.peek()) || "/".equals(op.peek())) {
                            // 前面是*或/
                            doBuild = true;
                        } else if (!"(".equals(op.peek())) {
                            if (nextIndex == array.length) {
                                // 最后一个运算符了
                                doBuild = true;
                            } else if (!"*".equals(array[nextIndex]) && !"/".equals(array[nextIndex])) {
                                // 前面和后面都是+或-
                                doBuild = true;
                            }
                        }
                    }
                    doJudge = false;

                    if (doBuild) {
                        doBuild = false;
                        doJudge = true;
                        String firstPop = exp.pop();
                        chain = new StringBuilder();
                        chain.append(exp.pop())
                                .append(" ")
                                .append(firstPop)
                                .append(" ")
                                .append(op.pop());
                        exp.push(chain.toString());
                    }
                } while (doJudge);

            }

            System.out.println("Infix = " + value);
            System.out.println("Postfix = " + exp.pop());
            System.out.println("exp.isEmpty = " + exp.isEmpty() + ", op.isEmpty = " + op.isEmpty());
            defaultValue = null;
        }
    }

    /**
     * 网上的一种实现方式，这种依赖于“括号”的正确以及足量使用
     * “括号”的足量并不是我的版本的必要条件
     */
    private static void InfixToPostfixFromNetwork() {
        Scanner input = new Scanner(System.in);
        String value = input.nextLine();

        java.util.Stack<String> ops = new java.util.Stack<>();
        java.util.Stack<String> vals = new java.util.Stack<>();
        for (int i=0; i<value.length(); i++) {
            String s = String.valueOf(value.charAt(i));

            if(s.equals("(")) {
            } else if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                ops.push(s);
            } else if(s.equals(")")) {
                String op = ops.pop();//operator
                String v = vals.pop();//value
                //only support +-*/ operator
                String subexpression = vals.pop() + " "  + v + " " + op;
                vals.push(subexpression);
            } else {
                vals.push(s);
            }
        }
        System.out.println(vals.pop());
    }

    /**
     * 1.3.11 编写一段程序EvaluatePostfix，从标准输入中得到一个后序表达式，求值并打印结果。
     * 例子：
     *  后序表达式：5 60 3 1 - * 6 4 - / + 3 +        结果：68
     *  后序表达式：2 5 1 - *                         结果：8
     *  后序表达式：6 5 7 2 - * 3 / + 6 +             结果：20
     */
    private static void EvaluatePostfix(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            String[] array = value.split(" ");
            Stack<String> numbers = new Stack<>();

            String firstPop;
            int ret;
            for (String str : array) {
                switch (str) {
                    case "+":
                        firstPop = numbers.pop();
                        ret = Integer.parseInt(numbers.pop()) + Integer.parseInt(firstPop);
                        numbers.push(String.valueOf(ret));
                        break;
                    case "-":
                        firstPop = numbers.pop();
                        ret = Integer.parseInt(numbers.pop()) - Integer.parseInt(firstPop);
                        numbers.push(String.valueOf(ret));
                        break;
                    case "*":
                        firstPop = numbers.pop();
                        ret = Integer.parseInt(numbers.pop()) * Integer.parseInt(firstPop);
                        numbers.push(String.valueOf(ret));
                        break;
                    case "/":
                        firstPop = numbers.pop();
                        ret = Integer.parseInt(numbers.pop()) / Integer.parseInt(firstPop);
                        numbers.push(String.valueOf(ret));
                        break;
                    default:
                        numbers.push(str);
                }
            }

            System.out.println("Postfix = " + value);
            System.out.println("Result = " + numbers.pop());
            System.out.println("numbers is empty = " + numbers.isEmpty());
            defaultValue = null;
        }
    }

    /**
     * 1.3.40 前移编码。从标准输入读取一串字符，使用链表保存这些字符并清除重复字符。
     * 当你读取了一个从未见过的字符时，将它插入表头。当你读取一个重复的字符时，将它
     * 从链表中删去并再次插入表头。将你的程序命名为MoveToFront：它实现了著名的前移
     * 编码策略，这种策略假设最近访问过的元素很可能会再次访问，因此可以用于缓存、数
     * 据压缩等许多场景。
     */
    private static void MoveToFront(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            String[] array = value.split("");
            LinkedList<String>  frontChar = new LinkedList<>();
            for (String str : array) {
                frontChar.remove(str);
                frontChar.addFirst(str);
            }

            System.out.println("the front char: ");
            frontChar.forEach(c -> System.out.print(c + " "));
            System.out.println();
            defaultValue = null;
        }
    }

    /**
     * 1.3.43 文件列表。文件夹就是一列文件和文件夹的列表。编写一个程序，从命令行接受
     * 一个文件夹命名作为参数，打印出该文件夹下的所有文件并用递归的方式在所有子文件夹
     * 的名下（缩进）列出其下的所有文件。提示：使用队列，并参考java.io.File
     *
     * 注：没看明白为什么要用队列，甚至感觉使用后进先出的栈都比队列合适
     */
    private static void ToListFile(Scanner input, String defaultValue) {
        String value;
        while ((value = defaultValue) != null || (value = input.nextLine()) != null) {
            if ("end".equals(value)) {
                break;
            }

            ReadRecursive(value, 0);
            defaultValue = null;
        }
    }

    private static void ReadRecursive(String path, int level) {
        File root = new File(path);
        if (root.isDirectory()) {
            printSplit(level, root.getName(), null);
            File[] children = root.listFiles();
            if (children == null)
                return;
            for (File child : children) {
                if (child.isHidden())
                    continue;
                if (child.isFile()) {
                    printSplit(level + 1, child.getName(), "|- ");
                } else {
                    ReadRecursive(child.getAbsolutePath(), level+1);
                }
            }
        } else {
            printSplit(level, root.getName(), "|- ");
        }
    }

    private static void printSplit(int count, String name, String append) {
        for (int i=0; i<count; i++)
            System.out.print("    ");
        if (append != null)
            System.out.print(append);
        System.out.println(name);
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

            String[] array = value.split(" ");

            defaultValue = null;
        }
    }
}
