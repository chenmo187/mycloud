package com.mycloud.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.mycloud.net.HttpClient;
import com.mycloud.net.URLConfig;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author clarechen 2014-10-28 常用工具类
 */
public class Utils {
    private static Context context;
    private static ProgressDialog mProgressDialog;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("SimpleDateFormat")
    public static String tranTime(Long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return format.format(c.getTimeInMillis());
    }

    public static boolean isPhoneNumber(String mobiles) {
        Pattern p = Pattern.compile("^(1)\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String formatByte(long data) {
        DecimalFormat format = new DecimalFormat("##.##");
        if (data < 1024) {
            return data + "bytes";
        } else if (data < 1024 * 1024) {
            return format.format(data / 1024f) + "KB";
        } else if (data < 1024 * 1024 * 1024) {
            return format.format(data / 1024f / 1024f) + "MB";
        } else if (data < 1024 * 1024 * 1024 * 1024) {
            return format.format(data / 1024f / 1024f / 1024f) + "GB";
        } else {
            return "超出统计返回";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseTime(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("MM-dd HH:mm");
        Calendar c = Calendar.getInstance();

        try {
            c.setTimeInMillis(Long.parseLong(time));
            return formater.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMEI() {
        return ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }


    public static String getOs() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String parseCheckSum(String sms) {
        Pattern pattern = Pattern.compile("(.*)(\\d{5})(.*)");
        Matcher matcher = pattern.matcher(sms);
        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            return "";
        }

    }

    public static String getSignature(String pkgname) {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo;
        Signature[] signatures;
        StringBuilder builder = new StringBuilder();

        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            packageInfo = manager.getPackageInfo(pkgname,
                    PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            for (Signature signature : signatures) {
                builder.append(signature.toCharsString());
            }
            /************** 得到应用签名 **************/
            return builder.toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void init(Context arg0) {
        context = arg0;
    }

    public static void renameFile(File file) {
        if (file.exists()) {
            String newName = file.getName() + ".apk";
            String path = file.getParent();
            File newFile = new File(path + "/" + newName);
            file.renameTo(newFile);
        }
    }

    public static void renameRingFile(File file) {
        if (file.exists()) {
            String newName = file.getName() + ".mp3";
            String path = file.getParent();
            File newFile = new File(path + "/" + newName);
            file.renameTo(newFile);
        }
    }

    public static String getModel() {
        return Build.MANUFACTURER + "  " + Build.MODEL;
    }


    public static String parseLong(String s) {
        if (s.contains(".")) {
            try {
                double d = Double.parseDouble(s);
                BigDecimal b = new BigDecimal(d);
                d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return String.valueOf(d);
            } catch (Exception e) {
                return s;
            }
        } else {
            return s;
        }
    }


    public static int parseInt(String s) {
        try {
            if (s.contains(".")) {
                int a = s.length() - s.indexOf(".");
                if (a == 3) {
                    return Integer.parseInt(s.replace(".", ""));
                } else if (a == 2) {
                    return Integer.parseInt(s.replace(".", "")) * 10;
                }
            } else {
                return Integer.parseInt(s) * 100;
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static String parseBankNumber(String number) {
        if (number.length() < 9) {
            return number;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(number.substring(0, 4) + " ");
            sb.append("****");
            sb.append(" ****");
            sb.append(" " + number.substring(number.length() - 4, number.length()));
            return sb.toString();
        }
    }


    public static float parseLong_round_halfup(float f) {
        try {
            BigDecimal b = new BigDecimal(f);
            f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            return f;
        } catch (Exception e) {
            return 0.0f;
        }

    }

    public static String parseImgUrl(String url) {
        if (url != null && !TextUtils.isEmpty(url.trim())) {

            if (url.startsWith("http")) {
                return url;
            } else {
                url = url.replace("\\", "/");

                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                return URLConfig.BASEURL_PIC + url;
            }
        } else {
            return "";
        }
    }

    public static String parseMoney(String money) {
        StringBuilder sb = new StringBuilder();
        try {
            if (money.length() > 2) {
                sb.append(money.substring(0, money.length() - 2));
                sb.append(".");
                sb.append(money.substring(money.length() - 2, money.length()));

            } else if (money.length() == 2) {
                sb.append("0.");
                sb.append(money);
            } else if (money.length() == 1) {
                sb.append("0.0");
                sb.append(money);
            }
            return sb.toString();

        } catch (Exception e) {
        }
        return "0.00";
    }

    public static String parseMoney2(String s) {
        if (s.contains(".")) {
            return s.substring(0, s.indexOf("."));
        }
        return s;
    }

    public static void deleteFile(File f) {
        if (f.isDirectory()) {
            File[] file = f.listFiles();
            for (File file2 : file) {
                deleteFile(file2);
                file2.delete();
            }
        } else {
            f.delete();
        }
        f.delete();
    }


    public static void deleteFileExecptSelf(File f) {
        if (f != null && f.isDirectory()) {
            File[] file = f.listFiles();
            for (File file2 : file) {
                deleteFile(file2);
                file2.delete();
            }
        }

    }

    /**
     * 时间分秒格式
     *
     * @param timestamp
     * @return
     */
    public static String formatMMSS(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("m:ss", Locale.CHINA);
        return sdf.format(new Date(timestamp));
    }


    public static String getPath(Path path) {
        if (path == Path.picture) {
            return Environment.getExternalStorageDirectory() + "/StoreManagerCommunicator/picture/";
        } else if (path == Path.cache) {
            return Environment.getExternalStorageDirectory() + "/StoreManagerCommunicator/cache/";
        }
        return null;
    }

    public enum Path

    {
        picture, cache,
    }


    /**
     * 产生四位随机数
     *
     * @return
     */
    public static String generateFourFigures() {
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(ran.nextInt(10));
        sb.append(ran.nextInt(10));
        sb.append(ran.nextInt(10));
        sb.append(ran.nextInt(10));
        return sb.toString();
    }


    public static String getVersion(Activity activity) {
        try {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static boolean stringIsEmpty(String str) {
        if (!TextUtils.isEmpty(str) || !"null".equals(String.valueOf(str))) {
            return false;
        }
        return true;
    }

    public static String getStringMaybeNull(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(String.valueOf(str))) {
            return "";
        }
        return str;
    }

    public static String changeStringIfNull(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(String.valueOf(str))) {
            return "未填写";
        }
        return str;
    }

}
