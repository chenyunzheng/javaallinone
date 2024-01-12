package com.chris.allinone.designpattern.proxy;

public class Main {

    public static void main(String[] args) {
        // none proxy
        WebSite webSite = new WebSite();
        Visitor visitor = new Visitor("chris");
        visitor.access(webSite);

        // with proxy
        Proxy proxy = new Proxy("chris");
        proxy.access(webSite);
        proxy.uploadTo(webSite);
        System.out.println(proxy.downloadFrom(webSite));
    }
}
