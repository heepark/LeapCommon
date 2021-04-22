package com.leap.util;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class BeanUtil {
	public static final String METHOD_PREFIX_SET = "set";
	public static final String METHOD_PREFIX_GET = "get";
	public static final String METHOD_PREFIX_IS = "is";
	public static final String METHOD_PREFIX_TO = "to";
	private static final String[] GETTING_METHOD_PREFIXES = new String[]{"get", "is", "to"};
	private static Pattern arrayIndexPattern = Pattern.compile("^\\[(\\d+)\\]$");

	public static void assignAttributes(Object srcObject, Object destObject, String[] attributeNames) {
		if (srcObject != null && destObject != null) {
			if (srcObject.getClass() != destObject.getClass()) {
				return;
			}

			try {
				for (int i = 0; i < attributeNames.length; ++i) {
					String attributeName = attributeNames[i];
					Object value = getAttributeValue(srcObject, attributeName);
					setAttributeValue(destObject, attributeName, value);
				}
			} catch (Exception var6) {
				var6.printStackTrace();
			}
		}

	}

	public static void assignAttributes(Object srcObject, Object destObject, String baseAttributeName, int begin,
			int end, String numberFormatPattern) {
		if (srcObject != null && destObject != null) {
			if (srcObject.getClass() != destObject.getClass()) {
				return;
			}

			try {
				DecimalFormat df = new DecimalFormat(numberFormatPattern);

				for (int i = begin; i <= end; ++i) {
					String attributeName = baseAttributeName + df.format((long) i);
					Object value = getAttributeValue(srcObject, attributeName);
					setAttributeValue(destObject, attributeName, value);
				}
			} catch (Exception var10) {
				var10.printStackTrace();
			}
		}

	}

	public static void assignAttributes(Object srcObject, Object destObject, Map<String, String> attributeMap) {
		if (srcObject != null && destObject != null) {
			try {
				Iterator srcAttributeNames = attributeMap.keySet().iterator();

				while (srcAttributeNames.hasNext()) {
					String srcAttributeName = (String) srcAttributeNames.next();
					String destAttributeName = (String) attributeMap.get(srcAttributeName);
					Object value = getAttributeValue(srcObject, srcAttributeName);
					setAttributeValue(destObject, destAttributeName, value);
				}
			} catch (Exception var7) {
				var7.printStackTrace();
			}
		}

	}

	public static void fillAttributes(Object object, Object attributeValue, String[] attributeNames) {
		if (object != null) {
			try {
				for (int i = 0; i < attributeNames.length; ++i) {
					String attributeName = attributeNames[i];
					setAttributeValue(object, attributeName, attributeValue);
				}
			} catch (Exception var5) {
				var5.printStackTrace();
			}
		}

	}

	public static void fillAttributes(Object object, Object value, String baseAttributeName, int begin, int end,
			String numberFormatPattern) {
		if (object != null) {
			try {
				DecimalFormat df = new DecimalFormat(numberFormatPattern);

				for (int i = begin; i <= end; ++i) {
					String attributeName = baseAttributeName + df.format((long) i);
					setAttributeValue(object, attributeName, value);
				}
			} catch (Exception var9) {
				var9.printStackTrace();
			}
		}

	}

	public static boolean existsAccessorMethod(Object object, String accessPrefix, String attributeName) {
		return existsAccessorMethod(object, accessPrefix, attributeName, (Class) null);
	}

	public static boolean existsAccessorMethod(Object object, String accessPrefix, String attributeName,
			Class<?> argType) {
		Method method = getAccessorMethod(object, accessPrefix, attributeName, argType);
		return method != null;
	}

	public static Method getAccessorMethod(Object object, String accessPrefix, String attributeName) {
		return getAccessorMethod(object, accessPrefix, attributeName, (Class) null);
	}

	public static Method getAccessorMethod(Object object, String accessPrefix, String attributeName, Class<?> argType) {
		String methodName = getAccessorMethodName(accessPrefix, attributeName);
		Method method = null;

		try {
			Class<?> cls = null;
			if (object instanceof Class) {
				cls = (Class) object;
			} else {
				cls = object.getClass();
			}

			if (argType != null) {
				try {
					method = cls.getMethod(methodName, argType);
				} catch (NoSuchMethodException var8) {
					if (argType.equals(Boolean.class)) {
						method = cls.getMethod(methodName, Boolean.TYPE);
					} else if (argType.equals(Integer.class)) {
						method = cls.getMethod(methodName, Integer.TYPE);
					} else if (argType.equals(Long.class)) {
						method = cls.getMethod(methodName, Long.TYPE);
					} else if (argType.equals(Float.class)) {
						method = cls.getMethod(methodName, Float.TYPE);
					} else if (argType.equals(Double.class)) {
						method = cls.getMethod(methodName, Double.TYPE);
					}
				}
			} else {
				method = cls.getMethod(methodName);
			}

			if (method == null && accessPrefix.equalsIgnoreCase("set")) {
				method = findMethodByName(cls, methodName);
			}
		} catch (NoSuchMethodException var9) {
			;
		}

		return method;
	}

	public static void setAttributeValue(Object object, String attributeName, Object attributeValue) {
		setAttributeValue(object, attributeName, (Class) null, attributeValue);
	}

	public static void setAttributeValue(Object object, String attributeName, Class<?> argType, Object attributeValue) {
		try {
			attributeName = TypeUtil.stringValue(attributeName);
			if (object != null && attributeName.length() > 0) {
				if (argType == null && attributeValue != null) {
					argType = attributeValue.getClass();
				}

				Object targetObject = object;
				attributeName = replaceNonDotArrayPrefix(attributeName);
				int cutIndex = attributeName.lastIndexOf(".");
				if (cutIndex >= 0) {
					String objectAttributeName = attributeName.substring(0, cutIndex);
					targetObject = getAttributeValue(object, objectAttributeName);
					if (targetObject == null) {
						throw new Exception("Can't found targetObject # " + objectAttributeName);
					}

					attributeName = attributeName.substring(cutIndex + 1);
				}

				if (targetObject != null) {
					if (targetObject instanceof Map) {
						Map mapTargetObject = (Map) targetObject;
						mapTargetObject.put(attributeName, attributeValue);
					} else {
						int index;
						if (targetObject instanceof List) {
							List listTargetObject = (List) targetObject;
							index = getArrayTypeIndex(attributeName);
							if (index >= 0 && index < listTargetObject.size()) {
								listTargetObject.set(index, attributeValue);
							} else {
								listTargetObject.add(attributeValue);
							}
						} else if (targetObject instanceof Object[]) {
							Object[] arrayTargetObject = (Object[]) ((Object[]) ((Object[]) targetObject));
							index = getArrayTypeIndex(attributeName);
							if (index >= 0 && index < arrayTargetObject.length) {
								arrayTargetObject[index] = attributeValue;
							}
						} else if (targetObject instanceof JSONObject) {
							JSONObject jsonObjectTargetObject = (JSONObject) targetObject;
							jsonObjectTargetObject.put(attributeName, attributeValue);
						} else if (targetObject instanceof JSONArray) {
							JSONArray jsonArrayTargetObject = (JSONArray) targetObject;
							index = getArrayTypeIndex(attributeName);
							if (index >= 0 && index < jsonArrayTargetObject.length()) {
								jsonArrayTargetObject.put(index, attributeValue);
							} else {
								jsonArrayTargetObject.put(attributeValue);
							}
						} else {
							Method setMethod = getAccessorMethod(targetObject, "set", attributeName, argType);
							if (setMethod == null) {
								throw new NoSuchMethodException("Can't found " + attributeName + " set method");
							}

							setMethod.invoke(targetObject, attributeValue);
						}
					}
				}
			}
		} catch (Exception var8) {
			var8.printStackTrace();
		}

	}

	public static Object getAttributeValue(Object object, String attributeName) {
		try {
			Object attrValueObj = object;
			attributeName = replaceNonDotArrayPrefix(attributeName);
			String[] attrNameTokens = attributeName.split("\\.");
			String[] arr = attrNameTokens;
			int len = attrNameTokens.length;

			for (int i = 0; i < len; ++i) {
				String attrNameToken = arr[i];
				if (attrValueObj != null && attrNameToken != null && attrNameToken.trim().length() > 0) {
					Method getMethod = null;
					if (attrValueObj instanceof Map) {
						Map mapAttrValueObj = (Map) attrValueObj;
						attrValueObj = mapAttrValueObj.get(attrNameToken);
					} else {
						int index;
						if (attrValueObj instanceof List) {
							List listAttrValueObj = (List) attrValueObj;
							index = getArrayTypeIndex(attrNameToken);
							if (index >= 0 && index < listAttrValueObj.size()) {
								attrValueObj = listAttrValueObj.get(index);
							} else {
								attrValueObj = null;
							}
						} else if (attrValueObj instanceof Object[]) {
							Object[] arrayAttrValueObj = (Object[]) ((Object[]) attrValueObj);
							index = getArrayTypeIndex(attrNameToken);
							if (index >= 0 && index < arrayAttrValueObj.length) {
								attrValueObj = arrayAttrValueObj[index];
							} else {
								attrValueObj = null;
							}
						} else if (attrValueObj instanceof JSONObject) {
							JSONObject jsonObjectAttrValueObj = (JSONObject) attrValueObj;
							attrValueObj = jsonObjectAttrValueObj.get(attrNameToken);
						} else if (attrValueObj instanceof JSONArray) {
							JSONArray jsonArrayAttrValueObj = (JSONArray) attrValueObj;
							index = getArrayTypeIndex(attrNameToken);
							if (index >= 0 && index < jsonArrayAttrValueObj.length()) {
								attrValueObj = jsonArrayAttrValueObj.get(index);
							} else {
								attrValueObj = null;
							}
						} else {
							String[] arrPrefix = GETTING_METHOD_PREFIXES;
							index = arrPrefix.length;

							for (int j = 0; j < index; ++j) {
								String methodPrefix = arrPrefix[j];
								getMethod = getAccessorMethod(attrValueObj, methodPrefix, attrNameToken);
								if (getMethod != null) {
									break;
								}
							}

							if (getMethod != null) {
								attrValueObj = getMethod.invoke(attrValueObj);
							} else {
								attrValueObj = null;
							}
						}
					}
				}
			}

			return attrValueObj;
		} catch (Exception var13) {
			var13.printStackTrace();
			return null;
		}
	}

	public static boolean isSameObject(Object a, Object b, List<String> attributeNames) {
		if (!a.getClass().equals(b.getClass())) {
			return false;
		} else {
			Iterator i = attributeNames.iterator();

			while (i.hasNext()) {
				String attributeName = (String) i.next();

				try {
					Object aValue = getAttributeValue(a, attributeName);
					Object bValue = getAttributeValue(b, attributeName);
					if (aValue != null) {
						if (!aValue.equals(bValue)) {
							return false;
						}
					} else if (bValue != null) {
						return false;
					}
				} catch (Exception var7) {
					return false;
				}
			}

			return true;
		}
	}

	private static String getAccessorMethodName(String accessPrefix, String attributeName) {
		return attributeName != null && attributeName.length() > 0
				? accessPrefix + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1)
				: accessPrefix;
	}

	private static Method findMethodByName(Class<?> cls, String methodName) {
		if (cls != null) {
			Method[] methods = cls.getMethods();
			Method[] arr = methods;
			int len = methods.length;

			for (int i = 0; i < len; ++i) {
				Method method = arr[i];
				if (method.getName().equals(methodName)) {
					return method;
				}
			}
		}

		return null;
	}

	private static int getArrayTypeIndex(String attrName) {
		Matcher m = arrayIndexPattern.matcher(attrName);
		if (m.matches()) {
			int index = TypeUtil.intValue(m.group(1), -1);
			if (index >= 0) {
				return index;
			}
		}

		return -1;
	}

	private static String replaceNonDotArrayPrefix(String attributeName) {
		if (attributeName.indexOf("[") >= 0) {
			attributeName = attributeName.replaceAll("([^.])(\\[)", "$1.$2");
		}

		return attributeName;
	}
}