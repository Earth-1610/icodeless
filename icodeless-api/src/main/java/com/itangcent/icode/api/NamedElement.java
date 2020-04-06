package com.itangcent.icode.api;

public interface NamedElement {

    /**
     * Similar to the id of the element
     */
    String name();

    /**
     * For display to the user.
     * Let the user know what the element represents.
     */
    String displayName();
}
