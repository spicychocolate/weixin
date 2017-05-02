package com.spicy.wechat.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ZipUtil {
    public static final int DEFAULT_BUFSIZE = 1024 * 16;

    /**
     * 解压Zip文件
     * @param srcZipFile
     * @param destDir
     * @throws IOException
     */
    public static void unZip(File srcZipFile, String destDir) throws IOException {
        ZipFile zipFile = new ZipFile(srcZipFile);
        unZip(zipFile, destDir);
    }

    /**
     * 解压Zip文件
     * @param srcZipFile
     * @param destDir
     * @throws IOException
     */
    public static void unZip(String srcZipFile, String destDir) throws IOException {
        ZipFile zipFile = new ZipFile(srcZipFile);
        unZip(zipFile, destDir);
    }

    /**
     * 解压Zip文件
     * @param zipFile
     * @param destDir
     * @throws IOException
     */
    public static void unZip(ZipFile zipFile, String destDir) throws IOException {
        Enumeration<? extends ZipEntry> e = zipFile.entries();
        ZipEntry entry = null;
        while (e.hasMoreElements()) {
            entry = e.nextElement();
            File destFile = new File(destDir + entry.getName());
            if (entry.isDirectory()) {
                destFile.mkdirs();
            } else {
                destFile.getParentFile().mkdirs();
                InputStream eis = zipFile.getInputStream(entry);
                write(eis, destFile);
            }
        }
    }

    /**
     * 将输入流中的数据写到指定文件
     * @param inputStream
     * @param destFile
     */
    public static void write(InputStream inputStream, File destFile) throws IOException {
        BufferedInputStream bufIs = null;
        BufferedOutputStream bufOs = null;
        try {
            bufIs = new BufferedInputStream(inputStream);
            bufOs = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] buf = new byte[DEFAULT_BUFSIZE];
            int len = 0;
            while ((len = bufIs.read(buf, 0, buf.length)) > 0) {
                bufOs.write(buf, 0, len);
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            close(bufOs, bufIs);
        }
    }

    /**
     * 安全关闭多个流
     * @param streams
     */
    public static void close(Closeable... streams) {
        try {
            for (Closeable s : streams) {
                if (s != null)
                    s.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
    }
}
