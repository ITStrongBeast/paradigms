package expression.exceptions;

import expression.generic.GenericTabulator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            GenericTabulator tab = new GenericTabulator();
            Object[][][] res = tab.tabulate("", "2 + x  +y+z", 1, 3, 1, 3, 1, 3);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        System.out.println(res[i][j][k]);
                    }
                    System.out.println("\n");
                }
                System.out.println("=============");
            }
        } catch (Exception e){
            System.out.println(e);
        }
//        try {
//            ExpressionParser par = new ExpressionParser();
//            System.out.println(par.parse("($0+ 0)").toString());
//        } catch (ParseExceptions e){
//            System.out.println(e);
//        }
    }
}