package a4;

import java.util.ArrayDeque;


/**
 * The Postfix class provides a method to evaluate mathematical expressions in postfix notation
 * using a stack-based approach.
 */
public class Postfix {

    /**
     * 
     * This method processes a queue of tokens representing a mathematical expression in postfix notation.
     *
     * @param tokens A queue of objects containing numbers (Double) and operators (Character) in postfix order.
     * @return The evaluated result of the postfix expression as a Double.
     * @throws IllegalArgumentException If the expression is invalid (e.g., incorrect number of operands or unknown operators).
     * @throws ArithmeticException If division by zero occurs.
     */
    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();
        
        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();
            
            if (token instanceof Double) {
                stack.addFirst((Double) token);
            } else if (token instanceof Character) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression");
                }
                double b = stack.removeFirst();
                double a = stack.removeFirst();
                char op = (Character) token;
                
                switch (op) {
                    case '+': stack.addFirst(a + b); break;
                    case '-': stack.addFirst(a - b); break;
                    case '*': stack.addFirst(a * b); break;
                    case '/': 
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        stack.addFirst(a / b); 
                        break;
                    case '^': stack.addFirst(Math.pow(a, b)); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + op);
                }
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }
        return stack.removeFirst();
    }
}