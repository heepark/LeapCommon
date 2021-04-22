package com.leap.util;

import java.io.*;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class UnzipUtil
{

    public UnzipUtil(String zipFilePath, String outputDirPath)
        throws IOException
    {
        zipFile = null;
        this.outputDirPath = null;
        zipFile = new ZipFile(zipFilePath);
        this.outputDirPath = outputDirPath;
    }

    public UnzipUtil(ZipFile zipFile, String outputDirPath)
        throws IOException
    {
        this.zipFile = null;
        this.outputDirPath = null;
        this.zipFile = zipFile;
        this.outputDirPath = outputDirPath;
    }

    public File unzip()
        throws IOException
    {
        (new File(outputDirPath)).mkdirs();
        ZipEntry zipEntry;
        for(Enumeration zipEntries = zipFile.getEntries(); zipEntries.hasMoreElements(); writeZipEntey(outputDirPath, zipEntry))
            zipEntry = (ZipEntry)zipEntries.nextElement();

        return new File(outputDirPath);
    }

    private void writeZipEntey(String parentEntryPath, ZipEntry zipEntry)
        throws IOException
    {
        String zipEntryPath;
        BufferedInputStream in;
        BufferedOutputStream out;
        String separator = System.getProperty("file.separator");
        if(!parentEntryPath.endsWith(separator))
            parentEntryPath = (new StringBuilder()).append(parentEntryPath).append(separator).toString();
        zipEntryPath = (new StringBuilder()).append(parentEntryPath).append(zipEntry.getName()).toString();
        File zipEntryDir = new File((new StringBuilder()).append(parentEntryPath).append(zipEntry.getName()).toString());
        if(zipEntry.isDirectory())
        {
            zipEntryDir.mkdirs();
           // break MISSING_BLOCK_LABEL_244;
        }
        File parentDir = zipEntryDir.getParentFile();
        parentDir.mkdirs();
        in = null;
        out = null;
        in = new BufferedInputStream(zipFile.getInputStream(zipEntry));
        out = new BufferedOutputStream(new FileOutputStream(zipEntryPath));
        byte buffer[] = new byte[0x19000];
        for(int n = 0; (n = in.read(buffer)) >= 0;)
            out.write(buffer, 0, n);

        out.flush();
        if(out != null)
            out.close();
        if(in != null)
            in.close();
       // break MISSING_BLOCK_LABEL_244;
       // Exception exception;
       // exception;
        if(out != null)
            out.close();
        if(in != null)
            in.close();
       // throw exception;
    }

    public ZipFile getZipFile()
    {
        return zipFile;
    }

    public void setZipFile(ZipFile zipFile)
    {
        this.zipFile = zipFile;
    }

    public String getOutputDirPath()
    {
        return outputDirPath;
    }

    public void setOutputDirPath(String outputDirPath)
    {
        this.outputDirPath = outputDirPath;
    }

    private ZipFile zipFile;
    private String outputDirPath;
}
