import java.math.BigInteger;
import java.util.HashMap;

class Merge { // 求导完毕后的一个单独项，是合并同类项的一个基本单位，
    private BigInteger coeff = BigInteger.ONE;
    private BigInteger powerExpo = BigInteger.ZERO;// 幂函数x次方
    private BigInteger sinExpo = BigInteger.ZERO;  // sin的次方
    private BigInteger cosExpo = BigInteger.ZERO;  // cos的次方

    public void setMerge(HashMap<Integer, BigInteger> tmp) {
        coeff = tmp.get(0);
        powerExpo = tmp.get(1);
        sinExpo = tmp.get(2);
        cosExpo = tmp.get(3);
    }

    public BigInteger getCoeff() {
        return coeff;
    }

    public BigInteger getPowerExpo() {
        return powerExpo;
    }

    public BigInteger getSinExpo() {
        return sinExpo;
    }

    public BigInteger getCosExpo() {
        return cosExpo;
    }

    public void fixCoeff(BigInteger num) {
        this.coeff = this.coeff.add(num);
    }

    public boolean Compare(Merge tmp) { // 如果返回true则不用add poly
        if (tmp.getPowerExpo().equals(getPowerExpo())
                && tmp.getSinExpo().equals(getSinExpo())
                && tmp.getCosExpo().equals(getCosExpo())) {
            // 然后不要加入poly List
            return true;
        } else {
            return false;
        }
    }
}