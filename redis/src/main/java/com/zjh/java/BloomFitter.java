package com.zjh.java;

import io.rebloom.client.Client;

public class BloomFitter {
    public static void main(String[] args) {
        Client client = new Client("124.220.200.142", 6380);
//        for (int i = 0; i < 100000; i++) {
//            client.add("name","zjh-"+i);
//        }
        boolean exists = client.exists("name", "zjh-9999");
        System.out.println(exists);
    }
}
