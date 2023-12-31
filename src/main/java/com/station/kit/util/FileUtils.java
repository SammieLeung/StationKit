package com.station.kit.util;

/**
 * Created by kingt on 2018/1/30.
 */

import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kingt on 2018/1/30.
 */
public class FileUtils {

    public static String getFileExt(String fileName) {
        if (TextUtils.isEmpty(fileName)) return "";
        int p = fileName.lastIndexOf('.');
        if (p != -1) {
            return fileName.substring(p).toLowerCase();
        }
        return "";
    }


    public static File[] getMediaFiles(String parentPath) {
        return getMediaFiles(parentPath, false);
    }

    public static File[] getMediaFiles(String parentPath, boolean isRecursive) {
        final File parentFile = new File(parentPath);
        if (parentFile.exists() && parentFile.isDirectory()) {
            File[] subFiles = parentFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile() && !parentFile.isHidden() && FileUtils.isMediaFile(pathname.getName());
                }
            });
            if (isRecursive) {
                File[] subDirs = parentFile.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                });
                for (File subDir : subDirs) {
                    File[] subFiles2 = getMediaFiles(subDir.getPath(), isRecursive);
                    File[] desFiles = new File[subFiles2.length + subFiles.length];
                    System.arraycopy(subFiles, 0, desFiles, 0, subFiles.length);
                    System.arraycopy(subFiles2, 0, desFiles, subFiles.length, subFiles2.length);
                    subFiles = desFiles;
                }
            }
            return subFiles;
        }
        return null;
    }


    public static boolean isMediaFile(String fileName) {
        switch (getFileExt(fileName)) {
            case ".avi":
            case ".mp4":
            case ".m4v":
            case ".mkv":
            case ".mov":
            case ".mpeg":
            case ".mpg":
            case ".mpe":
            case ".rm":
            case ".rmvb":
            case ".3gp":
            case ".wmv":
            case ".asf":
            case ".asx":
            case ".dat":
            case ".vob":
            case ".m3u8":
            case ".webm":
            case ".m2ts":
            case ".iso":
                return true;
            default:
                return false;
        }
    }

    public static String getFileNameWithoutExt(String filePath) {
        if (TextUtils.isEmpty(filePath)) return "";
        String fileName = filePath;
        int p = fileName.lastIndexOf(File.separatorChar);
        if (p != -1) {
            fileName = fileName.substring(p + 1);
        }
        p = fileName.lastIndexOf('.');
        if (p != -1) {
            fileName = fileName.substring(0, p);
        }
        return fileName;
    }

    public static String getWebMediaFileName(String url) {
        Uri uri = Uri.parse(url);
        return getFileName(uri.getPath());
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) return "";
        String fileName = filePath;
        int p = fileName.lastIndexOf(File.separatorChar);
        if (p != -1) {
            fileName = fileName.substring(p + 1);
        }
        return fileName;
    }

    public static String getLastFolderName(String path) {
        if (TextUtils.isEmpty(path)) return "";
        String fileName = path;
        if (fileName.endsWith(File.separator)) {
            fileName = fileName.substring(0, fileName.lastIndexOf(File.separator));
        }
        int p = fileName.lastIndexOf(File.separatorChar);
        if (p != -1)
            fileName = fileName.substring(p + 1, fileName.length());
        return fileName;
    }


    public static void deleteDirFiles(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                if (f.isDirectory()) deleteDirFiles(f);
                f.delete();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    public static void deleteFile(File file, Boolean force) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory() && force) {
            try {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory())
                        deleteFile(f, force);
                    else
                        f.delete();
                }
                file.delete();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean isLiveMedia(String url) {
        if (TextUtils.isEmpty(url)) return false;
        String uri = url.toLowerCase();
        if (url.startsWith("rtmp://") || url.startsWith("rtmps://") || url.startsWith("mms://")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkDownloadTask(String url) {
        if (TextUtils.isEmpty(url)) return false;
        String uri = url.toLowerCase();
        if (uri.startsWith("thunder://") || url.startsWith("ftp://") || url.startsWith("http://")
                || url.startsWith("https://") || url.startsWith("ed2k://") || url.startsWith("magnet:?")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getMagnetHashCode(String url) {
        if (TextUtils.isEmpty(url)) return "";
        int p = url.indexOf('=');
        if (p == -1) return "";
        String[] hashCode = url.substring(p).split(":");
        if (hashCode.length > 2) {
            p = hashCode[2].indexOf('&');
            if (p != -1) hashCode[2] = hashCode[2].substring(0, p);
            return hashCode[2];
        }
        return "";
    }

    public static boolean isInArray(int i, int[] intArr) {
        for (int k : intArr) {
            if (i == k)
                return true;
        }
        return false;
    }

    public static String readFromFile(File file) {
        if ((file != null) && file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(fin));
                String value = reader.readLine();
                fin.close();
                return value;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}