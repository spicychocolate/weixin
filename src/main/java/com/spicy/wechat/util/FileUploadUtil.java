package com.spicy.wechat.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.spicy.wechat.common.FileResponse;


public class FileUploadUtil {
    /**
     * @Description: TODO
     * @param multipartFile 原文件org.setUserId(userId);org.setUserId(userId);org.setUserId(userId);org.setUserId(userId);org.setUserId(userId);org.setUserId(userId);org.setUserId(userId);
     * @param targetPath 存放地址
     * @param prefix 重命名文件前缀 PC_21324346552.png
     * @return FileResponse
     * @throws IOException
     * @throws IllegalStateException
     * @date: 2016年10月12日 上午11:07:57
     */
    public static FileResponse uploadFile(MultipartFile multipartFile, String targetPath, String prefix) throws IllegalStateException, IOException {
        FileResponse fileResponse = new FileResponse();
        String fileName = multipartFile.getOriginalFilename();
        fileResponse.setFileName(fileName);
        String newFileName = prefix + "_" + System.currentTimeMillis() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        fileResponse.setNewFileName(newFileName);
        File file = new File(targetPath + "\\" + newFileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        multipartFile.transferTo(file);
        return fileResponse;
    }

    /**
     * 移动文件夹下的所有文件到另外一个文件夹
     * @param tempDir 要移动的文件
     * @param toDirName 要移动至的文件夹名称
     * @throws IOException
     * @date 2016年10月28日 上午11:04:57
     */
    public static void moveFiles(File tempDir, String toDirName) throws IOException {
        File[] files = tempDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(toDirName + "\\" + file.getName());
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fis.close();
            fos.close();
        }
    }

    /**
     * 删除目录下的所有文件及子目录下所有文件
     * @param dir
     * @return
     * @date 2016年10月28日 下午12:57:07
     */
    public static boolean deleteDir(File dir) throws IOException {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    /**
     * 删除指定文件
     * @param filePaths 多个文件路径
     * @return
     * @date 2016年12月28日
     */
    public static boolean deleteFiles(String[] filePaths) throws IOException{
        for(String filePath:filePaths){
            File file = new File(filePath);
            if(file.exists()){
                boolean flag = file.delete();
                if(!flag){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 删除指定文件
     * @param filePaths 文件路径
     * @return
     * @date 2016年12月28日
     */
    public static boolean deleteFile(String filePath)throws IOException{
        File file = new File(filePath);
        if(file.exists()){
            boolean flag = file.delete();
            if(!flag){
                return false;
            }
        }
        return true;
    }

    /**
     * 递归获取所有拥有文件的文件夹
     * @param file
     * @param resultFileName
     * @return
     * @date 2016年11月4日 下午2:57:10
     */
    public static Set<String> ergodic(File file, Set<String> resultFileName) {
        File[] files = file.listFiles();
        if (files == null)
            return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if (f.isDirectory()) {// 判断是否文件夹
                // resultFileName.add(f.getPath());
                ergodic(f, resultFileName);// 调用自身,查找子目录
            } else {
                resultFileName.add(f.getParent());
            }
        }
        return resultFileName;
    }

    /**
     * 复制目录下整个文件及文件夹到另一个目录下
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            // System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 解压文件到指定目录 解压后的文件名，和之前一致
     * @param zipFile 待解压的zip文件
     * @param descDir 指定目录
     */
    public static String unZipFiles(File zipFile, String descDir) throws IOException {
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));// 解决中文文件夹乱码
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));
        File pathFile = new File(descDir + name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            // System.out.println(outPath);
            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        zip.close();
        return descDir + name;
    }

    /**
     * 功能： 拷贝image从一个地址至另一个地址，图片名和之前一样
     * @param sourcePath 图片初始生成地址
     * @param toPath 图片要复制到的地方
     * @param fileName 新图片名（不包含后缀）
     */
    @SuppressWarnings("resource")
    public static void copyToOtherPath(String sourcePath, String toPath, String fileName) {
        File file = new File(sourcePath);
        String prefix = file.getName().substring(file.getName().lastIndexOf(".")); // 原文件名后缀
        try {
            File toFile = new File(toPath);
            if (!toFile.exists())
                toFile.mkdirs();
            FileChannel srcChannel = new FileInputStream(sourcePath).getChannel();
            FileChannel dstChannel = new FileOutputStream(toPath + "//" + fileName + prefix).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     * @param filepath 文件所在目录
     * @param zippath 压缩后zip文件地址名称
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     */
    public static void zipMultiFile(String filepath, String zippath, boolean dirFlag) {
        try {
            File file = new File(filepath);// 要被压缩的文件夹
            File zipFile = new File(zippath);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fileSec : files) {
                    if (dirFlag) {
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    } else {
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileSec : files) {
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            byte[] buf = new byte[1024];
            InputStream input = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while ((len = input.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            input.close();
        }
    }
    
    /**
     * 
     * @Description:文件下载Util
     * @param request
     * @param response
     * @param storeName
     * @param contentType
     * @throws Exception
     * @date 2017年3月17日 上午10:52:35
     * @version V1.0
     */
    public static void download(HttpServletRequest request, 
                                     HttpServletResponse response, String pathFile, String fileName
                                    ) throws Exception {                                   
         request.setCharacterEncoding("UTF-8"); 
         BufferedInputStream bis = null;                                              
         BufferedOutputStream bos = null;                                                             
         //获取下载文件路径
         String downLoadPath =pathFile;                            
         //获取文件的长度
         long fileLength = new File(downLoadPath).length();  
         //设置文件输出类型
         response.setContentType("application/octet-stream"); 
         response.setHeader("Content-disposition", "attachment; filename="+fileName);
         //设置输出长度
         response.setHeader("Content-Length", String.valueOf(fileLength)); 
         //获取输入流
         bis = new BufferedInputStream(new FileInputStream(downLoadPath)); 
         //输出流
         bos = new BufferedOutputStream(response.getOutputStream()); 
         byte[] buff = new byte[2048]; 
         int bytesRead; 
         while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) { 
             bos.write(buff, 0, bytesRead); 
         } 
       //关闭流
         bis.close(); 
         bos.close();         
     } 
}
