import java.math.BigInteger;
//专注幂求导，这是一个父类, 每一种函数都会有自己的系数和次数
//所以每一种函数在解决次方问题的时候操作都是一样的
public class PowerFunc {

    private BigInteger deriCoeff = BigInteger.ZERO;
    private BigInteger deriExpo = BigInteger.ZERO;

    public void PowerDerivate(BigInteger coeff, BigInteger exponent) {  //no need parameter
        if (coeff.equals(BigInteger.ZERO) || exponent.equals(BigInteger.ZERO)) {
            //do nothing
        } else if (!coeff.equals(BigInteger.ZERO)) {
            deriCoeff = coeff.multiply(exponent); // 导数系数 = 原系数*指数
            deriExpo = exponent.subtract(BigInteger.ONE);//导数幂 = 指数-1
        }
        System.out.println("deriCoeff is " + deriCoeff + " deriExpo is " + deriExpo); // debug

    }


}
