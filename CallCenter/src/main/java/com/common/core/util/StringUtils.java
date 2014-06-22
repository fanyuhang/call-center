package com.common.core.util;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class StringUtils {
    public static final int ACTION_EMPTY_AS_NULL = 1;
    public static final int ACTION_TRIM = 2;
    public static final int ACTION_REPLACE_RETURN_AS_BLANK = 3;

    public static String perform(String strObj, int intMethod) {
        if (strObj == null) {
            return null;
        }
        if (((intMethod & 0x1) > 0) &&
                (strObj != null) && (strObj.trim().equals(""))) {
            return null;
        }

        if ((intMethod & 0x2) > 0) {
            strObj = strObj.trim();
        }

        if (((intMethod & 0x3) > 0) &&
                (strObj != null)) {
            StringBuffer sb = new StringBuffer(strObj.trim());
            for (int i = 0; i < sb.length(); ++i) {
                if (('\n' == sb.charAt(i)) || ('\r' == sb.charAt(i))) {
                    sb.setCharAt(i, ' ');
                }
            }
            strObj = sb.toString();
        }

        return strObj;
    }

    public static String Int2String(int nValue, int nBit) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nBit; ++i) {
            sb.append('0');
        }
        sb.append(nValue);

        return sb.substring(sb.length() - nBit);
    }

    public static boolean isCharInASCII(String str) {
        int length = str.length();
        char[] charArray = new char[length];
        str.getChars(0, length, charArray, 0);

        for (int i = 0; i < length; ++i) {
            if ((charArray[i] < '0') || (charArray[i] > 'z')) {
                return false;
            }
        }
        return true;
    }

    public static String Long2String(long lnValue, int nBit) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nBit; ++i) {
            sb.append('0');
        }
        sb.append(lnValue);

        return sb.substring(sb.length() - nBit);
    }

    public static String getParameter(String strParam) {
        String strRet = strParam;
        if (strRet == null)
            return null;
        try {
            strRet = new String(strRet.getBytes("8859_1"));
        } catch (UnsupportedEncodingException ex) {
            return null;
        }

        return strRet;
    }

    public static String genRandomStr(int bit) {
        StringBuffer sbResult = new StringBuffer();
        Random rand =
                new Random(
                        Calendar.getInstance().getTime().getTime());
        byte[] byteArray = new byte[bit];
        rand.nextBytes(byteArray);

        for (int i = 0; i < bit; ++i) {
            int temp;
            byte sect = (byte) Math.abs(rand.nextInt() % 3);

            switch (sect) {
                case 0:
                    temp = Math.abs(byteArray[i]) % 10 + 48;
                    sbResult.append((char) temp);
                    break;
                case 1:
                    temp = Math.abs(byteArray[i]) % 26 + 65;
                    sbResult.append((char) temp);
                    break;
                case 2:
                    temp = Math.abs(byteArray[i]) % 26 + 97;
                    sbResult.append((char) temp);
            }
        }

        return sbResult.toString();
    }

    public static String genRandomStr(int bit, int type) {
        int temp;
        int i;
        StringBuffer sbResult = new StringBuffer();
        Random rand =
                new Random(
                        Calendar.getInstance().getTime().getTime());
        byte[] byteArray = new byte[bit];
        rand.nextBytes(byteArray);

        switch (type) {
            case 0:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i]) % 10 + 48;
                    sbResult.append((char) temp);
                }
                break;
            case 1:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i]) % 26 + 65;
                    sbResult.append((char) temp);
                }
                break;
            case 2:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i]) % 26 + 97;
                    sbResult.append((char) temp);
                }
            case 3:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i]) % 16;
                    if (temp < 10) temp += 48;
                    else temp += 55;
                    sbResult.append((char) temp);
                }

        }

        return sbResult.toString();
    }

    public static String genRandomStr(int bit, int type, int nFSData) {
        int temp;
        int i;
        StringBuffer sbResult = new StringBuffer();

        Random rand =
                new Random(
                        Calendar.getInstance().getTime().getTime());
        byte[] byteArray = new byte[bit];
        rand.nextBytes(byteArray);

        switch (type) {
            case 0:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ nFSData) % 10 + 48;
                    sbResult.append((char) temp);
                }
                break;
            case 1:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ nFSData) % 26 + 65;
                    sbResult.append((char) temp);
                }
                break;
            case 2:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ nFSData) % 26 + 97;
                    sbResult.append((char) temp);
                }
            case 3:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ nFSData) % 16;
                    if (temp < 10) temp += 48;
                    else temp += 55;
                    sbResult.append((char) temp);
                }

        }

        return sbResult.toString();
    }

    public static String genRandomStr(int bit, int type, String strFSData) {
        int temp;
        int i;
        StringBuffer sbResult = new StringBuffer();
        sbResult.append(strFSData);
        while (sbResult.length() < bit) {
            sbResult.append(strFSData);
        }
        byte[] bytesFSData = sbResult.toString().getBytes();
        sbResult.setLength(0);

        Random rand =
                new Random(
                        Calendar.getInstance().getTime().getTime());
        byte[] byteArray = new byte[bit];
        rand.nextBytes(byteArray);

        switch (type) {
            case 0:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ bytesFSData[i]) % 10 + 48;
                    sbResult.append((char) temp);
                }
                break;
            case 1:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ bytesFSData[i]) % 26 + 65;
                    sbResult.append((char) temp);
                }
                break;
            case 2:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ bytesFSData[i]) % 26 + 97;
                    sbResult.append((char) temp);
                }
            case 3:
                for (i = 0; i < bit; ++i) {
                    temp = Math.abs(byteArray[i] ^ bytesFSData[i]) % 16;
                    if (temp < 10) temp += 48;
                    else temp += 55;
                    sbResult.append((char) temp);
                }

        }

        return sbResult.toString();
    }

    public static String substring(String strSub, int start, int end, String strDefault) {
        if ((strSub == null) || (start < 0) || (end < start)) {
            return strDefault;
        }

        if (end > strSub.length()) {
            end = strSub.length();
        }

        return strSub.substring(start, end);
    }

    public static String formatLongStr(String strValue, long lngAdd, int nBit)
            throws NumberFormatException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nBit; ++i) {
            sb.append('0');
        }
        long lnNo = Long.parseLong(strValue) + lngAdd;
        sb.append(lnNo);
        int length = sb.length();
        return sb.substring(length - nBit, length);
    }

    public static String align(String strValue, boolean isAlignLeft, char cInsert, int nBit)
            throws NumberFormatException {
        int i;
        StringBuffer sb = new StringBuffer();
        if (isAlignLeft) {
            sb.append(strValue);
            for (i = 0; i < nBit; ++i) {
                sb.append(cInsert);
            }
        } else {
            for (i = 0; i < nBit; ++i) {
                sb.append(cInsert);
            }
            sb.append(strValue);
        }

        int length = sb.length();
        return sb.substring(length - nBit, length);
    }
    
    public static Date getTodayDate(){
    	Calendar cal = Calendar.getInstance();
		StringBuilder builder = new StringBuilder();
		builder.append(cal.get(Calendar.YEAR)).append("-");
		builder.append((cal.get(Calendar.MONTH)+1)).append("-");
		builder.append(cal.get(Calendar.DAY_OF_MONTH));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date date = dateFormat.parse(builder.toString());
			return date;
		}catch(Exception e){
			return new Date();
		}
    }
}