import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.HashMap;

public class Mainclass {
    public void CheckInvalid(String string) {

    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String input = s.nextLine();
        //先判断是否有非法字符

        // 再判断是否有非法空格
        // 例如：4+个符号连续，数字之间空格，数字与符号之间空格，幂次数前连续两个符号
        //     （大于1个符号的情况下最后一个符号与数字之间空格）
        // 判断sin(  x  ), cos(  x  )等写法是否正确,双重括号，

        //然后把已经确认合法的表达式中的空格全部去掉
        input = input.replaceAll("\\s*","");
        PowerFunc p = new PowerFunc();

        // 用非幂次的+-号分割字符串
        System.out.println("All done.");




    }
}
