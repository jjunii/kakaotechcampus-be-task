package com.kakaotechcampus.lv2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Calculator 인스턴스 생성
        Calculator calculator = new Calculator();

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

                int result = calculator.calculate(num1, num2, op);
                calculator.setResults((result));

                int output = calculator.getResults();
                System.out.println("결과: " + output);
                System.out.println("결과가 기록되었습니다.");

                // remove 사용
                System.out.println("첫번째 기록을 삭제하시겠습니까? (1 입력시 삭제)");
                int removeCheck = sc.nextInt();
                if (removeCheck == 1) {
                    calculator.removeResult();
                    System.out.println("삭제되었습니다.");
                }
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
