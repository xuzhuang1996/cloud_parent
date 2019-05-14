package xu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    }

    public static boolean match(char[] str, char[] pattern)
    {
        int i=0,j=0;
        while(true){
            if(i>=str.length || j>=pattern.length)
                return false;

            if(str[i]==pattern[j] || pattern[j]=='.'){
                if(i<str.length-1){
                    i++;
                    j++;
                    continue;
                }else
                    return i==str.length-1 && j==pattern.length-1;
            }

            if(pattern[j]=='*'){
                if(j-1>=0){
                    j--;
                    continue;
                }else{
                    return false;
                }
            }

            //现在处理不相等的情况
            if(j+1<pattern.length && pattern[j+1]=='*'){
                j+=2;//跳过这两个符号
            }
        }
    }
}
