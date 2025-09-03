package com.codebygaurav.AirbnbApp.test;

public class Test {

    public static void main(String[] args) {
    /*
        1,1, 2, 3, 5, 8,13, 21,
    * input - 1 - 1
          out put - 1= 1
                    2= 1,1
                    3= 1,1,2
                    4= 1 1 2 3
                    5= 1 1 2 3 5
                    8= 1 1 2 3 5 8


    * */
        int n = 10;
        for (int i = 0; i < n; i++) {
            generateSeq(i);
        }
        System.out.println("Hello");
    }

    public static String generateSeq(int i){
        if(i<=0) return "";

        String result="1";
        for(int j=1;j<i;j++){
            result= getNextElement(result);
        }
        return result;
    }

    public static String getNextElement(String term){
        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 0; i < term.length(); i++) {
            if(term.charAt(i) == term.charAt(i - 1)){
                count++;
            }else{
                sb.append(count).append(term.charAt(i-1));
                count=1;
            }
        }
        return sb.toString();
    }
}
