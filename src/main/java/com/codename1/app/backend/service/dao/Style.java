package com.codename1.app.backend.service.dao;

public class Style {
    private String uiid;
    private Integer fgColor;
    private Integer bgColor;
    private String font;
    private float fontSize;

    /**
     * @return the uiid
     */
    public String getUiid() {
        return uiid;
    }

    /**
     * @param uiid the uiid to set
     */
    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    /**
     * @return the fgColor
     */
    public Integer getFgColor() {
        return fgColor;
    }

    /**
     * @param fgColor the fgColor to set
     */
    public void setFgColor(Integer fgColor) {
        this.fgColor = fgColor;
    }

    /**
     * @return the bgColor
     */
    public Integer getBgColor() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * @return the fontSize
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
    
}
