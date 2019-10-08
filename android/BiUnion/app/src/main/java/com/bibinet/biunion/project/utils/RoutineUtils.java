package com.bibinet.biunion.project.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常规工具
 * Created by 吴昊 on 2017-4-18.
 */

public class RoutineUtils {

    public static boolean isInclude(String src, String tar){
        if(src.indexOf(tar)!=-1){
            return true;
        }else{
            return false;
        }
    }
}
