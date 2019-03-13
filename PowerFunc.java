import java.math.BigInteger;

public class PowerFunc { //这是一个"幂函数类"
    private BigInteger powCoeff = BigInteger.ONE; // 幂系数初始化为1, 其实可删掉，放进来的系数一定是1
    private BigInteger powExpo = BigInteger.ZERO; //幂次数

    public BigInteger getPowCoeff() {
        return powCoeff;
    }

    public BigInteger getPowExpo() {
        return powExpo;
    }

    //提取幂函数的系数和指数
    public void GetPowerNum(String string) { // x, 2*x, x^2, 2*x^2, -x
        BigInteger minusOne = BigInteger.ZERO.subtract(BigInteger.ONE);
        String[] temp = string.split("x"); // x^7, 2*x, 2*x^7,两侧都有2* 和 ^7则都有
        //System.out.println("temp.length is :" + temp.length);
        if (temp.length == 0) {
            powExpo = BigInteger.ONE;
            powCoeff = BigInteger.ONE;
        }
        else if (temp[0].contains("*") && temp.length == 1) { // 如果剩下仅有2*则默认次数为1
            temp[0] = temp[0].replace("*","");
            powCoeff = new BigInteger(temp[0],10);
            powExpo = BigInteger.ONE;
        }

        else if (temp[0].contains("^") && temp.length == 1) { //如果剩下仅有^7， 默认系数为1
            powCoeff = BigInteger.ONE;
            temp[0] = temp[0].replace("^","");
            powExpo = new BigInteger(temp[0],10);
        }
        else if (temp[0].contains("-") && temp.length == 1) { // -x
            powCoeff = minusOne; // -1
            powExpo = BigInteger.ONE; // 指数为1
        }
        else if (temp.length == 2) { // absolutely is 2
            if (temp[0].contains("*")) {
                temp[0] = temp[0].replace("*","");
                powCoeff = new BigInteger(temp[0],10);
            }
            else {
                if (temp[0].contains("-")) {
                    powCoeff = BigInteger.ZERO.subtract(BigInteger.ONE);
                } else {
                    powCoeff = BigInteger.ONE;
                }
            }
            if (temp[1].contains("^")) {
                temp[1] = temp[1].replace("^","");
                powExpo = new BigInteger(temp[1],10);
            }
            else {
                powExpo = BigInteger.ONE;
            }

        }
    }

}
