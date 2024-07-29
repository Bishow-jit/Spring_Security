package com.example.SpringSecurity;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretKeyMakerTest {

    @Test
    public void generateKeyMaker(){
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
        System.out.println("Key:"+encodedKey);
    }
}
