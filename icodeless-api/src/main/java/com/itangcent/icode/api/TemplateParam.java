package com.itangcent.icode.api;

public interface TemplateParam extends NamedElement {

    String type();

    String defaultValue();

    /**
     * return true if this param is not required;
     * By default ,it will be false
     *
     * @return {@code true} if an only if this parameter is not required.
     */
    default boolean optional() {
        String defaultValue = defaultValue();
        return defaultValue() != null && !defaultValue.isEmpty();
    }

    /**
     * Returns {@code true} if this parameter represents a variable
     * argument list; returns {@code false} otherwise.
     *
     * @return {@code true} if an only if this parameter represents a
     * variable argument list.
     */
    default boolean isVarargs() {
        return false;
    }
}
