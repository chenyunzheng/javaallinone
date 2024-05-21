package com.andrew.vapp;


import com.andrew.vsb.VSpringApplication;
import com.andrew.vsb.annotation.VSpringBootApplication;

/**
 * @author chrischen
 */
@VSpringBootApplication
public class VApp {

    public static void main(String[] args) {
        VSpringApplication.run(VApp.class);
    }
}
