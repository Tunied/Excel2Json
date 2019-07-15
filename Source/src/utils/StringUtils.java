package utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean IsNumeric(String _str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(_str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static Object GetValueWithTypeFromString(String _str) {
        if (StringUtils.IsNumeric(_str)) {
            try {
                return Integer.parseInt(_str);
            } catch (Exception e) {
            }
            try {
                return Float.parseFloat(_str);
            } catch (Exception e) {
            }
            return null;
        } else {
            return _str;
        }
    }


}
