package com.leap.util; 

import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtil {
	private static final Logger logger = LoggerFactory.getLogger(TypeUtil.class);

	public static String stringValue(Object obj, String initValue) {
		if (obj != null) {
			return obj.getClass().equals(String.class) ? ((String) obj).trim() : obj.toString().trim();
		} else {
			return initValue;
		}
	}

	public static String stringValue(Object obj) {
		return stringValue(obj, "");
	}

	public static boolean booleanValue(Object obj, boolean initValue) {
		Boolean value = booleanObject(obj);
		return value != null ? value : initValue;
	}

	public static boolean booleanValue(Object obj) {
		return booleanValue(obj, false);
	}

	public static Boolean booleanObject(Object obj, Boolean initValue) {
		if (obj != null) {
			String value = obj.toString().trim();
			if (value.equalsIgnoreCase(Boolean.TRUE.toString()) || value.equalsIgnoreCase(Boolean.FALSE.toString())) {
				return new Boolean(Boolean.parseBoolean(value));
			}
		}

		return initValue;
	}

	public static Boolean booleanObject(Object obj) {
		return booleanObject(obj, (Boolean) null);
	}

	public static int intValue(Object obj, int initValue) {
		Integer value = integerObject(obj);
		return value != null ? value : initValue;
	}

	public static int intValue(Object obj) {
		return intValue(obj, 0);
	}

	public static Integer integerObject(Object obj, Integer initValue) {
		if (obj != null) {
			try {
				if (obj.getClass().equals(String.class)) {
					return new Integer((int) Float.parseFloat((String) obj));
				}

				if (obj.getClass().equals(Integer.class)) {
					return (Integer) obj;
				}

				return new Integer((int) Float.parseFloat(obj.toString()));
			} catch (NumberFormatException var3) {
				logger.debug(var3.toString());
			}
		}

		return initValue;
	}

	public static Integer integerObject(Object obj) {
		return integerObject(obj, (Integer) null);
	}

	public static long longValue(Object obj, long initValue) {
		Long value = longObject(obj);
		return value != null ? value : initValue;
	}

	public static long longValue(Object obj) {
		return longValue(obj, 0L);
	}

	public static Long longObject(Object obj, Long initValue) {
		if (obj != null) {
			try {
				if (obj.getClass().equals(String.class)) {
					return new Long((long) Double.parseDouble((String) obj));
				}

				if (obj.getClass().equals(Long.class)) {
					return (Long) obj;
				}

				return new Long((long) Double.parseDouble(obj.toString()));
			} catch (NumberFormatException var3) {
				logger.debug(var3.toString());
			}
		}

		return initValue;
	}

	public static Long longObject(Object obj) {
		return longObject(obj, (Long) null);
	}

	public static float floatValue(Object obj, float initValue) {
		Float value = floatObject(obj);
		return value != null ? value : initValue;
	}

	public static float floatValue(Object obj) {
		return floatValue(obj, 0.0F);
	}

	public static Float floatObject(Object obj, Float initValue) {
		if (obj != null) {
			try {
				if (obj.getClass().equals(String.class)) {
					return new Float(Float.parseFloat((String) obj));
				}

				if (obj.getClass().equals(Float.class)) {
					return (Float) obj;
				}

				return new Float(Float.parseFloat(obj.toString()));
			} catch (NumberFormatException var3) {
				logger.debug(var3.toString());
			}
		}

		return initValue;
	}

	public static Float floatObject(Object obj) {
		return floatObject(obj, (Float) null);
	}

	public static double doubleValue(Object obj, double initValue) {
		Double value = doubleObject(obj);
		return value != null ? value : initValue;
	}

	public static double doubleValue(Object obj) {
		return doubleValue(obj, 0.0D);
	}

	public static Double doubleObject(Object obj, Double initValue) {
		if (obj != null) {
			try {
				if (obj.getClass().equals(String.class)) {
					return new Double(Double.parseDouble((String) obj));
				}

				if (obj.getClass().equals(Double.class)) {
					return (Double) obj;
				}

				return new Double(Double.parseDouble(obj.toString()));
			} catch (NumberFormatException var3) {
				logger.debug(var3.toString());
			}
		}

		return initValue;
	}

	public static Double doubleObject(Object obj) {
		return doubleObject(obj, (Double) null);
	}

	public static Date dateValue(Object obj) {
		return dateValue(obj, (Date) null);
	}

	public static Date dateValue(Object obj, Date initValue) {
		Date date = null;
		if (obj != null) {
			if (obj instanceof Date) {
				date = (Date) obj;
			} else {
				date = DateUtil.stringToDate(obj.toString());
			}
		}

		if (date == null) {
			date = initValue;
		}

		return date;
	}

	public static Date dateValue(Object obj, String pattern) {
		return dateValue(obj, pattern, Calendar.getInstance().getTime());
	}

	public static Date dateValue(Object obj, String pattern, Date initValue) {
		Date date = null;
		if (obj != null) {
			if (obj instanceof Date) {
				date = (Date) obj;
			} else {
				String dateString = obj.toString();
				date = DateUtil.stringToDate(dateString, pattern);
			}
		}

		if (date == null) {
			date = initValue;
		}

		return date;
	}
}
