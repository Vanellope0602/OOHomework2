import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/*
 *由输入被切割的单一种类函数项
 *这是一个父类, 各个类只包含次数不包含指数
 *所以每一种函数在解决  幂次方问题求导  的时候操作都是一样的，
 *系数变为指数，指数-1
 *分成的小项也要判断符不符合某正则表达式
 *在此单项求导
*/

public class NormalFunc {
    private int type; // 0 = 系数项；1：幂函数； 2 = 正弦； 3 = 余弦
    private String item = "";
    private BigInteger coeff = BigInteger.ONE; // 系数初始化为1
    private BigInteger expo = BigInteger.ZERO; // 次数初始化为0
    private Pattern pow = Pattern.compile("[+-]?x(\\^[-]?\\d+)?");
    private Pattern sin = Pattern.compile("[+-]?sin\\(x\\)(\\^[+-]?\\d+)?");
    private Pattern cos = Pattern.compile("[+-]?cos\\(x\\)(\\^[+-]?\\d+)?");
    // 如果不匹配这些形式的话，WRONG FORMAT

    protected void setItem(String s) {
        item = s; // 外面传的参数赋值给此对象的item字符串
    }

    public BigInteger getCoeff() {
        return coeff;
    }

    public BigInteger getExpo() {
        return expo;
    }

    public int JudgeType() {
        //同时已经可以调用JudgeType返回值,顺便调用三个小类提取系数和次数
        //JudgeType, 这几个Matcher一定要放在里面，防止提前制作好matcher而出现匹配错误的情况，后期修改一旦WF则停止程序
        Matcher mpow = pow.matcher(item);
        Matcher msin = sin.matcher(item);
        Matcher mcos = cos.matcher(item);
        if (item.contains("sin") || item.contains("i") || item.contains("n")) {
            type = 2;
            if (!msin.matches()) {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
            else {  // 提取次数
                SinFunc sinInstance = new SinFunc();
                sinInstance.GetSinNum(item);  //分离系数指数
                expo = sinInstance.getSinExpo(); // 提取指数
                coeff = sinInstance.getSinCoeff();
            }

        }
        else if (item.contains("cos") || item.contains("c")
                || item.contains("o")) {
            type = 3;
            if (!mcos.matches()) {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
            else {
                CosFunc cosInstance = new CosFunc();
                cosInstance.GetCosNum(item);  // 分离
                expo = cosInstance.getCosExpo(); //提取指数
                coeff = cosInstance.getCosCoeff();
            }
        }
        else if (item.contains("x")) {
            type = 1;
            if (!mpow.matches()) {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
            else {
                PowerFunc powInstance = new PowerFunc();
                powInstance.GetPowerNum(item);  // 分离
                expo = powInstance.getPowExpo();  //提取指数
                coeff = powInstance.getPowCoeff(); // 提取系数
                //System.out.println("get powCoeff is " + coeff);
            }
        }
        else {
            if (item.matches("[+-]?\\d+")) { // 带符号的数字
                type = 0; // 常数项，直接提取系数
                coeff = new BigInteger(item,10); // 常数项化为系数,指数为0 不管
            } else {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }

        }
        return type;
    }

}
