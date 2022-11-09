package com.dreamyprogrammer.calcjava;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class SimpleCalculator {

    private StringBuilder expression = new StringBuilder();
    private String expressionFinal;

    public SimpleCalculator() {
        //expression.append("0");
    }

    public void onButtonNumPressed(String buttonText) {
        if (canNumber(expression)) {
            if ("0".equals(buttonText)) {
                if (expression.length() != 0) {
                    expression.append("0");
                }
            } else {
                expression.append(buttonText);
            }
        }
    }

    public void onButtonOperationPressed(String buttonText) {

        if (lastCharOperation(expression)) {
            expression.replace(expression.length() - 1, expression.length(), buttonText);
        } else if (!getLastChar(expression).equals(","))
            expression.append(buttonText);
    }

    public void onButtonParenthesisStartPressed(String buttonText) {
        if (lastCharOperation(expression) || getLastChar(expression).equals("(")) {
            expression.append(buttonText);
        }
    }

    public void onButtonParenthesisEndPressed(String buttonText) {
        if (lastCharNum(expression) || getLastChar(expression).equals(")")) {
            expression.append(buttonText);
        }
    }

    public void onButtonPointPressed(String buttonText) {
        if (lastCharNum(expression)) {
            expression.append(buttonText);
        }
    }

    public void onButtonEquallyPressed() {
        expressionFinal = getEqually_private(expression.toString());
    }

    public void onResetZero() {
        expressionFinal = "0";
        expression.delete(0, expression.length());
    }

    public void onBackspace() {
        if (expression.length() > 0 ) {
            expression.delete(expression.length()-1,expression.length());
        }
    }

    public String getEqually() {
        return expressionFinal;
    }

    public String getText() {
        return expression.toString();
    }

    private boolean canNumber(StringBuilder str) {
        String subStr = getLastChar(str);
        return !subStr.equals(")");
    }

    private boolean lastCharOperation(StringBuilder str) {
        String subStr = getLastChar(str);
        return subStr.equals("+") || subStr.equals("-") || subStr.equals("*")
                || subStr.equals("/");
    }

    private boolean lastCharNum(StringBuilder str) {
        String subStr = getLastChar(str);
        return subStr.equals("0") || subStr.equals("1") || subStr.equals("2")
                || subStr.equals("3") || subStr.equals("4") || subStr.equals("5")
                || subStr.equals("6") || subStr.equals("7") || subStr.equals("8")
                || subStr.equals("9");
    }

    private String getLastChar(StringBuilder str) {
        if (str.length() > 0) {
            return str.substring(str.length() - 1);
        } else return "";
    }

    public void setExpression(String expression) {
        this.expression.append(expression);
    }

    private String getEqually_private(String expressionFinal) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scriptable = rhino.initStandardObjects();
            return rhino.evaluateString(scriptable, expressionFinal, "javascript", 1, null).toString();

        } catch (Exception e) {
            return "Error";
        }

    }

}
