import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigInteger;
import java.util.Scanner;

public class Mainclass {
    public boolean CheckInvalid(String string) {
        //空串
        if (string.isEmpty() || string.matches("\\s*")) { //空串判断
            //System.out.println("empty");
            System.out.println("WRONG FORMAT!");
            return false;
        }
        if (string.startsWith("*") || string.startsWith("^")
                || string.endsWith("^")
                || string.endsWith("*") || string.endsWith("+")
                || string.endsWith("-")) {
            System.out.println("WRONG FORMAT!");
            return false;
        }

        //先判断是否有非法字符,非法连续
        String invalidChar = "([^0-9xsinco\\(\\)(+)(\\-)(\\*)(\\^) \\t\\r])" +
                             "|(\\^\\s*\\*?\\s*\\^)" + // ^^, ^*^
                             "|([+-]\\s*\\^)" + // + ^
                             "|(\\*\\s*\\^)|(\\^\\s*\\*)" + // * ^, ^ *
                             "|(\\*\\s*\\*)"; // * *

        //     （大于1个符号的情况下最后一个符号与数字之间空格）
        // sin,cos字母间有空格
        String invalidSpace = "(\\d+\\s+\\d+)" +
                "|([+-]\\s*[+-]\\s*[+-]\\s+\\d+)" + // +++ 1错误，而++1 ,+++1 正确
                "|(\\^\\s*[+-]\\s+\\d+)|(\\*\\s*[+-]\\s+\\d+)" + // ^+ 2, *- 1
                "|([a-z]\\s+[a-z])" + // 字母与字母空格
                "|([+-]\\s*[+-]\\s*[+-]\\s*[a-z])" + // +++x, +++sin(x)
                "|((?<=\\*|\\^)\\s*[+-]\\s*[+-](\\s+\\d+)?)" + //,*^后两个+-
                "|(\\*\\s*[+-])|(\\^\\s*\\*)|([+-]\\s*\\*)" + // // *-, +*
                "|((\\s*[+-]\\s*){4,})";  // 连续3个符号

        Pattern v = Pattern.compile(invalidChar); // v检查非法字符
        Pattern v2 = Pattern.compile(invalidSpace); // v2检查非法空格
        Matcher m = v.matcher(string);
        Matcher m2 = v2.matcher(string);
        if (m.find()) {
            System.out.println("WRONG FORMAT!");
            //System.out.println("Invalid char");
            return false;
        }
        if (m2.find()) {
            System.out.println("WRONG FORMAT!");
            //System.out.println("invalid space");
            return false;
        }
        return true;
    }

    public String preTreatment(String input) {
        String fixedInput = input;
        // 去掉合法空格
        fixedInput = fixedInput.replaceAll("\\s+","");
        // ^+ , ^ , *+, *
        fixedInput = fixedInput
                .replaceAll("(\\^[+])","^")
                .replaceAll("(\\*[+])","*");//貌似*+是非法的

        // 归于正号
        fixedInput = fixedInput.replaceAll("([+][-][-])" +
                "|([-][-][+])" + // +--,--+
                "|([+]{2,3})|([-][-])|([-][+][-])","+"); // ++, +++ ,--, -+-
        //换符号,+-+,-++,++- 归于负号
        fixedInput = fixedInput.replaceAll("([-][+])|([-]{3})" + // -+， ---
                "|([+][-][+])|([-][+][+])|([+][+][-])","-");

        // 把所有不是幂次的减号，不是在乘号后面的减号，换成+-，反向否定预查
        fixedInput = fixedInput.replaceAll("(?<!\\^|\\*|\\+)[-]", "+-");

        if (fixedInput.startsWith("+")) {
            fixedInput = fixedInput.substring(1);
        }
        return fixedInput;

    }

    private ArrayList<Merge> poly = new ArrayList<Merge>();
    private Item[] items = new Item[10000];   //切割的Item class，多种函数乘积，含求导方法
    //private Merge[] merges = new Merge[30]; // 求导后的单个项

    public void SplitString(String input) {
        String[] separate = input.split("\\+");
        for (int i = 0; i < separate.length; i++) {
            // 现在得出了单个项，然后要用*分割不同种类函数
            //System.out.println("split group " + separate[i]);
            items[i] = new Item();           // 一定要记得新建项
            items[i].serString(separate[i]); // 赋予新对象字符串，是一个复合乘积项
            items[i].Cut();
            items[i].DeriItem(); // 求完导数，现在item里面有三个hashMap
            // 所以根据tmp的数值制作三个新的merge， HashMap<Integer,BigInteger> tmp1
            Merge merTmp1 = new Merge();
            merTmp1.setMerge(items[i].getDeriItem1());

            Merge merTmp2 = new Merge();
            merTmp2.setMerge(items[i].getDeriItem2());

            Merge merTmp3 = new Merge();
            merTmp3.setMerge(items[i].getDeriItem3());
            // 合并同类项
            boolean merge1 = false;
            boolean merge2 = false;
            if (poly.size() == 0) {
                poly.add(merTmp1);
                for (int k = 0; k < poly.size(); k++) {
                    if (poly.get(k).Compare(merTmp2)) {
                        poly.get(k).fixCoeff(merTmp2.getCoeff());
                        merge1 = true;
                        break;
                    }
                }
                if (!merge1) {
                    if (!merTmp2.getCoeff().equals(BigInteger.ZERO)) {
                        poly.add(merTmp2);
                    }
                }
                for (int k = 0; k < poly.size(); k++) {
                    if (poly.get(k).Compare(merTmp3)) {
                        poly.get(k).fixCoeff(merTmp3.getCoeff());
                        merge2 = true;
                        break;
                    }
                }
                if (!merge2) {
                    if (!merTmp3.getCoeff().equals(BigInteger.ZERO)) {
                        poly.add(merTmp3);
                    }
                }
            } else {
                MergeItem(merTmp1,merTmp2,merTmp3);
            }
        }
    }

    public void MergeItem(Merge merTmp1, Merge merTmp2, Merge merTmp3) {
        // now poly list has something
        boolean merge11 = false;
        boolean merge12 = false;
        boolean merge13 = false;
        for (int k = 0; k < poly.size(); k++) {
            if (poly.get(k).Compare(merTmp1)) {
                poly.get(k).fixCoeff(merTmp1.getCoeff());
                merge11 = true;
                break;
            }
        }
        if (!merge11) {
            if (!merTmp1.getCoeff().equals(BigInteger.ZERO)) {
                poly.add(merTmp1);
            }
        }
        for (int k = 0; k < poly.size(); k++) {
            if (poly.get(k).Compare(merTmp2)) {
                poly.get(k).fixCoeff(merTmp2.getCoeff());
                merge12 = true;
                break;
            }
        }
        if (!merge12) {
            if (!merTmp2.getCoeff().equals(BigInteger.ZERO)) {
                poly.add(merTmp2);
            }
        }
        for (int k = 0; k < poly.size(); k++) {
            if (poly.get(k).Compare(merTmp3)) {
                poly.get(k).fixCoeff(merTmp3.getCoeff());
                merge13 = true;
                break;
            }
        }
        if (!merge13) {
            if (!merTmp3.getCoeff().equals(BigInteger.ZERO)) {
                poly.add(merTmp3);
            }
        }

    }

    public void printAll() { // using poly
        boolean allZero = true;
        //System.out.println("Into print all ! polysize is " + poly.size());
        String output = "";
        for (int i = 0; i < poly.size(); i++) {
            Merge tmp = poly.get(i);
            if (tmp.getCoeff().equals(BigInteger.ZERO)) {
                continue;
            }
            else {
                allZero = false;
                output = output + tmp.getCoeff();
                if (!tmp.getPowerExpo().equals(BigInteger.ZERO)) { // 幂指数不为0
                    if (!tmp.getPowerExpo().equals(BigInteger.ONE)) {
                        output = output + "*x^" + tmp.getPowerExpo();
                    } else {
                        output = output + "*x";
                    }
                }
                if (!tmp.getSinExpo().equals(BigInteger.ZERO)) {
                    if (!tmp.getSinExpo().equals(BigInteger.ONE)) {
                        output = output + "*sin(x)^" + tmp.getSinExpo();
                    } else {
                        output = output + "*sin(x)";
                    }
                }
                if (!tmp.getCosExpo().equals(BigInteger.ZERO)) {
                    if (!tmp.getCosExpo().equals(BigInteger.ONE)) {
                        output = output + "*cos(x)^" + tmp.getCosExpo();
                    } else {
                        output = output + "*cos(x)";
                    }

                }
            }
            output = output + "+"; // 每一项后面都+
        }
        output = output.replace("1*", "");
        output = output.replace("+-","-");

        if (output.endsWith("+") || output.endsWith("*")) {
            output = output.substring(0, output.length() - 1);
        }
        if (allZero) {
            System.out.println("0");
        } else {
            System.out.println(output);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        if (!s.hasNextLine()) {
            System.out.println("WRONG FORMAT!");
            return;
        }
        String input = s.nextLine();
        boolean valid = new Mainclass().CheckInvalid(input);
        if (!valid) {
            return;
        }
        Mainclass newMain = new Mainclass();
        input = newMain.preTreatment(input);
        //System.out.println("after pretreatment : " + input);

        //预处理
        newMain.SplitString(input);
        newMain.printAll();


    }
}
