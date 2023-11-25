package org.jwn;

import org.jwn.data.DataReceiver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started");
        DataReceiver.downloadBgm();
        System.out.println("BGM Downloaded");
    }
}