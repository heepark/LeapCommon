package com.leap.util;

import java.io.*;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil
{

    public ZipUtil(String zipFilePath, File entryFiles[], boolean includeDirEntry, String encoding)
    {
        size = 0L;
        entryFileCount = 0;
        this.zipFilePath = null;
        this.entryFiles = null;
        this.includeDirEntry = true;
        this.encoding = null;
        this.zipFilePath = zipFilePath;
        this.entryFiles = entryFiles;
        this.includeDirEntry = includeDirEntry;
        this.encoding = encoding;
    }

    public ZipUtil(String zipFilePath, File entryFiles[], String encoding)
    {
        this(zipFilePath, entryFiles, false, encoding);
    }

    public ZipUtil(String zipFilePath, File srcFiles[])
    {
        this(zipFilePath, srcFiles, false, "euc-kr");
    }

    public File zip()
        throws IOException
    {
        File zipFile;
        String separator;
        ZipOutputStream zos;
        zipFile = null;
        for(int i = 0; i < entryFiles.length; i++)
            if(!entryFiles[i].isFile() && !entryFiles[i].isDirectory())
                return null;

        separator = System.getProperty("file.separator");
        if(zipFilePath.indexOf(separator) != -1)
        {
            String zipDirPath = zipFilePath.substring(0, zipFilePath.lastIndexOf(separator) + 1);
            (new File(zipDirPath)).mkdirs();
        }
        zos = null;
        zipFile = new File(zipFilePath);
        zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zos.setEncoding(encoding);
        zos.setLevel(3);
        String rootEntryPath = null;
        if(includeDirEntry && entryFiles.length > 0)
        {
            String firstEntryFilePath = entryFiles[0].getAbsolutePath();
            rootEntryPath = firstEntryFilePath.substring(0, firstEntryFilePath.lastIndexOf(separator) + 1);
        }
        for(int i = 0; i < entryFiles.length; i++)
            addEntryFiles(zos, entryFiles[i], rootEntryPath);

        if(zos != null)
            zos.close();
        //break MISSING_BLOCK_LABEL_239;
        //Exception exception;
       // exception;
        if(zos != null)
            zos.close();
       // throw exception;
        return zipFile;
    }

    private void addEntryFiles(ZipOutputStream zos, File entryFile, String rootEntryPath)
        throws IOException
    {
        String entryName;
        FileInputStream fis;
        String entryFilePath = entryFile.getAbsolutePath();
        if(entryFile.isDirectory())
        {
            if(entryFile.getName().equalsIgnoreCase(".metadata"))
                return;
            File files[] = entryFile.listFiles();
            for(int i = 0; i < files.length; i++)
                addEntryFiles(zos, files[i], rootEntryPath);

           // break MISSING_BLOCK_LABEL_261;
        }
        if(entryFilePath.equalsIgnoreCase(zipFilePath))
            return;
        size += entryFile.length();
        entryFileCount++;
        entryName = "";
        if(includeDirEntry)
        {
            if(entryFilePath.startsWith(rootEntryPath))
                entryName = entryFilePath.substring(rootEntryPath.length());
            else
                entryName = entryFilePath;
        } else
        {
            entryName = entryFilePath.substring(entryFilePath.lastIndexOf(System.getProperty("file.separator")) + 1);
        }
        fis = null;
        fis = new FileInputStream(entryFile);
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);
        int DATA_BLOCK_SIZE = 2048;
        byte b[] = new byte[DATA_BLOCK_SIZE];
        int byteCount;
        while((byteCount = fis.read(b, 0, DATA_BLOCK_SIZE)) != -1)
            zos.write(b, 0, byteCount);
        zos.closeEntry();
        if(fis != null)
            fis.close();
      //  break MISSING_BLOCK_LABEL_261;
      //  Exception exception;
        //exception;
        if(fis != null)
            fis.close();
      //  throw exception;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public int getEntryFileCount()
    {
        return entryFileCount;
    }

    public void setEntryFileCount(int entryFileCount)
    {
        this.entryFileCount = entryFileCount;
    }

    public String getZipFilePath()
    {
        return zipFilePath;
    }

    public void setZipFilePath(String zipFilePath)
    {
        this.zipFilePath = zipFilePath;
    }

    public File[] getEntryFiles()
    {
        return entryFiles;
    }

    public void setEntryFiles(File entryFiles[])
    {
        this.entryFiles = entryFiles;
    }

    public boolean isIncludeDirEntry()
    {
        return includeDirEntry;
    }

    public void setIncludeDirEntry(boolean includeDirEntry)
    {
        this.includeDirEntry = includeDirEntry;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    private long size;
    private int entryFileCount;
    private String zipFilePath;
    private File entryFiles[];
    private boolean includeDirEntry;
    private String encoding;
}
