package com.common.core.util;

public class NumberUtils
{
  public static double do4Down5Up(double a, int n)
  {
    long lPower = Math.round(Math.pow(10.0D, n));
    return (Math.round(a * lPower) / lPower);
  }

  public static float do4Down5Up(float a, int n)
  {
    long lPower = Math.round(Math.pow(10.0D, n));
    return (Math.round(a * (float)lPower) / (float)lPower);
  }

  public static double parseAsDouble(String strData, double dDefault) {
    double dRet = dDefault;
    try
    {
      dRet = Double.parseDouble(strData.trim());
    }
    catch (Exception localException)
    {
    }

    return dRet;
  }

  public static float parseAsFloat(String strData, float fDefault) {
    float fRet = fDefault;
    try
    {
      fRet = Float.parseFloat(strData.trim());
    }
    catch (Exception localException)
    {
    }

    return fRet;
  }

  public static int parseAsInt(String strData, int nDefault) {
    int nRet = nDefault;
    try
    {
      nRet = Integer.parseInt(strData.trim());
    }
    catch (Exception localException)
    {
    }

    return nRet;
  }

  public static long parseAsLong(String strData, long lDefault) {
    long lRet = lDefault;
    try
    {
      lRet = Long.parseLong(strData.trim());
    }
    catch (Exception localException)
    {
    }

    return lRet;
  }

  public static double Long2Double(long lngValue, double dDiv) {
    return (lngValue / dDiv);
  }

  public static float Long2Float(long lngValue, float fDiv) {
    return ((float)lngValue / fDiv);
  }

  public static String toHexString(byte[] btArray, String strDelimer) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < btArray.length; ++i)
    {
      sb.append(StringUtils.align(Integer.toHexString(btArray[i]), false, '0', 2)).append(strDelimer);
    }
    return sb.toString();
  }

  public static void main(String[] params) {
    double a = 386.71501220703101D;
    System.out.println("a:" + do4Down5Up(a, 2));
    float b = 386.70001F;
    System.out.println("b:" + do4Down5Up(b, 2));
    System.out.println("a:" + (Math.round(a * 100.0D) / 100.0D));
  }
}
