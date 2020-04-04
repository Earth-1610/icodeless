package com.itangcent.icode.api;

public interface TemplateContext {

    Template template();

    /**
     * return null if this is root context
     */
    TemplateContext parent();

    void setAttr(String name, String value);

    void removeAttr(String name);

    Object getAttr(String name);

    TemplateContext subContext(Template template);

    static TemplateContext empty() {
        return new EmptyTemplateContext();
    }
}
