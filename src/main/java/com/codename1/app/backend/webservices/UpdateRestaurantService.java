package com.codename1.app.backend.webservices;

import com.codename1.app.backend.data.RestaurantEntity;
import com.codename1.app.backend.data.RestaurantRepository;
import com.codename1.app.backend.service.dao.RestaurantRequest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/updateRestaurant")
public class UpdateRestaurantService {
    @Autowired
    private RestaurantRepository restRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String update(@RequestParam(name="logo", required = false) MultipartFile logo,
            @RequestParam(name="icon", required = false) MultipartFile icon,
            @RequestParam(name="backgroundImage", required = false) MultipartFile backgroundImage,
            @RequestParam(name="secret", required = true) String secret) throws IOException {
        RestaurantEntity e = restRepository.findBySecret(secret);
        if(icon != null) {
            e.setIcon(icon.getBytes());
        }
        if(logo != null) {
            e.setLogo(logo.getBytes());
        }
        if(backgroundImage != null) {
            e.setBackgroundImage(backgroundImage.getBytes());
        }
                
        restRepository.save(e);
        
        return e.getSecret();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody String update(@RequestBody(required = true) RestaurantRequest data) throws IOException {
        RestaurantEntity e;
        if(data.getId() == null) {
            e = new RestaurantEntity();
        } else {
            e = restRepository.findBySecret(data.getId());
        }
        
        e.setMerchantId(data.getMerchantId());
        e.setPrivateKey(data.getPrivateKey());
        e.setPublicKey(data.getPublicKey());
        e.setName(data.getName());
        e.setRestaurantEmail(data.getRestaurantEmail());
        e.setTagline(data.getTagline());
        
        e.setLatitude(data.getLatitude());
        e.setLongitude(data.getLongitude());
        e.setNavigationAddress(data.getNavigationAddress());
        e.setAddress(data.getAddress());
        e.setPhone(data.getPhone());
        e.setWebsite(data.getWebsite());
        e.setCurrency(data.getCurrency());
        e.setMinimumOrder(data.getMinimumOrder());
        e.setShippingRangeKM(data.getShippingRangeKM());
        e.setDeliveryExtraCost(data.getDeliveryExtraCost());
        
        restRepository.save(e);
        
        String sec = e.getSecret();
        return sec;
    }
}