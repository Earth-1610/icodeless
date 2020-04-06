package com.itangcent.icode.api;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DefaultEditableTemplate implements EditableTemplate {

    @Expose(serialize = false, deserialize = false)
    private boolean isAnyModified = false;

    private String content;
    private String type;
    private boolean alwaysCheckBeforeRender;
    private List<TemplateParam> params;
    private String displayName;
    private String name;

    @Override
    public void setName(String name) {
        isAnyModified = true;
        this.name = name;
    }

    @Override
    public void setDisplayName(String displayName) {
        isAnyModified = true;
        this.displayName = displayName;
    }

    @Override
    public void setParams(List<TemplateParam> params) {
        isAnyModified = true;
        this.params = params;
    }

    @Override
    public void setAlwaysCheckBeforeRender(boolean alwaysCheckBeforeRender) {
        isAnyModified = true;
        this.alwaysCheckBeforeRender = alwaysCheckBeforeRender;
    }

    @Override
    public void setType(String type) {
        isAnyModified = true;
        this.type = type;
    }

    @Override
    public void setContent(String content) {
        isAnyModified = true;
        this.content = content;
    }

    @Override
    public boolean isAnyModified() {
        return isAnyModified;
    }

    @Override
    public List<TemplateParam> params() {
        return params;
    }

    @Override
    public boolean alwaysCheckBeforeRender() {
        return alwaysCheckBeforeRender;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String displayName() {
        return displayName;
    }
}
