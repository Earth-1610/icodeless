package com.itangcent.icode.api;

import java.util.LinkedHashMap;

public abstract class AbstractTemplateContext implements TemplateContext {

    private LinkedHashMap<String, Object> attrs = new LinkedHashMap<>();

    @Override
    public void setAttr(String attr, String value) {
        if (attrs == null) {
            attrs = new LinkedHashMap<>();
        }
        attrs.put(attr, value);
    }

    @Override
    public void removeAttr(String attr) {
        attrs.remove(attr);
    }

    @Override
    public Object getAttr(String name) {
        if (attrs == null) {
            return null;
        }
        return attrs.get(name);
    }
}
