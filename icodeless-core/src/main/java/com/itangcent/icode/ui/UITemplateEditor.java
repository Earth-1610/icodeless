package com.itangcent.icode.ui;

import com.itangcent.icode.api.AbstractTemplateEditor;
import com.itangcent.icode.api.EditableTemplate;

import java.awt.*;

public abstract class UITemplateEditor extends AbstractTemplateEditor {

    public UITemplateEditor(EditableTemplate template) {
        super(template);
    }

    public abstract Component createUI();
}
