package com.itangcent.icode.api;

public interface TemplateManager {

    default Template findByName(String name) {
        return findByName(name, null);
    }

    Template findByName(String name, TemplateContext context);
}
