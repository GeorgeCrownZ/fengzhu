package com.soft.web.utils;

import com.soft.web.entity.PwdData;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public PwdData encryptPassword(PwdData pwdData) {
        if(pwdData.getSalt()==null){
            pwdData.setSalt(randomNumberGenerator.nextBytes().toHex());
        }
        String newPass = new SimpleHash(algorithmName, pwdData.getPassword(),
                ByteSource.Util.bytes(pwdData.getUsername() + pwdData.getSalt()), hashIterations).toHex();
        PwdData pwdData1=new PwdData();
        pwdData1.setUsername(pwdData.getUsername());
        pwdData1.setPassword(newPass);
        pwdData1.setSalt(pwdData.getSalt());

//        System.out.println(newPass);
//        System.out.println(salt);
        return pwdData1;
    }

    /**
     * lolin密码核验
     * @param pwdData
     * @param password
     * @return
     */
    public boolean passwordCheck(PwdData pwdData,String password) {
        String newPass = new SimpleHash(algorithmName, password,
                ByteSource.Util.bytes(pwdData.getUsername() + pwdData.getSalt()), hashIterations).toHex();
//        System.out.println(databasePassword);
        if(pwdData.getPassword().equals(newPass)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PwdData pwdData=new PwdData();
        pwdData.setUsername("bbb");
        pwdData.setPassword("bbb");
        pwdData.setSalt("6c73deaadfb5384effbbd6df068e62ab");
        PasswordHelper passwordHelper = new PasswordHelper();
//        System.out.println( "old password:132d0e9a99a4c3d384d00e0276338136");
        PwdData pwdData1 =  passwordHelper.encryptPassword(pwdData);
        System.out.println(pwdData.getPassword() + "  " +  pwdData1.getPassword() + " " + pwdData1.getSalt());
    }
}
