package com.codename1.app.backend.service.dao;

public class RestaurantRequest {
    private String id;
    private String name;
    private String tagline;
    private String merchantId;
    private String publicKey;
    private String privateKey;
    private String restaurantEmail;
    private String packageName;
    private String appName;

    private double latitude;
    private double longitude;
    private String navigationAddress;
    private String address;
    private String phone;
    private String website;
    private String currency;
    private double minimumOrder;
    private double shippingRangeKM;
    private double deliveryExtraCost;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * @param tagline the tagline to set
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId the merchantId to set
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey the privateKey to set
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return the restaurantEmail
     */
    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    /**
     * @param restaurantEmail the restaurantEmail to set
     */
    public void setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the navigationAddress
     */
    public String getNavigationAddress() {
        return navigationAddress;
    }

    /**
     * @param navigationAddress the navigationAddress to set
     */
    public void setNavigationAddress(String navigationAddress) {
        this.navigationAddress = navigationAddress;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the minimumOrder
     */
    public double getMinimumOrder() {
        return minimumOrder;
    }

    /**
     * @param minimumOrder the minimumOrder to set
     */
    public void setMinimumOrder(double minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    /**
     * @return the shippingRangeKM
     */
    public double getShippingRangeKM() {
        return shippingRangeKM;
    }

    /**
     * @param shippingRangeKM the shippingRangeKM to set
     */
    public void setShippingRangeKM(double shippingRangeKM) {
        this.shippingRangeKM = shippingRangeKM;
    }

    /**
     * @return the deliveryExtraCost
     */
    public double getDeliveryExtraCost() {
        return deliveryExtraCost;
    }

    /**
     * @param deliveryExtraCost the deliveryExtraCost to set
     */
    public void setDeliveryExtraCost(double deliveryExtraCost) {
        this.deliveryExtraCost = deliveryExtraCost;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
