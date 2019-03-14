/**
 * Copyright (C), 2014-2018, 蜂狂购:www.fkgou.com
 * FileName: MessageMD5Util
 * Author:   BeeMaven
 * Date:     2018/6/25 16:53
 * Description: MD5加密
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.cristi.util;

//import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 〈MD5加密〉 
 * @author BeeMaven
 * @create 2018/6/25 
 */
public class MessageMD5Util {

    /**
     * MD532位加密
     */
    public static String encoder32ByMd5(String str)
    {
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e)
        {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }




    /**利用MD5  64位进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String Encoder64ByMd5 (String str) {
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //加密后的字符串
        String newstr="";
        try{
            newstr=Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return newstr;
    }
}
