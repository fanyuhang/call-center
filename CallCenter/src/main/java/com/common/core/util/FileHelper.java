package com.common.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>文件处理类</p>
 */
public class FileHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    static private final int BUF_SIZE = 1024 * 10;

    private static String webRoot = "";

    // 保存的文件名，当上传文件为分块多次请求时，需要确保文件名唯一,
    private static Map<String, String> savedFileNameCache = new HashMap<String, String>();

    /**
     * 上传文件
     *
     * @param is       文件输入流
     * @param fullName 保存文件绝对路径
     * @return
     */
    private static boolean uploadFile(InputStream is, String fullName, boolean isAppend) throws Exception {
        int chunkSize = 50 * 1024 * 1024;
        try {
            OutputStream bos = new FileOutputStream(fullName, isAppend);
            int bytesRead = 0;
            byte[] buffer = new byte[chunkSize];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            is.close();
            return true;
        } catch (Exception e) {
            logger.error("上传文件时发生错误：{}", e);
            throw e;
        }
    }

    /**
     * 上传文件
     *
     * @param is       文件输入流
     * @param fullName 保存文件绝对路径
     * @return
     */
    private static boolean uploadFile(InputStream is, String fullName) throws Exception {
        try {
            OutputStream bos = new FileOutputStream(fullName);
            int bytesRead = 0;
            byte[] buffer = new byte[BUF_SIZE];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            is.close();
            return true;
        } catch (Exception e) {
            logger.error("上传文件时发生错误：{}", e);
            throw e;
        }
    }

    /**
     * 获取WebRoot绝对路径
     *
     * @return
     */
    public static String getServerWebRoot() {
        return webRoot;
    }

    /**
     * 获取文件的后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffiex(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffiex = "";
        if (index >= 0)
            suffiex = fileName.substring(index);
        return suffiex;
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param filePath
     * @param response
     * @throws java.io.IOException
     */
    public static void downloadFile(String fileName, String filePath,
                                    HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/x-msdownload;charset=gb2312");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

        // 设置响应头和下载保存的文件名
        String webroot = FileHelper.getServerWebRoot();
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(webroot + filePath));
        byte[] buf = new byte[10 * 1024];
        int len = 0;
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }

    /**
     * 获取文件除去后缀后的名字
     *
     * @param fileName
     * @return
     */
    public static String getFileDisplayName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1)
            return fileName;
        String displayName = fileName.substring(0, index);
        return displayName;
    }

    /**
     * <p>上传文件<p>
     *
     * @param request
     * @param response
     * @return 保存在文件系统的文件名，参照：{@code 文件名+<_yyyyMMddHHmmss>.<后缀>}
     * @see <p>仅适用于plupload或者基于其实现的上传操作客户端</p>
     *      <p>modify at 2013-01-17 for use spring MVC CommonsMultipartResolver</p>
     */
    @SuppressWarnings("rawtypes")
    public static String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filename = null;
        String savedFileName = null;
        String dirPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload" + File.separator;
        // 当前正在处理的文件分块序号
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        int chunk = Integer.valueOf(multipartRequest.getParameter("chunk"));
        //分块上传总数
        int chunks = Integer.valueOf(multipartRequest.getParameter("chunks"));
        // 判断当前表单是否为"multipart/form-data"
        if (commonsMultipartResolver.isMultipart(request)) {
            try {
                Iterator i = multipartRequest.getFileNames();
                while (i.hasNext()) {
                    multipartRequest.getAttribute("chunk");
                    MultipartFile f = multipartRequest.getFile((String) i.next());
                    InputStream input = f.getInputStream();

                    // 文件名
                    filename = f.getOriginalFilename();
                    if (!savedFileNameCache.containsKey(filename)) {
                        savedFileNameCache.put(filename, getSavedFileName(filename));
                    }
                    // 保存文件目录绝对路径
                    File dir = new File(dirPath);
                    if (!dir.isDirectory() || !dir.exists()) {
                        dir.mkdirs();
                    }

                    //保存文件绝对路径
                    String fullPath = dirPath + "/" + savedFileNameCache.get(filename);
                    if (chunk == 0) {
                        File file = new File(fullPath);
                        if (file.exists()) {
                            file.delete();
                        }
                        //上传文件
                        FileHelper.uploadFile(input, fullPath);
                    }
                    if (chunk > 0) {
                        //追加文件
                        FileHelper.uploadFile(input, fullPath, true);
                    }
                    if (chunk + 1 == chunks || chunks == 0) {
                        savedFileName = dirPath + "/" + savedFileNameCache.get(filename);
                        savedFileNameCache.remove(filename);
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("上传文件时发生错误：{}", e);
                e.printStackTrace();
                throw e;
            }
        }

        return savedFileName;
    }

    /**
     * <p>获取文件系统保存的文件名</p>
     *
     * @param filename
     * @return
     */
    private static String getSavedFileName(String filename) {
        StringBuffer sb = new StringBuffer();
        String suffic = filename.substring(filename.lastIndexOf("."));
        String file = filename.substring(0, filename.lastIndexOf("."));
        sb.append(file).append("_").append(System.currentTimeMillis())
                .append(suffic);
        return sb.toString();
    }
}