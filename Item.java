/*
 *这是一个单独的项，可能是多种函数的（幂）的乘积，在此拆分变成多种NormalFunc
 *在此合并乘法，例如 x^-2 * x^12 = x^10
 *  sin(x)^2 + cos^2 = 1
 *  x^9*sin(x)^7*cos(x)^12*3*x^-9*cos(x)^-8 = 3*sin(x)^7*cos(x)^12
 *用hashMap，key：， value：
 *也顺便在此循环求导，然后形成求导后的多项式字符串
*/
import java.util.HashMap;
import java.math.BigInteger;

public class Item {
    private String item;
    private String[] smallItem;

    private NormalFunc[] normalFuncs = new NormalFunc[100]; // 注意大小写

    private HashMap<Integer, BigInteger> countMap = new HashMap<>();

    public HashMap initializeMap(HashMap<Integer,BigInteger> inMap) {
        inMap.put(0,BigInteger.ONE); // 系数初始化为1对吗
        inMap.put(1,BigInteger.ZERO);
        inMap.put(2,BigInteger.ZERO);
        inMap.put(3,BigInteger.ZERO); // 哈希表初始化
        return inMap;
    }

    protected void serString(String string) {
        item = string;
    }

    public void Cut() { //一个Item带一个HashMap
        countMap = initializeMap(countMap); // 初始化次项哈希表，准备开始切割乘法然后合并幂次
        smallItem = item.split("\\*"); // 乘号切割, 用自己当前的项item 切割
        //切割后全都按组放到normal class当中
        for (int i = 0; i < smallItem.length; i++) {
            normalFuncs[i] = new NormalFunc(); // new一遍才能用
            normalFuncs[i].setItem(smallItem[i]); // 赋予字符串
            //System.out.println("the small Item is :" + smallItem[i]);

            int typeTmp = normalFuncs[i].JudgeType(); //已经提取好系数
            // 0 = 系数项；1：幂函数； 2 = 正弦； 3 = 余弦
            /*System.out.println("this smallitem's coeff and expo are "
                    + normalFuncs[i].getCoeff()
                    + " " + normalFuncs[i].getExpo());
            */
            if (typeTmp == 0) {
                //System.out.println("This is 常数项，系数要相乘");
                countMap.put(0,countMap.get(0)
                        .multiply(normalFuncs[i].getCoeff()));
            }
            // 后面的项对应指数相加
            else {
                //System.out.println("单一函数项，指数要相加！系数也要乘到Hash0");
                countMap.put(0, countMap.get(0)
                        .multiply(normalFuncs[i].getCoeff()));
                countMap.put(typeTmp,countMap.get(typeTmp)
                        .add(normalFuncs[i].getExpo()));
            }
        }

        /*for (int i = 0; i < 4; i++) { // no problem
            System.out.println("Hash " + i + " number: " + countMap.get(i));
        }*/
    }

    // 一个Item求导会出来三个项，(uvw)' = u'vw + uv'w + uvw'
    private HashMap<Integer,BigInteger> deriItem1 = new HashMap<>();
    private HashMap<Integer,BigInteger> deriItem2 = new HashMap<>();
    private HashMap<Integer,BigInteger> deriItem3 = new HashMap<>();

    public void DeriItem() { // 使用countMap
        for (int i = 0; i < 4; i++) { // 先把countMap里面的所有数值拷贝给deriItem
            deriItem1.put(i,countMap.get(i));
            deriItem2.put(i,countMap.get(i));
            deriItem3.put(i,countMap.get(i));
        }
        // 幂函数求导, 导函数系数 = 原系数*幂函数次方get1, 幂函数次方-1,三角函数不动
        deriItem1.put(0, deriItem1.get(0).multiply(countMap.get(1)));
        if (!deriItem1.get(1).equals(BigInteger.ZERO)) {
            deriItem1.put(1, deriItem1.get(1).subtract(BigInteger.ONE));
        }

        // 正弦求导，导函数系数 = 原系数*正弦次方get2，正弦次方-1，余弦次方+1
        deriItem2.put(0, deriItem2.get(0).multiply(countMap.get(2)));
        if (!deriItem2.get(2).equals(BigInteger.ZERO)) { //如果是0，都不用动
            deriItem2.put(2, deriItem2.get(2).subtract(BigInteger.ONE));
            deriItem2.put(3, deriItem2.get(3).add(BigInteger.ONE));
        }

        // 余弦求导，导函数系数 = 原系数*余弦次方*（-1），正弦次方+1, 余弦次方-1，
        deriItem3.put(0, (deriItem3.get(0).multiply(countMap.get(3))).negate());
        if (!deriItem3.get(3).equals(BigInteger.ZERO)) { // 次数不为0采用求导
            deriItem3.put(2, deriItem3.get(2).add(BigInteger.ONE));
            deriItem3.put(3, deriItem3.get(3).subtract(BigInteger.ONE));
        }
    }

    public HashMap<Integer, BigInteger> getDeriItem1() {
        return deriItem1;
    }

    public HashMap<Integer, BigInteger> getDeriItem2() {
        return deriItem2;
    }

    public HashMap<Integer, BigInteger> getDeriItem3() {
        return deriItem3;
    }

}
