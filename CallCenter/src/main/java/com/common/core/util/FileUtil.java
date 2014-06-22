package com.common.core.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

public class FileUtil {

    public static final String TEMP_DIRECTORY_NAME_PROVISIONS = "provisions";

    public static final String DIRECTORY_NAME_TEMPLATE = "template";

    public static String getProvisionsTempDirectory(HttpServletRequest request, String batchNo) {
        ServletContext context = request.getSession().getServletContext();
        String path = context.getRealPath("/") + File.separator + TEMP_DIRECTORY_NAME_PROVISIONS + File.separator + batchNo + File.separator;
        return path;
    }

    public static String getProvisionsTempDirectory(HttpServletRequest request) {
        ServletContext context = request.getSession().getServletContext();
        String path = context.getRealPath("/") + File.separator + TEMP_DIRECTORY_NAME_PROVISIONS + File.separator;
        return path;
    }

    public static String getTemplateDirectory(HttpServletRequest request) {
        ServletContext context = request.getSession().getServletContext();
        String path = context.getRealPath("/") + File.separator + "WEB-INF" + File.separator + DIRECTORY_NAME_TEMPLATE + File.separator;
        return path;
    }

    public static String getProvisionsReportName(Date date, String number) {
        if (date == null) {
            return "M_" + DateFormatUtils.format(new Date(), "yyyyMM") + "_上海瑞得服务企业有限公司_T" + number;
        }
        return "M_" + DateFormatUtils.format(date, "yyyyMM") + "_上海瑞得服务企业有限公司_T" + number;
    }

    public static List<FileItem> fileUpload(HttpServletRequest request) throws FileUploadException {
        try {
            ServletContext context = request.getSession().getServletContext();
            String path = context.getRealPath("/") + "temp/";
            File tempPath = new File(path);
            if (!tempPath.exists()) {
                tempPath.mkdirs();
            }
            DiskFileItemFactory dff = new DiskFileItemFactory();
            dff.setSizeThreshold(1024 * 1024);
            dff.setRepository(tempPath);
            ServletFileUpload fu = new ServletFileUpload(dff);
            fu.setFileSizeMax(Long.parseLong(ResourceRead.getValueByKey("upload.fileSizeMax")));
            List<FileItem> items = fu.parseRequest(request);
            return items;
        } catch (FileSizeLimitExceededException e) {
            throw e;
        }


    }
}
