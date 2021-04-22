package com.sinszm.sofa.util;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 常用工具方法
 * @author sinszm
 */
public class BaseUtil {

    public static final char[] AC = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };

    /**
     * 数字按字符串长度要求添0补位并转换为字符串
     *
     * @param arg   源数据
     * @param len   位数
     * @return      结果
     */
    public synchronized static String covering(long arg, long len) {
        return String.format("%0" + len + "d", arg);
    }

    /**
     * 十进制转三十六进制
     *
     * @param n     十进制值
     * @return      三十六进制结果
     */
    public static String intToAc(long n) {
        StringBuilder s = new StringBuilder(16);
        String a;
        while (n != 0) {
            s.append(AC[(int) (n % 36)]);
            n = n / 36;
        }
        a = s.reverse().toString();
        return StrUtil.isEmpty(a) ? "0" : a;
    }

    /**
     * UUID规则返回
     *
     * @return 32位唯一字符串
     */
    public static String uuid() {
        return UUID.randomUUID()
                .toString()
                .toLowerCase()
                .replace("-", "");
    }

    /**
     * 用于List通过forEach得到下标和对象
     *
     * @param consumer  参数
     * @param <T>       泛型类型
     * @return          带序号数据
     */
    public static <T> Consumer<T> consumerWithIndex(BiConsumer<T, Integer> consumer) {
        class Obj {
            int i;
        }
        Obj obj = new Obj();
        return t -> {
            int index = obj.i++;
            consumer.accept(t, index);
        };
    }

    /**
     * 字符串格式化
     * @param arg0  字符串，可为null
     * @return      格式化字符串
     */
    public String trim(String arg0) {
        return StrUtil.trimToEmpty(arg0);
    }

    /**
     * 字符串判断是否为空
     * @param arg0  字符串
     * @return      true表示空，false表示非空
     */
    public boolean isEmpty(String arg0) {
        return StrUtil.isEmpty(trim(arg0));
    }

    /**
     * 生成MD5字符串
     * @param arg   字符串
     * @return      字符串对应编码串
     */
    public synchronized static String md5(String arg) {
        try {
            MessageDigest mdTemp = DigestUtils.getMd5Digest();
            mdTemp.update(arg.getBytes(StandardCharsets.UTF_8));
            return hex(mdTemp);
        }catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return "";
    }

    /**
     * SHA1签名方式
     * @param arg   字符串
     * @return      字符串对应编码串
     */
    public synchronized static String sha1(String arg) {
        try {
            final MessageDigest mdTemp = DigestUtils.getSha1Digest();
            mdTemp.update(arg.getBytes(StandardCharsets.UTF_8));
            return hex(mdTemp);
        } catch (Exception e) {
            return "";
        }
    }

    private static String hex(MessageDigest mdTemp) {
        byte[] md = mdTemp.digest();
        int j = md.length;
        char[] buf = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            buf[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
            buf[k++] = HEX_DIGITS[byte0 & 0xf];
        }
        return new String(buf);
    }

}
