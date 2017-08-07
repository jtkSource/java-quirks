package com.jtk.cls;

import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;

/**
 * Created by jubin on 5/10/2017.
 */
public class ClassLoader101 {
    public static void main(String[] args) {
        System.out.println("class loader for HashMap: "
                + java.util.HashMap.class.getClassLoader());
        System.out.println("class loader for DNSNameService: "
                + sun.net.spi.nameservice.dns.DNSNameService.class
                .getClassLoader());
        System.out.println("class loader for ClassLoader101 class: "
                + ClassLoader101.class.getClassLoader());
        System.out.println(PublicSuffixPatterns.class.getClassLoader());

    }
}
