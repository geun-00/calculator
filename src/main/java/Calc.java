public class Calc {

    public static double run(String expression) {
        return calculate(expression.replaceAll(" ", "").trim());
    }

    private static double calculate(String expr) {
        if (expr.startsWith("(") && getMatchingBracketIndex(expr) == expr.length() - 1) {
            return calculate(expr.substring(1, expr.length() - 1));
        }

        int pmIndex = getOperatorIndex(expr, '+', '-');
        if (pmIndex != -1) {
            return divideAndCalculate(expr, pmIndex);
        }

        int mdIndex = getOperatorIndex(expr, '*', '/');
        if (mdIndex != -1) {
            return divideAndCalculate(expr, mdIndex);
        }

        if (expr.startsWith("-")) {
            return -1 * calculate(expr.substring(1));
        }

        return Double.parseDouble(expr);
    }

    private static double divideAndCalculate(String expr, int pmIndex) {
        char oper = expr.charAt(pmIndex);
        String left = expr.substring(0, pmIndex);
        String right = expr.substring(pmIndex + 1);

        return calculate(left, oper, right);
    }

    private static double calculate(String left, char oper, String right) {
        double leftResult = calculate(left);
        double rightResult = calculate(right);

        return switch (oper) {
            case '+' -> leftResult + rightResult;
            case '-' -> leftResult - rightResult;
            case '*' -> leftResult * rightResult;
            case '/' -> {
                if (rightResult == 0.0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                yield leftResult / rightResult;
            }
            default -> throw new IllegalStateException("지원하지 않는 연산자입니다 : " + oper);
        };
    }

    private static int getOperatorIndex(String expr, char op1, char op2) {
        int count = 0;

        for (int i = expr.length() - 1; i >= 0; i--) {
            char ch = expr.charAt(i);

            if (ch == ')') count++;
            else if (ch == '(') count--;
            else if (count == 0) {

                if (ch == op1 || ch == op2) {
                    if (i == 0 || isOperator(expr.charAt(i - 1))) {
                        continue;
                    }
                    return i;
                }
            }
        }

        return -1;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int getMatchingBracketIndex(String expr) {
        int count = 0;

        for (int index = 0; index < expr.length(); index++) {
            char ch = expr.charAt(index);

            if (ch == '(') count++;
            else if (ch == ')') count--;

            if (count == 0) {
                return index;
            }
        }

        return -1;
    }
}
