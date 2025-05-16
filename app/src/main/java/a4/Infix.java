package a4;

import java.util.ArrayDeque;


/**
 * The Infix class provides a method to convert an infix expression into postfix notation and evaluate the result using a stack-based algorithm.
 */
public class Infix {

     /**
     * Converts an infix expression into postfix notation and evaluates the result.
     *
     * @param tokens A queue of tokens representing the infix expression.
     * @return The evaluated result of the expression as a Double.
     * @throws IllegalArgumentException if the input expression has mismatched parentheses.
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        
        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();
            
            if (token instanceof Double) {
                outputQueue.addLast(token);
            } else if (token instanceof Character) {
                char op = (Character) token;
                if (op == '(') {
                    stack.addFirst(op);
                } else if (op == ')') {
                    while (!stack.isEmpty() && stack.peekFirst() != '(') {
                        outputQueue.addLast(stack.removeFirst());
                    }
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }
                    stack.removeFirst();
                } else {
                    while (!stack.isEmpty() && precedence(stack.peekFirst()) > precedence(op)) {
                        outputQueue.addLast(stack.removeFirst());
                    }
                    if (op != '^' && !stack.isEmpty() && precedence(stack.peekFirst()) == precedence(op)) {
                        outputQueue.addLast(stack.removeFirst());
                    }
                    stack.addFirst(op);
                }
            }
        }
        
        while (!stack.isEmpty()) {
            char op = stack.removeFirst();
            if (op == '(') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            outputQueue.addLast(op);
        }
        
        return Postfix.postfix(outputQueue);
    }

    /**
     * Determines the precedence of an operator.
     *
     * @param op The operator character.
     * @return An integer representing the precedence level.
     */
    private static int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return -1;
        }
    }
}
