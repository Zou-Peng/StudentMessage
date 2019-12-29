package com.yidong.util;

import java.util.Date;
import java.util.List;

public class StringUtil {

    public static String joinString(List<Integer> list,String split){
        String ret="";
        for (Integer l:list){
            ret += l+split;
        }
        if (!"".equals(ret)){
            ret=ret.substring(0,ret.length()-split.length());
        }
        return ret;
    }

    public static String generateSn(String prefix,String suffix){
        return prefix+new Date().getTime()+suffix;
    }
}
