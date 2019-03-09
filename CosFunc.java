import java.math.BigInteger;
//余弦函数
public class CosFunc {
    private BigInteger cosCoeff = BigInteger.ZERO; // 貌似不需要
    private BigInteger cosExpo = BigInteger.ZERO;
    //提取出cos的系数和和次数，貌似只用次数就可以了，把系数全都堆到幂函数那里去
    // 专注 cos(x)^99求导, 把cos(x)视为一个整体，进行幂求导（给powerfunc来做），然后再乘上 -sin(x)
    public void GetCosNum(String string) {

    }


}
