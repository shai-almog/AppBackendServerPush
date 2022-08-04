package com.codename1.app.backend.service;

import com.codename1.app.backend.data.RestaurantEntity;
import com.codename1.app.backend.data.RestaurantRepository;
import com.codename1.app.backend.data.StyleEntity;
import com.codename1.app.backend.webservices.FileResponseService;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.UUID;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class BuildAppAsync {
    @Autowired
    private RestaurantRepository restRepository;

    private final static Logger logger = LoggerFactory.getLogger(BuildAppAsync.class);

    private static final String ITUNES_DEVELOPMENT_PUSH_CERT;
    private static final String ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD;
    private static final boolean ITUNES_PRODUCTION_PUSH;
    private static final String ITUNES_PRODUCTION_PUSH_CERT;
    private static final String ITUNES_PRODUCTION_PUSH_CERT_PASSWORD;
    private static final String PUSH_TOKEN;
    private static final String GCM_SERVER_API_KEY;
    
    static {
        Properties prop = new Properties();
        try(InputStream is = new FileInputStream(System.getProperty("user.home") + "/rest-builder.properties")) {
            prop.load(is);
        } catch(IOException err) {
            logger.error("rest-builder.properties not found in home directory!", err);
        }
        ITUNES_DEVELOPMENT_PUSH_CERT = prop.getProperty("ITUNES_DEVELOPMENT_PUSH_CERT");
        ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD = prop.getProperty("ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD");
        ITUNES_PRODUCTION_PUSH = prop.getProperty("ITUNES_PRODUCTION_PUSH", "false").equalsIgnoreCase("true");
        ITUNES_PRODUCTION_PUSH_CERT = prop.getProperty("ITUNES_PRODUCTION_PUSH_CERT");
        ITUNES_PRODUCTION_PUSH_CERT_PASSWORD = prop.getProperty("ITUNES_PRODUCTION_PUSH_CERT_PASSWORD");
        PUSH_TOKEN = prop.getProperty("PUSH_TOKEN");
        GCM_SERVER_API_KEY = prop.getProperty("GCM_SERVER_API_KEY");
    }

    private void replaceInFile(File f, String from, String to) throws IOException {
        Path p = f.toPath();
        String content = new String(Files.readAllBytes(p)).replaceAll(from, to);
        Files.write(p, content.getBytes());        
    }

    private void appendToFile(File f, String t ) throws IOException {
        Path p = f.toPath();
        String content = new String(Files.readAllBytes(p)) + t;
        Files.write(p, content.getBytes());        
    }
    
    @Async
    public void buildApp(String secret, String pushKey, String targetType) {
        RestaurantEntity re = restRepository.findBySecret(secret);
        try {
            if(re == null) {
                return;
            }

            File tempDirectory = File.createTempFile("tempDir", "ending");
            tempDirectory.delete();
            tempDirectory.mkdirs();

            unzip(getClass().getResourceAsStream("/MyRestaurant.zip"), tempDirectory);

            // here we customize the code for the target app first we need to create a new
            // main class
            File mainPackage = new File(tempDirectory, "src/" + re.getPackageName().replace('.', '/'));
            mainPackage.mkdirs();
            File mainClass = new File(mainPackage, re.getAppName());
            String mainClassCode = "package " + re.getPackageName() + ";\n\n"
                    + "public class " + re.getAppName() + " extends com.myrestaurant.app.MyRestaurant {}";
            
            Files.write(mainClass.toPath(), mainClassCode.getBytes());
            File properties = new File(tempDirectory, "codenameone_settings.properties");
            replaceInFile(properties, "com.myrestaurant.app", re.getPackageName());
            replaceInFile(properties, "MyRestaurant", re.getAppName());
            
            File myRestaurantJava = new File(tempDirectory, "src/com/myrestaurant/app/MyRestaurant.java");
            replaceInFile(myRestaurantJava, "97adbd16-79a6-4e24-b516-d7361a2bab98", re.getId());
            
            String cssAppend = "";
            for(StyleEntity se : re.getStyles()) {
                cssAppend += "\n" + se.getUiid() + " {\n";
                if(se.getFgColor() != null) {
                    cssAppend += "    color: #" + Integer.toHexString(se.getFgColor()) + ";\n";
                }
                if(se.getBgColor() != null) {
                    cssAppend += "    background-color: #" + Integer.toHexString(se.getBgColor()) + ";\n";
                }
                if(se.getFont()!= null) {
                    cssAppend += "    font-family: " + se.getFont() + ";\n";
                    cssAppend += "    font-size: " + se.getFontSize()+ "mm;\n";
                }
                cssAppend += "}\n";
            }
            
            appendToFile(new File(tempDirectory, "css/theme.css"), cssAppend);
            
            
            String uuid = UUID.randomUUID().toString();
            FileResponseService.FILES_DIRECTORY.mkdirs();
            String resultUrl;
            
            if(targetType.equals("source")) {
                File tempFileName = new File(FileResponseService.FILES_DIRECTORY, uuid + ".zip");
                zipDir(tempFileName.getAbsolutePath(), tempDirectory.getAbsolutePath());
                resultUrl = "http://serverurl.com/files/" + uuid + ".zip";
            } else {
                File buildXML = new File(tempDirectory, "build.xml");
                replaceInFile(buildXML, "<property name=\"automated\" value=\"false\" />", "<property name=\"automated\" value=\"true\" />");
                File apacheAntExe = new File(System.getProperty("user.dir") + File.separator + "ant" + File.separator + "bin"
                    + File.separator + "ant");
                ProcessBuilder pb = new ProcessBuilder(apacheAntExe.getAbsolutePath(), "build-for-android-device")
                        .directory(tempDirectory).inheritIO();
                try {
                    int result = pb.start().waitFor();
                    if(result == 0) {
                        File resultZip = new File(tempDirectory, "dist/result.zip");
                        ZipInputStream zis = new ZipInputStream(new FileInputStream(resultZip));
                        ZipEntry entry;
                        while ((entry = zis.getNextEntry()) != null) {
                            String entryName = entry.getName();
                            if(entryName.endsWith(".apk")) {
                                File tempFileName = new File(FileResponseService.FILES_DIRECTORY, uuid + ".apk");
                                FileOutputStream fos = new FileOutputStream(tempFileName);
                                copy(zis, fos);
                                break;
                            }
                        }
                    } else {
                        if(pushKey != null && pushKey.length() > 0) {
                            sendPushNotification(pushKey, "There was a build error when trying to build your app within the Ant process",1);
                        }
                        re.setLastResult("There was a build error when trying to build your app within the Ant process");
                        return;
                    }
                } catch(InterruptedException err) {
                    throw new IOException(err);
                }
                
                resultUrl = "http://serverurl.com/files/" + uuid + ".apk";
            }

            if(pushKey != null && pushKey.length() > 0) {
                sendPushNotification(pushKey, "App build completed successfully!;" + resultUrl, 3);
            }
            re.setLastResult(resultUrl);
            restRepository.save(re);
        } catch(IOException err) {
            try {
                if(pushKey != null && pushKey.length() > 0) {
                    sendPushNotification(pushKey, "There was a build error when trying to build your app. Please let us know...",1);
                }
                re.setLastResult("There was a build error when trying to build your app. Please let us know...");
                restRepository.save(re);
            } catch(IOException err1) {
                logger.error("Ugh error during push", err1);
            }
            throw new RuntimeException(err);
        }
    }    
    
    private void sendPushNotification(String deviceId, String messageBody, int type) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)new URL("https://push.codenameone.com/push/push").openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        String cert = ITUNES_DEVELOPMENT_PUSH_CERT;
        String pass = ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD;
        if(ITUNES_PRODUCTION_PUSH) {
            cert = ITUNES_PRODUCTION_PUSH_CERT;
            pass = ITUNES_PRODUCTION_PUSH_CERT_PASSWORD;
        }
        String query = "token="  + PUSH_TOKEN +
            "&device=" + URLEncoder.encode(deviceId, "UTF-8") +
            "&type=" + type +
            "&auth=" + URLEncoder.encode(GCM_SERVER_API_KEY, "UTF-8") +
            "&certPassword=" + URLEncoder.encode(pass, "UTF-8") +
            "&cert=" + URLEncoder.encode(cert, "UTF-8") +
            "&body=" + URLEncoder.encode(messageBody, "UTF-8") +
            "&production=" + ITUNES_PRODUCTION_PUSH;
        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes("UTF-8"));
        }
        int c = connection.getResponseCode();
        if(c == 200) {
            try (InputStream i = connection.getInputStream()) {
                byte[] data = readInputStream(i);
                logger.info("Push server returned JSON: " + new String(data));
            }
        } else {
            logger.error("Error response code from push server: " + c);
        }
    }
    
    private  static byte[] readInputStream(InputStream i) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            copy(i, b);
            return b.toByteArray();
        }
    }

    private static void copy(InputStream i, OutputStream o) throws IOException {
            byte[] buffer = new byte[8192];
            int size = i.read(buffer);
            while(size > -1) {
                o.write(buffer, 0, size);
                size = i.read(buffer);
            }
     }
    
    private void unzip(InputStream sourceZip, File destDir) throws IOException {
        BufferedOutputStream dest = null;
        ZipInputStream zis = new ZipInputStream(sourceZip);
        ZipEntry entry;
        byte[] data = new byte[8192];
        while ((entry = zis.getNextEntry()) != null) {
            String entryName = entry.getName();

            if (entry.isDirectory()) {
                File dir = new File(destDir, entryName);
                dir.mkdirs();
                continue;
            }

            int count;

            // write the files to the disk
            File destFile = new File(destDir, entryName);
            destFile.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(destFile);
            dest = new BufferedOutputStream(fos, data.length);
            while ((count = zis.read(data, 0, data.length)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
        zis.close();
        sourceZip.close();
    }

    private static void zipDir(String zipFileName, String dir) throws IOException {
        File dirObj = new File(dir);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        addDir(dirObj, dirObj, out);
        out.close();
    }

    static void addDir(File baseDir, File dirObj, ZipOutputStream out) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[8192];

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                boolean found = false;
                if (!found) {
                    addDir(baseDir, files[i], out);
                }
                continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            //System.out.println(" Adding: " + files[i].getAbsolutePath());
            out.putNextEntry(new ZipEntry(files[i].getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1).replace('\\', '/')));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
    }
}
