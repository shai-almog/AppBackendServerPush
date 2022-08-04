package com.codename1.app.backend.service.dao;

public class StyleSet {
    private String secret;
    private Style[] styles; 

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the styles
     */
    public Style[] getStyles() {
        return styles;
    }

    /**
     * @param styles the styles to set
     */
    public void setStyles(Style[] styles) {
        this.styles = styles;
    }
}
