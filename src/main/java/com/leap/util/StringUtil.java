package com.leap.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static String DIGEST_MD5 = "MD5";
	private static String UNICODE_PREFIX = "\\u";
	private static Pattern REGEXP_PATTERN_IP = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
	private static String PATTERN_STR_STAR = "*";
	private static String REGEXP_SPECIAL_CHARS = "^$[](){}|*+?\\.";

	public static String replace(String text, String oldString, String newString) {
		if (text != null && oldString != null && newString != null) {
			if (oldString.length() == 0) {
				return null;
			} else {
				for (int index = 0; (index = text.indexOf(oldString, index)) >= 0; index += newString.length()) {
					text = text.substring(0, index) + newString + text.substring(index + oldString.length());
				}

				return text;
			}
		} else {
			return null;
		}
	}

	public static String replaceByPattern(String text, String pattern, String newStr, boolean ignoreCase) {
		Pattern p = null;
		if (ignoreCase) {
			p = Pattern.compile(pattern, 2);
		} else {
			p = Pattern.compile(pattern);
		}

		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, Matcher.quoteReplacement(newStr));
		}

		m.appendTail(sb);
		return sb.toString();
	}

	public static String replaceByPattern(String text, String pattern, String newStr) {
		return replaceByPattern(text, pattern, newStr, true);
	}

	public static String cutStringByBytes(String str, int length, String suffix) {
		byte[] bytes = str.getBytes();
		int len = bytes.length;
		if (length < len) {
			length -= suffix.length();
			int counter = 0;

			for (int i = length - 1; i >= 0; --i) {
				if ((bytes[i] & 128) != 0) {
					++counter;
				}
			}

			return new String(bytes, 0, length + counter % 2) + suffix;
		} else {
			return str;
		}
	}

	public static String cutStringByBytes(String str, int length) {
		return cutStringByBytes(str, length, "...");
	}

	public static String join(List<String> list, String delim) {
		if (list != null && delim != null) {
			StringBuffer buff = new StringBuffer();

			for (int i = 0; i < list.size(); ++i) {
				if (i > 0) {
					buff.append(delim);
				}

				buff.append((String) list.get(i));
			}

			return buff.toString();
		} else {
			return null;
		}
	}

	public static int getStringCount(String text, String findStr) {
		int count = 0;
		int index = 0;
		if (text != null && findStr != null) {
			if (findStr.length() > 0) {
				while (index <= text.length()) {
					index = text.indexOf(findStr, index);
					if (index < 0) {
						break;
					}

					++count;
					index += findStr.length();
				}
			}

			return count;
		} else {
			return count;
		}
	}

	public static String convertCharset(String text, String oldCharset, String newCharset) {
		if (text == null) {
			return null;
		} else {
			String newText = text;
			oldCharset = oldCharset != null ? oldCharset.trim() : "";
			newCharset = newCharset != null ? newCharset.trim() : "";

			try {
				if ((oldCharset == null || oldCharset.length() == 0) && newCharset != null && newCharset.length() > 0) {
					newText = new String(text.getBytes(), newCharset);
				} else if (oldCharset == null || oldCharset.length() <= 0
						|| newCharset != null && newCharset.length() != 0) {
					if (oldCharset != null && oldCharset.length() > 0 && newCharset != null
							&& newCharset.length() > 0) {
						newText = new String(text.getBytes(oldCharset), newCharset);
					}
				} else {
					newText = new String(text.getBytes(oldCharset));
				}
			} catch (Exception var5) {
				var5.printStackTrace();
			}

			return newText;
		}
	}

	public static String toBinaryString(int intNum, int textSize) {
		try {
			String binaryText = Integer.toBinaryString(intNum);
			return leftFill(binaryText, "0", textSize);
		} catch (Exception var3) {
			var3.printStackTrace();
			return null;
		}
	}

	public static String[] toBinaryArray(int intNum, int arrayLength) {
		String binaryStr = toBinaryString(intNum, arrayLength);
		if (binaryStr == null) {
			return null;
		} else {
			char[] chars = binaryStr.toCharArray();
			String[] strArray = new String[chars.length];

			for (int i = 0; i < chars.length; ++i) {
				strArray[i] = "" + chars[i];
			}

			return strArray;
		}
	}

	public static long toDecimalValue(String binaryString) throws Exception {
		int notation = 2;
		long resultValue = 0L;
		char[] binChars = binaryString.toCharArray();

		for (int i = 0; i < binChars.length; ++i) {
			int value = Integer.parseInt("" + binChars[i]);
			if (value >= notation) {
				throw new Exception("Wrong numeral notation !");
			}

			resultValue = (long) ((double) resultValue
					+ (double) value * Math.pow(2.0D, (double) (binChars.length - (i + 1))));
		}

		return resultValue;
	}

	public static long calculateMultipleValue(String propValue) {
		return calculateMultipleValue(propValue, 0L);
	}

	public static long calculateMultipleValue(String propValue, long initValue) {
		if (propValue == null) {
			return initValue;
		} else {
			try {
				long retValue = 1L;
				if (propValue.indexOf("*") >= 0) {
					for (StringTokenizer st = new StringTokenizer(propValue, "*"); st
							.hasMoreTokens(); retValue *= Long.parseLong(st.nextToken().trim())) {
						;
					}
				} else {
					retValue = Long.parseLong(propValue.trim());
				}

				return retValue;
			} catch (Exception var6) {
				return initValue;
			}
		}
	}

	public static String fill(String text, String fillChar, int textLength, boolean isRightward) {
		String resultText = null;
		if (text != null && fillChar != null && fillChar.length() == 1) {
			int diffLength = textLength - text.length();
			StringBuffer buff = new StringBuffer();

			for (int i = 0; i < diffLength; ++i) {
				buff.append(fillChar);
			}

			if (isRightward) {
				resultText = text + buff.toString();
			} else {
				resultText = buff.toString() + text;
			}
		}

		return resultText;
	}

	public static String leftFill(String text, String fillChar, int textLength) {
		return fill(text, fillChar, textLength, false);
	}

	public static String rightFill(String text, String fillChar, int textLength) {
		return fill(text, fillChar, textLength, true);
	}

	public static int calculateAge(String birthday) {
		long ONE_HOUR = 3600000L;
		long ONE_DAY = 86400000L;
		double var5 = 365.25D;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(birthday);
			Calendar calendar = Calendar.getInstance();
			long duration = calendar.getTime().getTime() - date.getTime();
			return (int) ((double) (duration / 86400000L) / 365.25D);
		} catch (Exception var12) {
			return 0;
		}
	}

	public static String extractText(String str, String prefix, String suffix) {
		String text = null;
		int prefixIndex = str.indexOf(prefix);
		int suffixIndex = str.indexOf(suffix, prefixIndex + prefix.length());
		if (prefixIndex > -1) {
			text = str.substring(prefixIndex + prefix.length(), suffixIndex);
		}

		return text;
	}

	public static String convertStrToUnicode(String str) {
		StringBuffer buffer = new StringBuffer(str);

		for (int i = buffer.length() - 1; i >= 0; --i) {
			char c = buffer.charAt(i);
			String hex = Integer.toHexString(c);
			if (hex.length() == 4) {
				buffer.replace(i, i + 1, UNICODE_PREFIX + hex);
			}
		}

		return buffer.toString();
	}

	public static String convertUnicodeToStr(String unicode) {
		StringBuffer buffer = new StringBuffer(unicode);
		int index = 0;

		while ((index = buffer.indexOf(UNICODE_PREFIX, index)) > -1) {
			try {
				int start = index + 2;
				int end = start + 4;
				String str = String.valueOf((char) Integer.parseInt(buffer.substring(start, end), 16));
				buffer.replace(index, end, str);
			} catch (Exception var6) {
				;
			}
		}

		return buffer.toString();
	}

	public static String cutLastFileSeparator(String path) {
		if (path != null && (path.endsWith("/") || path.endsWith("\\"))) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	public static String stripLastFileSeparator(String path) {
		return cutLastFileSeparator(path);
	}

	public static String getRepeatChars(String str, int count) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < count; ++i) {
			buffer.append(str);
		}

		return buffer.toString();
	}

	public static boolean isIpAddr(String ip) {
		Matcher m = REGEXP_PATTERN_IP.matcher(ip);
		return m.matches();
	}

	public static boolean isRegExpSpecialChar(String str) {
		return REGEXP_SPECIAL_CHARS.indexOf(str) >= 0;
	}

	public static boolean matchesIpAddr(String configIpPattern, String configIpDelim, String ipAddr) {
		boolean matchedIpAddr = false;
		if (!isIpAddr(ipAddr)) {
			return false;
		} else {
			String[] ipTokens = ipAddr.split("\\.");
			int ipTokensLength = ipTokens.length;
			String[] configIpAddrTokens = configIpPattern.split(configIpDelim);
			int configIpAddrTokensLength = configIpAddrTokens.length;

			for (int i = 0; i < configIpAddrTokensLength; ++i) {
				String configIpAddrToken = configIpAddrTokens[i].trim();
				if (configIpAddrToken.length() > 0) {
					if (configIpAddrToken.indexOf(PATTERN_STR_STAR) < 0) {
						if (configIpAddrToken.equals(ipAddr)) {
							matchedIpAddr = true;
							break;
						}
					} else {
						boolean matchedIp = true;
						String[] configIpTokens = configIpAddrToken.split("\\.");
						int configIpTokensLength = configIpTokens.length;

						for (int j = 0; j < ipTokensLength; ++j) {
							String confirIpToken = null;
							if (j < configIpTokensLength) {
								confirIpToken = configIpTokens[j].trim();
							} else {
								confirIpToken = PATTERN_STR_STAR;
							}

							String ipToken = ipTokens[j].trim();
							if (!PATTERN_STR_STAR.equals(confirIpToken) && !confirIpToken.equals(ipToken)) {
								matchedIp = false;
								break;
							}
						}

						if (matchedIp) {
							matchedIpAddr = true;
							break;
						}
					}
				}
			}

			return matchedIpAddr;
		}
	}

	public static String generateMD5Token(String[] keyTokens) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(DIGEST_MD5);

			for (int i = 0; i < keyTokens.length; ++i) {
				md5.update(keyTokens[i].getBytes());
			}

			return toHex(md5.digest());
		} catch (Exception var3) {
			System.err.print("Unable to calculate MD5 Digests");
			return null;
		}
	}

	private static String toHex(byte[] digest) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < digest.length; ++i) {
			buffer.append(Integer.toHexString(digest[i] & 255));
		}

		return buffer.toString();
	}
}
