/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jhona
 */
public class HashHelper {
    private static MessageDigest md;
       
    public static String criptografarSenha(String senha) throws NoSuchAlgorithmException{
        md = MessageDigest.getInstance("SHA-1");
        md.update( senha.getBytes() );
        byte[] hash = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
             if ((0xff & hash[i]) < 0x10)
                  hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
             else
              hexString.append(Integer.toHexString(0xFF & hash[i]));
        }
        return hexString.toString();  
    }
}
