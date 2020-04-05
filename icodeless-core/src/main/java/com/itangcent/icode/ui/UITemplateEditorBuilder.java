package com.itangcent.icode.ui;

import com.itangcent.icode.api.EditableTemplate;
import com.itangcent.icode.api.TemplateEditor;
import com.itangcent.icode.api.TemplateEditorBuilder;

import java.awt.*;

public abstract class UITemplateEditorBuilder implements TemplateEditorBuilder {

    /**
     * The parent container for rendering the template editor
     */
    private UIContainer parentContainer;

    public UITemplateEditorBuilder(Container parentContainer) {
        this.parentContainer = UIContainer.of(parentContainer);
    }

    @Override
    public TemplateEditor createEditor(EditableTemplate template) {
        UITemplateEditor uiTemplateEditor = buildEditor(template);
        addEditorUIToParent(uiTemplateEditor.createUI());
        return uiTemplateEditor;
    }

    protected void addEditorUIToParent(Component editorUI) {
        parentContainer.clear();
        parentContainer.add(editorUI);
    }

    protected abstract UITemplateEditor buildEditor(EditableTemplate template);
}
