package com.chris.allinone.designpattern.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chrischen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor implements IVisitor {

    private String name;

    @Override
    public void access(WebSite webSite) {
        System.out.println(this.name + " access " + webSite.getAddress());
    }

    @Override
    public void uploadTo(WebSite webSite) {
        System.out.println("上传成功！");
    }

    @Override
    public String downloadFrom(WebSite webSite) {
        return webSite.provide();
    }
}
