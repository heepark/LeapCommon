package com.leap.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class RBUtil
{

    public RBUtil()
    {
    }

    public static String getMessage(String rbName, String errorCode)
    {
        Object args[] = null;
        return getMessage(rbName, errorCode, args);
    }

    public static String getMessage(String rbName, String errorCode, Object args[])
    {
        Locale locale = null;
        return getMessage(rbName, locale, errorCode, args);
    }

    public static String getMessage(String rbName, Locale locale, String errorCode)
    {
        Object args[] = null;
        return getMessage(rbName, locale, errorCode, args);
    }

    public static String getMessage(String rbName, Locale locale, String errorCode, Object args[])
    {
        String errorMessage = "";
        try
        {
            ResourceBundle rb = null;
            if(locale != null)
                rb = ResourceBundle.getBundle(rbName, locale);
            else
                rb = ResourceBundle.getBundle(rbName);
            if(rb != null)
            {
                errorMessage = rb.getString(errorCode);
                if(errorMessage != null && args != null && args.length > 0)
                    errorMessage = MessageFormat.format(errorMessage, args);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return errorMessage;
    }
}
