package com.tls.sigcheck;

import com.util.PropertyUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Administrator on 2017/4/24.
 */
public class TLSUtil {
    private static final String tlsSigApi ;
    static {
        PropertyUtils.load("config/property/file.properties");
        tlsSigApi = PropertyUtils.getProperty("tlsSigApi");
    }



    public static String getSig(String identifier)throws Exception{
        tls_sigcheck demo = new tls_sigcheck();
        demo.loadJniLib(tlsSigApi+"lib"+File.separator+"jni"+File.separator+"jnisigcheck.dll");//jnisigcheck.so
        File priKeyFile = new File(tlsSigApi+"keys"+File.separator+"ec_key.pem");
        StringBuilder strBuilder = new StringBuilder();
        String s = "";

        BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String priKey = strBuilder.toString();
        int ret = demo.tls_gen_signature_ex2("1400029072", identifier, priKey);

        if (0 != ret) {
            System.out.println("ret " + ret + " " + demo.getErrMsg());
            return "";
        }
        File pubKeyFile = new File(tlsSigApi+"keys/public.pem");
        br = new BufferedReader(new FileReader(pubKeyFile));
        strBuilder.setLength(0);
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String pubKey = strBuilder.toString();
        ret = demo.tls_check_signature_ex2(demo.getSig(), pubKey, "1400029072", identifier);
        if (0 != ret) {
            System.out.println("ret " + ret + " " + demo.getErrMsg());
        }
        else
        {
            System.out.println("--\nverify ok -- expire time " + demo.getExpireTime() + " -- init time " + demo.getInitTime());
        }
        System.out.println("sig=="+demo.getSig());
        return demo.getSig();

    }
    public static void main(String args[]) throws Exception {
        System.out.println(TLSUtil.getSig("57"));
    }
}
