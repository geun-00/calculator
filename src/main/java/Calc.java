public class Calc {

    public static double run(String expression) {
        return calculate(expression.replaceAll(" ", "").trim());
    }

    //기저 조건 = 식이 아닌 숫자가 단 하나
    private static double calculate(String expr) {

        if (expr.startsWith("(") && getMatchingBracketIndex(expr) == expr.length() - 1) {
            return calculate(expr.substring(1, expr.length() - 1));
        }

        int pmIndex = solve(expr, '+', '-');
        if (pmIndex != -1) {
            char oper = expr.charAt(pmIndex);
            String left = expr.substring(0, pmIndex);
            String right = expr.substring(pmIndex + 1);

            double leftResult = calculate(left);
            double rightResult = calculate(right);

            if (oper == '+') return leftResult + rightResult;
            else return leftResult - rightResult;
        }

        int mdIndex = solve(expr, '*', '/');
        if (mdIndex != -1) {
            char oper = expr.charAt(mdIndex);
            String left = expr.substring(0, mdIndex);
            String right = expr.substring(mdIndex + 1);

            double leftResult = calculate(left);
            double rightResult = calculate(right);

            if (oper == '*') return leftResult * rightResult;
            else return leftResult / rightResult;
        }

        if (expr.startsWith("-")) {
            return -1 * calculate(expr.substring(1));
        }

        return Double.parseDouble(expr);
    }

    private static int solve(String expr, char op1, char op2) {
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
