import java.math.BigInteger;
//仅带有x的幂函数
public class NormalFunc {
    private BigInteger coeff = BigInteger.ZERO;
    private BigInteger exponent = BigInteger.ZERO;

    public void GetNormalNum(String string) { //提取幂函数的系数和指数
        String[] temp = string.split("x"); // x^7, 2*x, 2*x^7,两侧都有2* 和 ^7则都有

        if (temp[0].contains("*") && temp.length == 1) { // 如果剩下仅有2*，则默认次数为1
            temp[0] = temp[0].replace("*","");
            coeff = new BigInteger(temp[0],10);
            exponent = BigInteger.ONE;
        }
        else if(temp[0].contains("^") && temp.length == 1) { //如果剩下仅有^7， 默认系数为1
            coeff = BigInteger.ONE;
            temp[0] = temp[0].replace("^","");
            exponent = new BigInteger(temp[0],10);
        }
        else if(temp[0].contains("*") && temp[1].contains("^")) {
            temp[0] = temp[0].replace("*","");
            temp[1] = temp[1].replace("^","");
            coeff = new BigInteger(temp[0],10);
            exponent = new BigInteger(temp[1],10);
        }
        System.out.println("temp0 is " + temp[0]  +" temp.length is " + temp.length); // debug
    }
}
