package com.zts.jzyy.app.sqlserver;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface ScriptGenerator {

    /**
     * 具体生成sql语句的方法
     * @param oldBusiCode 一柜通业务代码
     * @param newBusiCode app业务代码
     * @param template
     * @return
     */
    String generate(String oldBusiCode, String newBusiCode, JdbcTemplate template);

    /**
     * 生成各业务脚本方法
     */
    void generateScript() throws FileNotFoundException, UnsupportedEncodingException;

}
