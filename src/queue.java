import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        
        
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        int i = 0;
        
        while(i < q) {
            int input = sc.nextInt();
            
            if(input == 1) {
                stack1.push(sc.nextInt());
            }
            else{
                if(stack2.isEmpty()) {
                    while(!stack1.isEmpty()) {
                        stack2.push(stack1.pop());
                    }
                }
                if(input == 2) {
                    stack2.pop();
                }
                else if(input == 3){
                    if(!stack2.isEmpty())
                        System.out.println(stack2.peek());
                }
            }
            i++;
        }
        sc.close();
        
    }
}