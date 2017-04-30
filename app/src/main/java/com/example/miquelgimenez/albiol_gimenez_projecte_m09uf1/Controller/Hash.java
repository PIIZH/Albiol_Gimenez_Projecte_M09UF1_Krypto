package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

import javax.crypto.SecretKey;


public interface Hash {
    SecretKey hashBuildKey(String pass);
}
