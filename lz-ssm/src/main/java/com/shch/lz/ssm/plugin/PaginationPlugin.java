package com.shch.lz.ssm.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by Link at 18:42 on 3/30/18.
 */
public class PaginationPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    // add limit,offset to java source
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();
        // add field: int limit
        Field limit = new Field();
        limit.setName("limit");
        limit.setVisibility(JavaVisibility.PRIVATE);
        limit.setType(integerWrapper);
        topLevelClass.addField(limit);
        // add method: setLimit
        Method setLimit = new Method();
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.setName("setLimit");
        setLimit.addParameter(new Parameter(integerWrapper, "limit"));
        setLimit.addBodyLine("this.limit=limit");
        topLevelClass.addMethod(setLimit);
        // add method: getLimit
        Method getLimit = new Method();
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerWrapper);
        getLimit.setName("getLimit");
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);
        // add field: int offset
        Field offset = new Field();
        offset.setName("offset");
        offset.setVisibility(JavaVisibility.PRIVATE);
        offset.setType(integerWrapper);
        topLevelClass.addField(offset);
        // add method: setOffset
        Method setOffset = new Method();
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.setName("setOffset");
        setOffset.addParameter(new Parameter(integerWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);
        // add method: getOffset
        Method getOffset = new Method();
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerWrapper);
        getOffset.setName("getOffset");
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);
        return true;
    }

    // add limit,offset to XML
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // if null == limit
        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "null != limit"));
        // if null != offset
        XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "null != offset"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${offset}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);
        // if null == offset
        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "null == offset"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);

        return true;
    }
}
