package com.chris.allinone.designpattern.proxy;

/**
 * 由代理对象访问目标对象
 */
public class Proxy implements IVisitor {

    private final IVisitor visitor;

    public Proxy(String name) {
        this.visitor = new Visitor(name);
    }

    @Override
    public void access(WebSite webSite) {
        visitor.access(webSite);
    }

    @Override
    public void uploadTo(WebSite webSite) {
        visitor.uploadTo(webSite);
    }

    @Override
    public String downloadFrom(WebSite webSite) {
        String res = "";
        if (visitor != null) {
            res = visitor.downloadFrom(webSite);
        }
        return res;
    }
}
