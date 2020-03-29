package com.itangcent.icode.api;

import java.util.LinkedHashMap;

public class BaseTemplateContext implements TemplateContext {
    private Template template;
    private TemplateContext parent;

    private LinkedHashMap<String, Object> attrs = new LinkedHashMap<>();

    private BaseTemplateContext(Template template, TemplateContext parent) {
        this.template = template;
        this.parent = parent;
    }

    @Override
    public Template template() {
        return template;
    }

    @Override
    public TemplateContext parent() {
        return parent;
    }

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

    @Override
    public BaseTemplateContext subContext(Template template) {
        return new BaseTemplateContext(template, this);
    }

    public static BaseTemplateContext of(Template template) {
        return new BaseTemplateContext(template, null);
    }

    public static BaseTemplateContext empty() {
        return new BaseTemplateContext(null, null);
    }

}
