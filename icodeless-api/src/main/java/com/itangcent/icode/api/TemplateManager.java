package com.itangcent.icode.api;

public interface TemplateManager {

    /**
     * Find template by name.
     *
     * @param name -template name
     * @return the template which is editable
     */
    default Template findByName(String name) {
        return findByName(name, null);
    }

    /**
     * Find template by name and context.
     *
     * @param name -template name
     * @return the template which is editable
     */
    Template findByName(String name, TemplateContext context);
}
