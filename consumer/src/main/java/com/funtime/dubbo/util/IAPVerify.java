package com.funtime.dubbo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by steven on 2018/10/2.
 */
public class IAPVerify {
    public static Map getSecondaryVerify(String data, String tp) throws Exception {
        Map verifyMap = new HashMap();
        if (data != null  && data.equals("") &&  null !=tp && !tp.equals("")) {
            URL dataUrl = new URL("https://buy.itunes.apple.com/verifyReceipt");
            if(tp.equals("t")){
                dataUrl = new URL("https://sandbox.itunes.apple.com/verifyReceipt");
            }

            HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "text/json");
            con.setRequestProperty("Proxy-Connection", "Keep-Alive");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + data + "\"}");
            System.out.println(str);
            out.write(str);
            out.flush();
            out.close();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line + "\r\n";
            }
            org.json.JSONObject j;
            try {
                j = new org.json.JSONObject(result);
                String returnresult = j.get("status").toString();
                if (returnresult.equals("0")) {
                    verifyMap.put("status", 0);
                    verifyMap.put("receipt", j.get("receipt"));
                }else if (returnresult.equals("21002")) {
                    verifyMap.put("status", -6L);
                    verifyMap.put("receipt", j.get("receipt"));
                } else {
                    verifyMap.put("status", Long.valueOf(returnresult));
                    verifyMap.put("receipt", j.get("receipt"));
                }

                return verifyMap;
            } catch (Exception e) {
                System.out.println("接收返回类型:" + e.getMessage());
            }
        }
        return null;
    }
}