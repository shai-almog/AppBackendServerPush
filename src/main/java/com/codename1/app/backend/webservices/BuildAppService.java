package com.codename1.app.backend.webservices;

import com.codename1.app.backend.data.RestaurantEntity;
import com.codename1.app.backend.data.RestaurantRepository;
import com.codename1.app.backend.service.BuildAppAsync;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doBuildApp")
public class BuildAppService {
    @Autowired
    private BuildAppAsync buildAsync;

    @Autowired
    private RestaurantRepository restRepo;
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String build(@RequestParam(name = "secret", required = true) String secret, 
            @RequestParam(name = "pushKey", required = false) String pushKey,
            @RequestParam(name = "targetType", required = true) String targetType) {
        buildAsync.buildApp(secret, pushKey, targetType);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String get(@RequestParam(name = "secret", required = true) String secret) {
        RestaurantEntity re = restRepo.findBySecret(secret);
        return re.getLastResult();
    }

}
