package com.station.kit.util;

/**
 * author: Sam Leung
 * date:  21-3-3
 */
public class FileSizeFormatter {

    public static String formatFileSize(long bytes){
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (bytes >= gb) {
            return String.format("%.2f GB", (float) bytes / gb);
        } else if (bytes >= mb) {
            float f = (float) bytes / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (bytes >= kb) {
            float f = (float) bytes / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else
            return String.format("%d B", bytes);
    }

    public static String formatFileSizeCeil(long bytes){
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (bytes >= gb) {
            double f = Math.ceil((double)  bytes / gb);
            return String.format("%.2f GB", f);
        } else if (bytes >= mb) {
            double f = Math.ceil((double) bytes / mb);
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (bytes >= kb) {
            double f = Math.ceil((double) bytes / kb);
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else
            return String.format("%d B", bytes);
    }

    public static String formatFileSizeFake(long bytes){
        long kb = 1000;
        long mb = kb * 1000;
        long gb = mb * 1000;

        if (bytes >= gb) {
            double f = (double)  bytes / gb;
            return String.format("%.2f GB", f);
        } else if (bytes >= mb) {
            double f = (double) bytes / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (bytes >= kb) {
            double f =(double) bytes / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else
            return String.format("%d B", bytes);
    }

    public static String formatFileSizeFakeCeil(long bytes){
        long kb = 1000;
        long mb = kb * 1000;
        long gb = mb * 1000;

        if (bytes >= gb) {
            double f = Math.ceil((double)  bytes / gb);
            return String.format("%.2f GB", f);
        } else if (bytes >= mb) {
            double f = Math.ceil((double) bytes / mb);
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (bytes >= kb) {
            double f = Math.ceil((double) bytes / kb);
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else
            return String.format("%d B", bytes);
    }

    public static String formatSpeed(long speedB) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (speedB >= gb) {
            return String.format("%.2f G/s", (float) speedB / gb);
        } else if (speedB >= mb) {
            float f = (float) speedB / mb;
            return String.format(f > 100 ? "%.0f M/s" : "%.2f M/s", f);
        } else if (speedB >= kb) {
            float f = (float) speedB / kb;
            return String.format(f > 100 ? "%.0f K/s" : "%.2f K/s", f);
        } else
            return String.format("%d B/s", speedB);
    }
}
