package com.chris.allinone.designpattern.proxy;

/**
 * @author chrischen
 */
public interface IVisitor {
    void access(WebSite webSite);
    void uploadTo(WebSite webSite);
    String downloadFrom(WebSite webSite);
}
