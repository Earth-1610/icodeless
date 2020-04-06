package com.itangcent.icode.sqlite;

import com.itangcent.icode.api.*;
import com.itangcent.icode.utils.GsonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SqliteTemplateManager extends IndexedEditableTemplateManager {

    private SqliteDataResourceHelper sqliteDataResourceHelper = new SqliteDataResourceHelper();

    private SqliteDataResourceHelper.SimpleBeanDAO dao = sqliteDataResourceHelper.getSimpleBeanDAO(dbPath(), "sqliteTemplate");

    private String dbPath() {
        String home = SystemUtils.getUserHome().getPath();
        if (home.endsWith(File.separator)) {
            home = home.substring(0, home.length() - 1);
        }
        return home + "/.icodeless/.template.v.1.0.db";
    }

    @Override
    public List<NamedElement> allTemplateNames() {
        SqliteDataResourceHelper.NameAndTiny[] nameAndTinies = dao.listNameAndTiny();
        if (ArrayUtils.isEmpty(nameAndTinies)) return Collections.emptyList();
        ArrayList<NamedElement> namedElements = new ArrayList<>(nameAndTinies.length);
        for (SqliteDataResourceHelper.NameAndTiny nameAndTiny : nameAndTinies) {
            BaseNamedElement baseNamedElement = new BaseNamedElement();
            baseNamedElement.setName(Optional.ofNullable(nameAndTiny.getName())
                    .map(it -> {
                        try {
                            return new String(it, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            return "unnamed";
                        }
                    })
                    .orElse("unnamed"));
            baseNamedElement.setDisplayName(Optional.ofNullable(nameAndTiny.getTiny())
                    .map(it -> {
                        try {
                            return new String(it, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            return "unnamed";
                        }
                    })
                    .orElse("unnamed"));
            namedElements.add(baseNamedElement);
        }
        return namedElements;
    }

    @Override
    protected EditableTemplate doFindByName(String name, TemplateContext context) {
        byte[] content = dao.get(name.getBytes());
        if (content == null) return null;
        String json;
        try {
            json = new String(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //todo:
            return null;
        }
        return GsonUtils.INSTANCE.fromJson(json, DefaultEditableTemplate.class);
    }

    @Override
    protected void doSave(List<EditableTemplate> editableTemplates) {
        for (EditableTemplate editableTemplate : editableTemplates) {
            dao.set(editableTemplate.name().getBytes(),
                    editableTemplate.displayName().getBytes(),
                    GsonUtils.INSTANCE.toJson(editableTemplate).getBytes()
            );
        }
    }
}
