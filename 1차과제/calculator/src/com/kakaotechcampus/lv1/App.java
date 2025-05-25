package com.kakaotechcampus.lv1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    static int add(int a, int b) {
        return a + b;
    }

    static int subtract(int a, int b) {
        return a - b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }

    static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("0으로 나눌 수 없습니다.");
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true) {
            try {
                // 입력 받기
                System.out.print("첫 번째 숫자를 입력하세요: ");
                int num1 = sc.nextInt();
                if (num1 < 0){
                    throw new InputMismatchException();
                }
                System.out.print("두 번째 숫자를 입력하세요: ");
                int num2 = sc.nextInt();
                if (num2 < 0){
                    throw new InputMismatchException();
                }

                System.out.print("사칙연산 기호를 입력하세요: ");
                char op = sc.next().charAt(0);

                int result = 0;
                // 연산
                switch (op) {
                    case '+':
                        result = add(num1, num2);
                        break;
                    case '-':
                        result = subtract(num1, num2);
                        break;
                    case '*':
                        result = multiply(num1, num2);
                        break;
                    case '/':
                        result = divide(num1, num2);
                        break;
                    default:
                        throw new IllegalArgumentException("올바르지 않은 연산자입니다.");
                }
                System.out.println("결과: " + result);
            } catch (InputMismatchException e) {    // num1, num2에 숫자가 아닌 입력 시
                System.out.println("올바르지 않은 숫자입니다.");
            } catch (RuntimeException e) {  // 0 나누기, 연산자 예외
                System.out.println(e.getMessage());
            }

            // exit 입력 시 반복문 종료
            sc.nextLine();  // 이전 개행문자 처리
            System.out.println("더 계산하시겠습니까? (exit 입력 시 종료)");
            String endCheck = sc.nextLine();
            if (endCheck.equals("exit")) {
                break;
            }
        }

    }
}
