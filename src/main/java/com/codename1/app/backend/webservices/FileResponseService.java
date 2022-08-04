package com.codename1.app.backend.webservices;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/files")
public class FileResponseService {
    public static final File FILES_DIRECTORY = new File(System.getProperty("user.home") + File.separator + "files");
    
    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("file_name") String fileName,
            HttpServletResponse response) throws IOException {
        for(int iter = 0 ; iter < fileName.length() ; iter++) {
            char c = fileName.charAt(iter);
            if(c >= 'a' && c <= 'z' || 
                    c >= '0' && c <= '9' ||
                    c == '.' || c == '-' || c == '_') {
                continue;
            }
            // illegal character that might compromize security
            response.setStatus(404);
            return;
        }
        File f = new File(FILES_DIRECTORY, fileName +".zip");
        
        if(f.exists()) {
            response.setStatus(200);
            try(OutputStream os = response.getOutputStream()) {
                Files.copy(f.toPath(), os);
            }
        } else {
            response.setStatus(404);
        }
            
    }
}
