package com.hsbank.util.java.constant;

/**
 * 下载文件类型_枚举类
 * @author xwy
 * 2011-04-01
 */
public enum DownloadFileType {
	/** 文件类型：默认 */
    DEFAULT("", "application/x-download"),
    /** 文件类型：TXT */
    TXT(".txt", "text/plain"),
    /** 文件类型：CSV */
    CSV(".csv", "application/msexcel"),    
    /** 文件类型：XLS */
    XLS(".xls", "application/msexcel"),
    /** 文件类型：XLSX */
    XLSX(".xlsx", "application/msexcel"),
    /** 文件类型：DOC */
    DOC(".doc", "application/word"),
    /** 文件类型：DOCX */
    DOCX(".docx", "application/word"),
    /** 文件类型：PDF */
    PDF(".pdf", "application/pdf"),
    /** 文件类型：ZIP */
    ZIP(".zip", "application/zip");

    /** Content类型 */
    private String contentType;
    /** 文件扩展名 */
    private String fileExt;

    /**
     * 构造函数
     * 
     * @param fileExt
     *            文件扩展名
     * @param contentType
     *            Content类型
     */
    DownloadFileType(String fileExt, String contentType) {
        this.fileExt = fileExt;
        this.contentType = contentType;
    }

    /**
     * 返回Content类型
     * @return Content类型
     */
    public String contentType() {
        return contentType;
    }

    /**
     * 返回文件扩展名
     * @return 文件扩展名
     */
    public String fileExt() {
        return fileExt;
    }

}
