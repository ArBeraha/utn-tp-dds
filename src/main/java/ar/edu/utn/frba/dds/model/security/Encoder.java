package ar.edu.utn.frba.dds.model.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {

    private static Encoder instance;
    private static BCryptPasswordEncoder bCryptEncoder;

    //Singleton
    public static Encoder getInstance() {
        if (instance == null) {
            instance = new Encoder();
        }
        return instance;
    }

    private Encoder() {
        bCryptEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String cadena) {
        return bCryptEncoder.encode(cadena);
    }

    public BCryptPasswordEncoder getbCryptEncoder() {
        return bCryptEncoder;
    }
}
