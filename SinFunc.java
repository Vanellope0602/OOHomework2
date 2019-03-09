import java.math.BigInteger;
//正弦函数
public class SinFunc {
    private BigInteger sinCoeff = BigInteger.ZERO; // 貌似不需要
    private BigInteger sinExpo = BigInteger.ZERO;
    //提取出sin的系数和和次数，貌似只用次数就可以了，把系数全都堆到幂函数那里去
    // 专注 sin(x)^99求导，把sin(x)视为一个整体，进行幂求导（给powerfunc来做），然后再乘上 cos(x)
    public void GetSinNum(String string) {

    }


}
