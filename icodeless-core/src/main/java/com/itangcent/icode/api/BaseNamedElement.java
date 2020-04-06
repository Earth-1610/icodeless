package com.itangcent.icode.api;

public class BaseNamedElement implements NamedElement {

    /**
     * Similar to the id of the element
     */
    private String name;

    /**
     * For display to the user.
     * Let the user know what the element represents.
     */
    private String displayName;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
