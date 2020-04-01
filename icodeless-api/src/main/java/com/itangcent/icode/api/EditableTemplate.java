package com.itangcent.icode.api;

import java.util.List;

public interface EditableTemplate extends Template {

    void setName(String name);

    void setDisplayName(String displayName);

    void setParams(List<TemplateParam> params);

    void setAlwaysCheckBeforeRender(boolean alwaysCheckBeforeRender);

    void setType(String type);

    void setContent(String content);
}
