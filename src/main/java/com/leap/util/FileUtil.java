package com.leap.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

// Referenced classes of package net.innofactory.common.util:
//            TypeUtil

public class FileUtil
{

    public FileUtil()
    {
    }

    public static int removeDir(String dirPath)
    {
        return removeDir(dirPath, false);
    }

    public static int removeDir(String dirPath, boolean fileOnly)
    {
        int removedCount = 0;
        File file = new File(dirPath);
        if(file.isDirectory())
        {
            File subFiles[] = file.listFiles();
            for(int i = 0; i < subFiles.length; i++)
                removedCount += removeDir(subFiles[i].getAbsolutePath(), fileOnly);

            if(!fileOnly)
            {
                file.delete();
                removedCount++;
            }
        } else
        {
            file.delete();
            removedCount++;
        }
        return removedCount;
    }

    public static void copyFile(File srcFile, File destFile)
        throws IOException
    {
        copyFile(srcFile, destFile, 2048);
    }

    public static void copyFile(File srcFile, File destFile, int bufferSize)
        throws IOException
    {
        writeStreamToStream(new FileInputStream(srcFile), new FileOutputStream(destFile), bufferSize);
    }

    public static void writeStreamToFile(InputStream is, File destFile)
        throws IOException
    {
        writeStreamToFile(is, destFile, 2048);
    }

    public static void writeStreamToFile(InputStream is, File destFile, int bufferSize)
        throws IOException
    {
        writeStreamToStream(is, new FileOutputStream(destFile), bufferSize);
    }

    public static void writeFileToStream(File srcFile, OutputStream os)
        throws IOException
    {
        writeFileToStream(srcFile, os, 2048);
    }

    public static void writeFileToStream(File srcFile, OutputStream os, int bufferSize)
        throws IOException
    {
        writeStreamToStream(new FileInputStream(srcFile), os, bufferSize);
    }

    public static void writeStreamToStream(InputStream is, OutputStream os)
        throws IOException
    {
        writeStreamToStream(is, os, 2048);
    }

    public static void writeStreamToStream(InputStream is, OutputStream os, int bufferSize)
        throws IOException
    {
        BufferedInputStream bis;
        BufferedOutputStream bos;
        bis = null;
        bos = null;
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(os);
        byte buf[] = new byte[bufferSize];
        for(int read = 0; (read = bis.read(buf)) != -1;)
            bos.write(buf, 0, read);

        if(bos != null)
            bos.close();
        if(bis != null)
            bis.close();
       // break MISSING_BLOCK_LABEL_102;
     //   Exception exception;
       // exception;
        if(bos != null)
            bos.close();
        if(bis != null)
            bis.close();
       // throw exception;
    }

    public static File getDirectory(String dirPath, boolean createDir)
        throws IOException
    {
        File dir = null;
        dir = new File(dirPath);
        if(!dir.exists())
            if(createDir)
            {
                getDirectory(dir.getParent(), true);
                if(!dir.mkdir())
                    dir = null;
            } else
            {
                dir = null;
            }
        return dir;
    }

    public static String generateUniqueId()
    {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return uniqueId;
    }

    public static String generateTempFileName(String prefix, String suffix)
    {
        prefix = TypeUtil.stringValue(prefix);
        suffix = TypeUtil.stringValue(suffix);
        return (new StringBuilder()).append(prefix).append(generateUniqueId()).append(suffix).toString();
    }

    public static String getExtension(String fileName)
    {
        if(fileName != null)
        {
            int cutIndex = fileName.lastIndexOf(".");
            if(cutIndex >= 0)
                return fileName.substring(cutIndex + 1).toLowerCase();
        }
        return null;
    }

    public static byte[] getBytes(File file)
        throws IOException
    {
        ByteArrayOutputStream os;
        InputStream is;
        os = null;
        is = null;
        byte abyte0[];
        if(file == null || !file.exists() || !file.isFile()) return null;
            //break MISSING_BLOCK_LABEL_100;
        os = new ByteArrayOutputStream();
        is = new FileInputStream(file);
        byte buffer[] = new byte[4096];
        for(int read = 0; (read = is.read(buffer)) != -1;)
            os.write(buffer, 0, read);

        byte arrayOfByte1[] = os.toByteArray();
        abyte0 = arrayOfByte1;
        if(os != null)
            os.close();
        if(is != null)
            is.close();
        return abyte0;
//        if(os != null) os.close();
//        if(is != null)
//            is.close();
//        break MISSING_BLOCK_LABEL_140;
//        Exception exception;
//       // exception;
//        if(os != null)
//            os.close();
//        if(is != null)
//            is.close();
//        throw exception;
//        return new byte[0];
    }

    public static String digestMd5(File file)
        throws IOException, NoSuchAlgorithmException
    {
        return digest(file, ALGORITHM_MD5);
    }

    public static String digestSha1(File file)
        throws IOException, NoSuchAlgorithmException
    {
        return digest(file, ALGORITHM_SHA1);
    }

    public static String digest(File file, String algorithm)
        throws IOException, NoSuchAlgorithmException
    {
        StringBuilder sb = new StringBuilder();
        byte bytes[] = getBytes(file);
        if(bytes.length > 0)
        {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            byte md5Data[] = md.digest();
            for(int i = 0; i < md5Data.length; i++)
                sb.append(Integer.toString((md5Data[i] & 0xff) + 256, 16).substring(1));

        }
        return sb.toString();
    }

    private static final int DEFAULT_BUFFER_SIZE = 2048;
    public static String ALGORITHM_MD5 = "MD5";
    public static String ALGORITHM_SHA1 = "SHA-1";

}
