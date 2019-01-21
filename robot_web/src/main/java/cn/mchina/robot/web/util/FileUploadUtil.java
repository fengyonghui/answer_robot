package cn.mchina.robot.web.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-1-25
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public class FileUploadUtil {
    public static String UPLOAD_ROOT = "/upload";
    public static String FILE_SEP = System.getProperties().getProperty("file.separator");

    public static String uploadFile(CommonsMultipartFile obj, String localFileName, String domain, Integer objectId) throws IOException {
        String result = null;

        if (obj != null && org.apache.commons.lang3.StringUtils.isNotEmpty(obj.getOriginalFilename())) {
            String realPath = UPLOAD_ROOT + FILE_SEP;
            String datePath = createFold(realPath, domain, objectId);
            String fileName = localFileName;
            if (fileName == null) {
                fileName = dynamicFileName(obj.getOriginalFilename());
            }
            File file = new File(realPath + FILE_SEP + datePath + FILE_SEP + fileName);

            byte[] bytes = obj.getBytes();
            FileUtils.writeByteArrayToFile(file, bytes);
            result = "/upload/" + datePath.replaceAll("\\\\", "/") + "/" + fileName;
        }

        return result;
    }

    public static String dynamicFileName(String fileName) {
        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            int k = random.nextInt(); // 生成随机整数：
            int j = Math.abs(k % 10); // 生成0-10之间的随机数字：
            pass.append(Integer.toString(j));
        }
        pass.append(System.currentTimeMillis());
        if (StringUtils.isNotBlank(FilenameUtils.getExtension(fileName))) {
            pass.append("").append(FilenameUtils.getExtension(fileName));
        } else {
            pass.append("").append("jpeg");
        }
        return pass.toString();
    }

    public static String createFold(String realPath, String domain, Integer objectId) {
        String path = new SimpleDateFormat("yyyyMMdd").format(new Date());
        File f = null;
        String filePath = "";
        if (objectId != null) {
            filePath = domain + FILE_SEP + path + FILE_SEP + objectId;
            f = new File(realPath + FILE_SEP + filePath);
        } else {
            filePath = domain + FILE_SEP + path;
            f = new File(realPath + FILE_SEP + filePath);
        }
        if (f.isDirectory() && f.exists()) {
        } else {
            f.mkdirs();
        }

        return filePath;
    }
}
