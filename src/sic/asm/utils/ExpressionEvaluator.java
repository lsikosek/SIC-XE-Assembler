package sic.asm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExpressionEvaluator {

    // A map to store token-value pairs
    private Map<String, Integer> variables;

    public ExpressionEvaluator(Map<String, Integer> variables) {
        this.variables = variables;
    }

    // Function to set a variable in the map
    public void setVariable(String key, int value) {
        variables.put(key, value);
    }

    // Main function to evaluate an expression
    public int evaluateExpression(String expression) throws Exception {
        // Replace variables with their values in the expression
        for (String key : variables.keySet()) {
        	if (key==null) continue;
            expression = expression.replace(key, variables.get(key).toString());
        }

        // Evaluate the resulting arithmetic expression
        return evaluateArithmetic(expression);
    }

    // Helper function to evaluate a simple arithmetic expression
    private int evaluateArithmetic(String expression) throws Exception {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Skip spaces
            if (ch == ' ') continue;

            // If the current character is a digit, parse the number
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--; // Step back for the loop increment
                values.push(Integer.parseInt(sb.toString()));
            }

            // If the current character is an operator, handle precedence
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            }
            else continue; //if (ch=='\n') continue;
//            else {
//            	throw new Exception("Unknown symbol \'" + ch + "\' in EQU expression.");
//            }
        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Helper function to determine operator precedence
    private boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    // Helper function to apply an operator to two operands
    private int applyOperation(char operator, int b, int a) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
        }
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }

//    public static void main(String[] args) {
//        ExpressionEvaluator evaluator = new ExpressionEvaluator();
//
//        // Set variables in the map
//        evaluator.setVariable("x", 5);
//        evaluator.setVariable("y", 10);
//
//        // Example expression
//        String expression = "3 + x * 2 - y / 5";
//
//        // Evaluate the expression
//        double result = evaluator.evaluateExpression(expression);
//        System.out.println("Result: " + result);
//    }
}
