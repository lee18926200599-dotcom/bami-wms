package com.org.permission.server.utils;

/**
 * 字符串 工具类
 */
public class NumericUtil {
	/**
	 * 为空 或 小于零 或 等于零
	 *
	 * @param number 数字
	 * @return <code>true</code> 小于零,<code>false</code>大于零
	 */
	public static boolean nullOrlessThanOrEqualToZero(Number number) {
		return null == number || number.longValue() <= 0;
	}

	/**
	 * 小于 零（可空）
	 *
	 * @param number 数字
	 * @return <code>true</code> 小于等于零,<code>false</code>大于零
	 */
	public static boolean lessThanZero(Number number) {
		return null == number || number.longValue() <= 0;
	}

	/**
	 * 小于 零（不为空）
	 *
	 * @param number 数字
	 * @return <code>true</code> 小于等于零,<code>false</code>大于零
	 */
	public static boolean lessZeroNotNull(Number number) {
		return null != number && number.longValue() < 0;
	}

	/**
	 * 大于 零（不为空）
	 *
	 * @param number 数字
	 * @return <code>true</code> 大于零,<code>false</code>小于等于零
	 */
	public static boolean greterThanZero(Number number) {
		return null != number && number.longValue() > 0;
	}
}
