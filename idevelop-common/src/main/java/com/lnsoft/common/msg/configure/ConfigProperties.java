package com.lnsoft.common.msg.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("msg")
public class ConfigProperties   {

    private String url;

    private String contentType;

    private String account;

    private String password;

    private String title;

    public String getAccount() {
        return account;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

