package com.itangcent.icode.api;

import java.util.List;

public interface Template extends NamedElement {

    List<TemplateParam> params();

    /**
     * Always try to let the developer confirm param values,
     * even if the  all parameters of the template are provided by caller,
     * By default it will be false.
     */
    default boolean alwaysCheckBeforeRender() {
        return false;
    }

    String type();

    String content();
}
