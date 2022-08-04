package com.codename1.app.backend.webservices;

import com.codename1.app.backend.data.CategoryEntity;
import com.codename1.app.backend.data.CategoryRepository;
import com.codename1.app.backend.data.DishEntity;
import com.codename1.app.backend.data.DishRepository;
import com.codename1.app.backend.data.RestaurantEntity;
import com.codename1.app.backend.data.RestaurantRepository;
import com.codename1.app.backend.data.StyleEntity;
import com.codename1.app.backend.data.StyleRepository;
import com.codename1.app.backend.service.dao.AppVersion;
import com.codename1.app.backend.service.dao.Dish;
import com.codename1.app.backend.service.dao.DishContainer;
import com.codename1.app.backend.service.dao.RestaurantRequest;
import com.codename1.app.backend.service.dao.Style;
import com.codename1.app.backend.service.dao.StyleSet;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/style")
public class StyleService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private StyleRepository styleRepository;

    
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody String update(@RequestBody(required = true) StyleSet styles) {
        RestaurantEntity e = restaurantRepository.findBySecret(styles.getSecret());

        Set<StyleEntity> stylesSet = e.getStyles();
        Set<StyleEntity> newStylesSet = new HashSet<>();
        e.setStyles(newStylesSet);
        
        styleRepository.delete(stylesSet);
        
        for(Style currentStyle : styles.getStyles()) {
            StyleEntity ent = new StyleEntity();
            ent.setUiid(currentStyle.getUiid());
            ent.setBgColor(currentStyle.getBgColor());
            ent.setFgColor(currentStyle.getFgColor());
            ent.setFont(currentStyle.getFont());
            ent.setFontSize(currentStyle.getFontSize());
            styleRepository.save(ent);
            newStylesSet.add(ent);
        }
        e.setStyles(newStylesSet);
        restaurantRepository.save(e);
        
        return "OK";
    }
    
    
}