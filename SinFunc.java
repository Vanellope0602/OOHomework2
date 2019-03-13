import java.math.BigInteger;
//正弦函数

public class SinFunc {
    private BigInteger sinCoeff = BigInteger.ONE; // 初始化为1,貌似不需要这个东西
    private BigInteger sinExpo = BigInteger.ZERO;
    //提取出sin的系数和和次数，貌似只用次数就可以了，把系数全都堆到幂函数那里去
    // 专注 sin(x)^99求导，把sin(x)视为一个整体，进行幂求导（给powerfunc来做
    // 然后再乘上 cos(x)，即系数不变，cosExpo++

    public BigInteger getSinExpo() {
        return sinExpo;
    }

    public BigInteger getSinCoeff() {
        return sinCoeff;
    }

    public void GetSinNum(String string) { // 提取次数给sinExpo
        String s = string;
        if (s.startsWith("-")) {
            sinCoeff = BigInteger.ZERO.subtract(BigInteger.ONE); // -sin(x)
            s = s.substring(1); // 弄掉负号
        }
        if (string.contains("^")) {
            s = s.replace("sin(x)^", "");
            sinExpo = new BigInteger(s,10);
        } else {
            sinExpo = BigInteger.ONE;
        }
    }

}
