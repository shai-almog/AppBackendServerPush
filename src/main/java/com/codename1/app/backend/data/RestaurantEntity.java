package com.codename1.app.backend.data;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="Restaurant")
public class RestaurantEntity {

    @Id
    private String id;
    private String secret;
    private String name;
    private String tagline;
    private String merchantId;
    private String publicKey;
    private String privateKey;
    private String packageName;
    private String appName;
    private double currentVersionIOS = 1.0;
    private double lastSupportedVersionIOS = 0;
    private double currentVersionAnd = 1.0;
    private double lastSupportedVersionAnd = 0;
    private double currentVersionUWP = 1.0;
    private double lastSupportedVersionUWP = 0;
    private long dishListUpdateTimestamp = 1;
    private String restaurantEmail;
    private String sendgridKey;
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
    private String lastResult;
    
    @Lob
    private byte[] logo;
    
    @Lob
    private byte[] icon;
    
    @Lob
    private byte[] backgroundImage;

    @OneToMany
    @OrderBy("name ASC")
    private Set<DishEntity> dishes;

    
    @OneToMany
    @OrderBy("name ASC")
    private Set<CategoryEntity> categories;

    @OneToMany
    private Set<StyleEntity> styles;
    
    public RestaurantEntity() {
        id = UUID.randomUUID().toString();
        secret = UUID.randomUUID().toString();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
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
     * @return the categories
     */
    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    /**
     * @return the currentVersionIOS
     */
    public double getCurrentVersionIOS() {
        return currentVersionIOS;
    }

    /**
     * @param currentVersionIOS the currentVersionIOS to set
     */
    public void setCurrentVersionIOS(double currentVersionIOS) {
        this.currentVersionIOS = currentVersionIOS;
    }

    /**
     * @return the lastSupportedVersionIOS
     */
    public double getLastSupportedVersionIOS() {
        return lastSupportedVersionIOS;
    }

    /**
     * @param lastSupportedVersionIOS the lastSupportedVersionIOS to set
     */
    public void setLastSupportedVersionIOS(double lastSupportedVersionIOS) {
        this.lastSupportedVersionIOS = lastSupportedVersionIOS;
    }

    /**
     * @return the currentVersionAnd
     */
    public double getCurrentVersionAnd() {
        return currentVersionAnd;
    }

    /**
     * @param currentVersionAnd the currentVersionAnd to set
     */
    public void setCurrentVersionAnd(double currentVersionAnd) {
        this.currentVersionAnd = currentVersionAnd;
    }

    /**
     * @return the lastSupportedVersionAnd
     */
    public double getLastSupportedVersionAnd() {
        return lastSupportedVersionAnd;
    }

    /**
     * @param lastSupportedVersionAnd the lastSupportedVersionAnd to set
     */
    public void setLastSupportedVersionAnd(double lastSupportedVersionAnd) {
        this.lastSupportedVersionAnd = lastSupportedVersionAnd;
    }

    /**
     * @return the currentVersionUWP
     */
    public double getCurrentVersionUWP() {
        return currentVersionUWP;
    }

    /**
     * @param currentVersionUWP the currentVersionUWP to set
     */
    public void setCurrentVersionUWP(double currentVersionUWP) {
        this.currentVersionUWP = currentVersionUWP;
    }

    /**
     * @return the lastSupportedVersionUWP
     */
    public double getLastSupportedVersionUWP() {
        return lastSupportedVersionUWP;
    }

    /**
     * @param lastSupportedVersionUWP the lastSupportedVersionUWP to set
     */
    public void setLastSupportedVersionUWP(double lastSupportedVersionUWP) {
        this.lastSupportedVersionUWP = lastSupportedVersionUWP;
    }

    /**
     * @return the dishListUpdateTimestamp
     */
    public long getDishListUpdateTimestamp() {
        return dishListUpdateTimestamp;
    }

    /**
     * @param dishListUpdateTimestamp the dishListUpdateTimestamp to set
     */
    public void setDishListUpdateTimestamp(long dishListUpdateTimestamp) {
        this.dishListUpdateTimestamp = dishListUpdateTimestamp;
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
     * @return the dishes
     */
    public Set<DishEntity> getDishes() {
        return dishes;
    }

    /**
     * @param dishes the dishes to set
     */
    public void setDishes(Set<DishEntity> dishes) {
        this.dishes = dishes;
    }

    /**
     * @return the sendgridKey
     */
    public String getSendgridKey() {
        return sendgridKey;
    }

    /**
     * @param sendgridKey the sendgridKey to set
     */
    public void setSendgridKey(String sendgridKey) {
        this.sendgridKey = sendgridKey;
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
     * @return the logo
     */
    public byte[] getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    /**
     * @return the icon
     */
    public byte[] getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    /**
     * @return the backgroundImage
     */
    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * @param backgroundImage the backgroundImage to set
     */
    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

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
     * @return the styles
     */
    public Set<StyleEntity> getStyles() {
        return styles;
    }

    /**
     * @param styles the styles to set
     */
    public void setStyles(Set<StyleEntity> styles) {
        this.styles = styles;
    }

    /**
     * @return the lastResult
     */
    public String getLastResult() {
        return lastResult;
    }

    /**
     * @param lastResult the lastResult to set
     */
    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
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

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

 
   
}