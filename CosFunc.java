import java.math.BigInteger;
//余弦函数

public class CosFunc {
    private BigInteger cosCoeff = BigInteger.ONE; // 有可能是1！
    private BigInteger cosExpo = BigInteger.ZERO;

    //提取出cos的系数和和次数，貌似只用次数就可以了，把系数全都堆到幂函数那里去
    // 专注 cos(x)^99求导, 把cos(x)视为一个整体，进行幂求导（给powerfunc来做）
    // 然后再乘上 -sin(x), 系数变为负的，sinExpo++；
    public BigInteger getCosExpo() {
        return cosExpo;
    }

    public BigInteger getCosCoeff() {
        return cosCoeff;
    }

    public void GetCosNum(String string) { //提取次数
        String s = string;
        if (s.startsWith("-")) {
            cosCoeff = BigInteger.ZERO.subtract(BigInteger.ONE);
            s = s.substring(1); // 弄掉负号
        }
        if (s.contains("^")) {
            s = s.replace("cos(x)^", "");
            cosExpo = new BigInteger(s,10);
        } else {
            cosExpo = BigInteger.ONE;
        }
    }
}
