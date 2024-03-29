package com.bibinet.biunion.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bibinet on 2017-5-3.
 */

public class PhoneNumberUtils {
    /**
     * 验证手机号格式
     */
    public static boolean isMobileNumber(String mobilenumber) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
        m = p.matcher(mobilenumber);
        b = m.matches();
        return b;
    }

    /**
     * 邮箱验证
     * @param src
     * @return
     */
    public static boolean isEmail(String src){
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(src);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id){
        if (id.toUpperCase().matches("(^\\d{17}([0-9]|X)$)")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检测密码是否合法
     */
    public static boolean isPassword(String password){
        if (password.matches("(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,18}")){
            return true;
        }else {
            return false;
        }
    }


}
