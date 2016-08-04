package com.example.hongcheng.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ZipUtil
{
    private ZipUtil()
    {
    }
    
    private final static String TAG = ZipUtil.class.getName();
    
    /**
     * <br>
     * 
     * @author s00208243
     * @see [递归压缩文件夹]
     * @since iManager TMS1000 V100R006C00 , 2015年1月2日
     * @param srcRootDir
     * @param file
     * @param zos
     * @throws Exception
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos)
    {
        BufferedInputStream bis = null;
        
        try
        {
            // 如果是文件，则直接压缩该文件
            if (file.isFile())
            {
                int count = 0;
                byte data[] = new byte[1024];
                
                File rootFile = new File(srcRootDir);
                String subPath = file.getCanonicalPath();
                int index = subPath.indexOf(rootFile.getCanonicalPath());
                if (index != -1)
                {
                    subPath = file.getCanonicalPath().substring(index + rootFile.getCanonicalPath().length() + 1);
                }
                ZipEntry entry = new ZipEntry(subPath);
                
                zos.putNextEntry(entry);
                
                bis = new BufferedInputStream(new FileInputStream(file));
                while ((count = bis.read(data)) != -1)
                {
                    zos.write(data, 0, count);
                }
                zos.closeEntry();
            }
            // 如果是目录，则压缩整个目录
            else
            {
                // 压缩目录中的文件或子目录
                File[] childFileList = file.listFiles();
                
                if (null != childFileList)
                {
                    for (int n = 0; n < childFileList.length; n++)
                    {
                        zip(srcRootDir, childFileList[n], zos);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            LoggerUtils.error(TAG, "zip error");
        }
        catch (IOException e)
        {
            LoggerUtils.error(TAG, "zip error");
        }
        finally
        {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    LoggerUtils.error(TAG, "bis.close() error");
                }
            }
        }
        
    }
    
    /**
     * <br>
     * 
     * @author s00208243
     * @see [对文件或文件目录进行压缩]
     * @since iManager TMS1000 V100R006C00 , 2015年1月2日
     * @param srcDir 压缩文件夹
     * @param zipDir 压缩文件存放
     * @param zipFileName
     * @throws Exception
     */
    public static void zip(String srcDir, String zipDir, String zipFileName)
    {
        if ((null == srcDir || "".equals(srcDir)) || (null == zipDir || "".equals(zipDir))
                || (null == zipFileName || "".equals(zipFileName)))
        {
            LoggerUtils.debug(TAG, "zip parameter error");
            return;
        }
        
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        
        try
        {
            File srcFile = new File(srcDir);
            
            // 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
            if (srcFile.isDirectory() && zipDir.indexOf(srcDir) != -1)
            {
                LoggerUtils.debug(TAG, "zipPath must not be the child directory of srcPath");
                return;
            }
            
            checkDir(zipDir);
            
            // 创建压缩文件保存的文件对象
            String zipFilePath = zipDir + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists())
            {
                // 删除已存在的目标文件
                // zipFile.delete();
                LoggerUtils.debug(TAG, "zipFile.delete()=" + zipFile.delete());
            }
            
            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);
            
            // 调用递归压缩方法进行目录或文件压缩
            zip(srcDir, srcFile, zos);
            zos.flush();
        }
        catch (FileNotFoundException e)
        {
            LoggerUtils.error(TAG, "File not found exception.", e);
        }
        catch (IOException e)
        {
            LoggerUtils.error(TAG, "", e);
        }
        finally
        {
            if (zos != null)
            {
                
                try
                {
                    zos.close();
                }
                catch (IOException e)
                {
                    LoggerUtils.error(TAG, "zos.close() error");
                }
                
            }
            
            if (cos != null)
            {
                
                try
                {
                    cos.close();
                }
                catch (IOException e)
                {
                    LoggerUtils.error(TAG, "cos.close() error");
                }
                
            }
        }
        
    }
    
    /**
     * <br>
     * 
     * @author s00208243
     * @see [解压缩zip包]
     * @since iManager TMS1000 V100R006C00 , 2015年1月3日
     * @param unzipFilePath 待解压文件
     * @param unzipDir 解压路径
     * @param includeZipFileName 解压目录是否包含文件名称
     */
    @SuppressWarnings("unchecked")
    public static void unzip(String unzipFilePath, String unzipDir, boolean includeZipFileName)
    {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        File zipFile = null;
        ZipFile zip = null;
        
        try
        {
            if ((null == unzipDir || "".equals(unzipDir)) || (null == unzipFilePath || "".equals(unzipFilePath)))
            {
                LoggerUtils.debug(TAG, "unzip parameter error");
                return;
            }
            zipFile = new File(unzipFilePath);
            // 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
            // if (includeZipFileName)
            // {
            // String fileName = zipFile.getName();
            // if ((null == fileName || "".equals(fileName)))
            // {
            // fileName = fileName.substring(0, fileName.lastIndexOf("."));
            // }
            // unzipDir = unzipDir + File.separator + fileName;
            // }
            checkDir(unzipDir);
            
            zip = new ZipFile(unzipFilePath);
            
            // 开始解压
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
            // 循环对压缩包里的每一个文件进行解压
            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                // 构建压缩包中一个文件解压后保存的文件全路径
                String entryFilePath = unzipDir + File.separator + entry.getName();
                if (entry.isDirectory())
                {
                    checkDir(entryFilePath);
                }
                else
                {
                    
                    // 创建解压文件
                    int count = 0;
                    byte[] buffer = new byte[1024];
                    File entryFile = new File(entryFilePath);
                    checkDir(entryFile.getParent());
                    OutputStream output = new FileOutputStream(entryFile);
                    bos = new BufferedOutputStream(output);
                    bis = new BufferedInputStream(zip.getInputStream(entry));
                    while ((count = bis.read(buffer)) != -1)
                    {
                        bos.write(buffer, 0, count);
                    }
                    bos.flush();
                    output.close();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            LoggerUtils.error(TAG, "file not found error", e);
            // 解压缩失败时删除解压缩的目录
            FileUtils.deleteFile(new File(unzipDir));
        }
        catch (IOException e)
        {
            LoggerUtils.error(TAG, "unzip error", e);
            // 解压缩失败时删除解压缩的目录
            FileUtils.deleteFile(new File(unzipDir));
        }
        finally
        {
            if (zip != null)
            {
                try
                {
                    zip.close();
                }
                catch (IOException e)
                {
                    LoggerUtils.error(TAG, "zip.close() error");
                }
            }

            IOUtils.close(bis);
            IOUtils.close(bos);

            if (zipFile != null)
            {
                boolean delete = zipFile.delete();
                LoggerUtils.debug(TAG, "unzipFilePath = " + unzipFilePath + ",zipFile.delete() = " + delete);
            }
        }
    }
    
    public static void checkDir(String dirPath)
    {
        File dir = new File(dirPath);
        if (!dir.exists())
        {
            boolean flag = dir.mkdirs();
            if (!flag)
            {
                LoggerUtils.debug(TAG, "create dir failed.");
            }
        }
    }
}
