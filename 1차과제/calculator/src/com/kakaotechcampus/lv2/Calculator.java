package com.kakaotechcampus.lv2;

import java.util.ArrayList;

public class Calculator {
    // 연산 결과 저장
    private ArrayList<Integer> results = new ArrayList<>();

    public int calculate(int num1, int num2, char op) {
        int result = 0;
        // 연산
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 연산자입니다.");
        }
        return result;
    }

    public int getResults() {
        return this.results.getLast();
    }

    public void setResults(int result) {
        this.results.add(result);
    }

    public void removeResult(){
        if(results.isEmpty()){
            return;
        }
        results.remove(0);
    }
}
