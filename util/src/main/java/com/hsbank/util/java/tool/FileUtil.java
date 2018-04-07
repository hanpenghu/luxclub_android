package com.hsbank.util.java.tool;

import android.util.Log;

import com.hsbank.util.java.JavaConstant;
import com.hsbank.util.java.type.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 文件操作公共类
 * @author Arthur.Xie
 * CreateDate 2006-12-28
 */
public class FileUtil {
	/**文件大小单位：byte*/
	public static final String UNIT_BYTE = "b";
	/**文件大小单位：kb*/
	public static final String UNIT_KB = "kb";
	/**文件大小单位：mb*/
	public static final String UNIT_MB = "mb";
	/**文件大小单位：gb*/
	public static final String UNIT_GB = "gb";

	/*文件过滤器定义示例：
	private static FileFilter fileFilter = new FileFilter() {
        public boolean accept(File file) {
        	boolean bResult = true;
            String fileName = file.getName().toLowerCase(Locale.getDefault());
            if (fileName.endsWith(".scc")) {
                bResult = false;
            }
            return bResult;
        }
    };
    */
	
	public static void main(String[] args) {
	}
		
	/**构造函数*/
	public FileUtil() {
	}
		
	/**
	 * 创建文件
	 * @param filePathName 文件路径
	 * @return File
	 */
	public static File createFile(String filePathName) {
		File resultValue = null;
		try {
			createDirByFilePathName(filePathName);
			resultValue = new File(filePathName);
			if (!resultValue.exists()) {
				resultValue.createNewFile();
			}
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
		return resultValue;
	}
	
	/**
	 * 读文件
	 * @param filePathName 文件路径
	 * @return String 文件内容
	 */
	public static String readFile(String filePathName) {
		return readFile(filePathName, "");
	}
	
	/**
	 * 读文件
	 * @param filePathName 文件路径
	 * @param strTag	每行用一个指定的标签隔开
	 * @param encoding 字符编码
	 * @return String 文件内容
	 */
	public static String readFileWithEncoding(String filePathName, String strTag, String encoding) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathName), encoding));		   
			String strLine = null;
			while((strLine = br.readLine()) != null) {
				sb.append(strLine).append(strTag);
			}			
		} catch (FileNotFoundException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 读文件
	 * 得到文件内容，用一个指定的标签隔开
	 * @param filePathName
	 * @param strTag
	 * @return String 
	 * @exception 
	 */
	public static String readFile(String filePathName, String strTag) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {			
		    br = new BufferedReader(new FileReader(filePathName));
			String strLine = null;
			while((strLine = br.readLine()) != null) {
				sb.append(strLine).append(strTag);
			}			
		} catch (FileNotFoundException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 写文件
	 * 如果文件不存在，则创建文件
	 * @param filePathName 			文件路径名称
	 * @param fileContent 			文件内容
	 */
	public static void writeFile(String filePathName, String fileContent) {
		BufferedReader br = null;
    	PrintWriter pw = null;
		try {
			if (isExistFile(filePathName) == false) {
				createFile(filePathName);
			}
			br = new BufferedReader(new StringReader(fileContent));
            pw = new PrintWriter(new BufferedWriter(new FileWriter(filePathName, true)));
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                pw.println(strLine);
            }       
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
			if (pw != null) {
				pw.close();
			}
		}
	}	
	
	/**
	 * 写文件
	 * 如果文件不存在，则创建文件
	 * @param filePathName			文件路径名称
	 * @param fileContent			文件内容
	 * @param encoding				文件编码
	 */
	public static void writeFile(String filePathName, String fileContent, String encoding) {
		BufferedReader br = null;
		OutputStreamWriter os = null;
		try {
			if (isExistFile(filePathName) == false) {
				createFile(filePathName);
			}
			br = new BufferedReader(new StringReader(fileContent));			
			os = new OutputStreamWriter(new FileOutputStream(filePathName), encoding);
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                os.write(strLine + "\n");
            }       
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
	}
	
	/**
	 * 追加指定内容到指定文件
	 * @param filePathName
	 * @param fileContent
	 */
	public static void appendFile(String filePathName, String fileContent) {
		try {
			createFile(filePathName);
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(filePathName, true);
			writer.write(fileContent);
			writer.close();
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
	}
	
	/**
	 * 追加指定内容到指定文件
	 * @param f
	 * @param fileContent
	 */
	public static void appendFile(File f, String fileContent) {
		appendFile(f.getAbsolutePath(), fileContent);
	}

	/**
     * 得到指定“文件路径名称”的文件的大小（byte）
     * @param filePathName 文件路径名称
     * @return 文件大小
     */
    public static int getFileSize(String filePathName) {
        int returnValue = 0;
        if (isExistFile(filePathName)) {
        	File f = new File(filePathName);
			FileInputStream fs = null;
			try {
				fs = new FileInputStream(f);
				returnValue = fs.available();
			} catch (Exception e) {
				Log.e(FileUtil.class.getName(), e.getMessage(), e);
			} finally {
				try {
					if (fs != null) {
						fs.close();
					}
				} catch (Exception e) {
					Log.e(FileUtil.class.getName(), e.getMessage(), e);
				}				
			}
        }
        return returnValue;
    }
    
    /**
     * 处理路径
	 * 诸如“G:\\aa/ddd\\中文目录/中文文件名.txt”之类的路径，需要把文件分隔符统一为当前操作系统的文件分隔符
     * @param path
     * @return
     */
    public static String dealPath(String path) {
    	path = dealPathName(path);
    	return path.endsWith(JavaConstant.LOCAL_FILE_SEPARATOR) ? path : path + JavaConstant.LOCAL_FILE_SEPARATOR;
    }
    
    /**
     * 处理路径
	 * 诸如“G:\\aa/ddd\\中文目录/中文文件名.txt”之类的路径，需要把文件分隔符统一为当前操作系统的文件分隔符
     * @param path
     * @return
     */
    public static String dealPathName(String path) {
    	path = StringUtil.dealString(path);
    	if ("/".equals(JavaConstant.LOCAL_FILE_SEPARATOR)) {
    		path = path.replaceAll("\\\\", "/");
    	} else {
    		path = path.replaceAll("/", "\\\\");
    	}
    	return path;
    }
    
    /**
     * 得到文件名称
     * @param filePathName
     * @return
     */
    public static String getFileName(String filePathName) {
    	filePathName = dealPathName(filePathName);
        int pos = 0;
        //"/"的ASCII码是47  
        pos = filePathName.lastIndexOf(47);
        if (pos != -1) {
            return filePathName.substring(pos + 1, filePathName.length());
        }
        //"\"的ASCII码是92 
        pos = filePathName.lastIndexOf(92);
        if (pos != -1) {
            return filePathName.substring(pos + 1, filePathName.length());
        } else {
            return filePathName;
        }
    }
    
    /**
     * 得到目录路径
     * @param filePathName
     * @return
     */
    public static String getDirPath(String filePathName) {
    	filePathName = dealPathName(filePathName);
    	int pos = 0;
        //"/"的ASCII码是47  
        pos = filePathName.lastIndexOf(47);
        if (pos != -1) {
            return filePathName.substring(0, pos);
        }
        //"\"的ASCII码是92 
        pos = filePathName.lastIndexOf(92);
        if (pos != -1) {
            return filePathName.substring(0, pos);
        } else {
            return filePathName;
        }
    }

	/**
	 * 得到指定文件的扩展名
	 * @param filePathName	文件路径名称
	 * @return 如“jpg”、“png”、“gif”
	 */
	public static String getFileExt(String filePathName) {
		String resultValue = "";
		filePathName = filePathName == null ? "" : filePathName.trim();
		//"."的ASCII码是46
		if (filePathName.lastIndexOf(46) > 0) {
			resultValue = filePathName.substring(filePathName.lastIndexOf(46) + 1);
		}
		return resultValue;
	}
	
	/**
     * 文件是否存在
     * @param filePathName 文件路径
     * @return 存在则返回true，否则返回false
     */
    public static boolean isExistFile(String filePathName) {
        boolean bResult = false;
        File file = new File(filePathName);
        if (file.exists()) {
            bResult = true;
        }        
        return bResult;
    }
    
    /**
     * 复制文件：源文件和目标文件使用相同的名称
     * @param sourceDirPath 		源目录路径
     * @param destDirPath 		目标目录路径           
     * @param fileName 	  			文件名称
     * @param bReplace 				文件已存在时的替换标志(true为替换、false为不替换)
     * @return 0 -- 复制成功；
     * 			1 -- 源文件不存在；
     * 			2 -- 目标文件已存在，且不替换；
     * 			3 -- 其它错误
     */ 
    public static int copyFileByName(String sourceDirPath, String destDirPath, String fileName, boolean bReplace) {
    	//<1>.===============检查源文件是否存在===============
    	//源文件路径名称
    	String sourceFilePathName = sourceDirPath + JavaConstant.LOCAL_FILE_SEPARATOR + fileName;
    	if (!isExistFile(sourceFilePathName)) {
    		//源文件不存在
    		Log.d(FileUtil.class.getName(), "The sourceFile isn't exist. sourceFilePathName = " + sourceFilePathName);
    		return 1;
    	}
    	//<2>.===============检查目标文件是否存在===============
    	//目标文件路径名称
    	String destFilePathName = destDirPath + JavaConstant.LOCAL_FILE_SEPARATOR + fileName;
		File destFile = new File(destFilePathName);
	    if ((destFile.exists()) && (bReplace == false)) {
	    	//目标文件已存在，且不替换
	        Log.d(FileUtil.class.getName(), "The destFile is exist. destFilePathName = " + destFilePathName);
	        return 2;
	    }
	    //<3>.===============创建目标文件===============
	    createFile(destFilePathName);
	    //<4>.===============复制文件===============
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
			fis = new FileInputStream(sourceFilePathName);
			fos = new FileOutputStream(destFilePathName);
			int i = -1;
			byte[] b = new byte[1024];
			while ((i = fis.read(b)) > -1) {
				fos.write(b, 0, i);
				b = new byte[1024];
			}
			//复制成功
			return 0;
		} catch (FileNotFoundException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
            try {
            	if (fos != null) {
            		fos.close();
            	}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
	    //其它错误
	    return 3;
    }
    
    /**
     * 复制文件：源文件和目标文件可以使用不同的名称
     * @param sourceFilePathName 	源文件路径名称
     * @param destDirPath 		目标目录路径           
     * @param destFileName 	  		目标文件名称
     * @param bReplace 				目标文件已存在时的替换标志(true为替换、false为不替换)
     * @return 0 -- 复制成功；
     * 			1 -- 源文件不存在；
     * 			2 -- 目标文件已存在，且不替换；
     * 			3 -- 其它错误
     */ 
    public static int copyFile(String sourceFilePathName, String destDirPath, String destFileName, boolean bReplace) {
    	//<1>.===============检查源文件是否存在===============
    	//源文件路径名称
    	if (!isExistFile(sourceFilePathName)) {
    		//源文件不存在
    		Log.d(FileUtil.class.getName(), "The sourceFile is exist. sourceFilePathName = " + sourceFilePathName);
    		return 1;
    	}
    	//<2>.===============检查目标文件是否存在===============
    	//目标文件路径名称
    	String destFilePathName = destDirPath + JavaConstant.LOCAL_FILE_SEPARATOR + destFileName;
		File destFile = new File(destFilePathName);
	    if ((destFile.exists()) && (bReplace == false)) {
	    	//目标文件已存在，且不替换
	        Log.d(FileUtil.class.getName(), "The destFile is exist. destFilePathName = " + destFilePathName);
	        return 2;
	    }
	    //<3>.===============创建目标文件===============
	    createFile(destFilePathName);
	    //<4>.===============复制文件===============
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
			fis = new FileInputStream(sourceFilePathName);
			fos = new FileOutputStream(destFilePathName);
			int i = -1;
			byte[] b = new byte[1024];
			while ((i = fis.read(b)) > -1) {
				fos.write(b, 0, i);
				b = new byte[1024];
			}
			//复制成功
			return 0;
		} catch (FileNotFoundException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
            try {
            	if (fos != null) {
            		fos.close();
            	}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
	    //其它错误
	    return 3;
    }
    
    /**
     * 复制文件：源文件和目标文件可以使用不同的名称
     * @param sourceFilePathName 	源文件路径名称       
     * @param destFilePathName 		目标文件路径名称
     * @param bReplace 				目标文件已存在时的替换标志(true为替换、false为不替换)
     * @return 0 -- 复制成功；
     * 			1 -- 源文件不存在；
     * 			2 -- 目标文件已存在，且不替换；
     * 			3 -- 其它错误
     */ 
    public static int copyFile(String sourceFilePathName, String destFilePathName, boolean bReplace) {
    	//<1>.===============检查源文件是否存在===============
    	//源文件路径名称
    	if (!isExistFile(sourceFilePathName)) {
    		//源文件不存在
    		Log.d(FileUtil.class.getName(), "The sourceFile is exist. sourceFilePathName = " + sourceFilePathName);
    		return 1;
    	}
    	//<2>.===============检查目标文件是否存在===============
    	//目标文件路径名称
		File destFile = new File(destFilePathName);
	    if ((destFile.exists()) && (bReplace == false)) {
	    	//目标文件已存在，且不替换
	        Log.d(FileUtil.class.getName(), "The destFile is exist. destFilePathName = " + destFilePathName);
	        return 2;
	    }
	    //<3>.===============创建目标文件===============
	    createFile(destFilePathName);
	    //<4>.===============复制文件===============
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
			fis = new FileInputStream(sourceFilePathName);
			fos = new FileOutputStream(destFilePathName);
			int i = -1;
			byte[] b = new byte[1024];
			while ((i = fis.read(b)) > -1) {
				fos.write(b, 0, i);
				b = new byte[1024];
			}
			//复制成功
			return 0;
		} catch (FileNotFoundException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} catch (IOException e) {
			Log.d(FileUtil.class.getName(), "", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
            try {
            	if (fos != null) {
            		fos.close();
            	}
			} catch (IOException e) {
				Log.d(FileUtil.class.getName(), "", e);
			}
		}
	    //其它错误
	    return 3;
    }
    
    /**
     * 移动文件(强制替换已存在文件)
     * @param srcFilePathName 		源文件路径
     * @param destFilePathName 		目标文件路径
     * @return boolean 				操作成功则返回true，否则返回false
     */
    public static boolean moveFile(String srcFilePathName, String destFilePathName) {
        boolean bResult = false;
        try {
            File srcFile = new File(srcFilePathName);
            File destFile = new File(destFilePathName);
            createDirByFilePathName(destFilePathName);
            if (destFile.exists()) {
                destFile.delete();
            }
            bResult = srcFile.renameTo(destFile);
        } catch(Exception e) {
            Log.d(FileUtil.class.getName(), "", e);
        }
        return bResult;
    }
        
    /**
     * 删除文件
     * @param filePathName 文件路径
     */
    public static void deleteFile(String filePathName) {
		try {
			File file = new File(filePathName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
	}
    
	/**
	 * 创建目录
	 * @param dirPathName
	 * @return File
	 */
	public static File createDir(String dirPathName) {
		File resultValue = null;
		try {
			File dir = new File(dirPathName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
		return resultValue;
	}
	
	/**
	 * 根据给定的文件路径名称逐级创建目录
	 * @param filePathName
	 * @return File
	 */
	public static void createDirByFilePathName(String filePathName) {
		String dirPath = getDirPath(filePathName);
		if (!"".equals(dirPath)) {
			createDir(dirPath);
		}		
	}
	
    /**
     * 目录是否存在
     * @param folerPathName 目录路径
     * @return 存在则返回true，否则返回false
     */
    public static boolean isExistDir(String folerPathName) {
        boolean bResult = false;
        File dir = new File(folerPathName);
        if (dir.exists()) {
            bResult = true;
        } 
        return bResult;
    }
    
    /**
     * 复制目录
     * @param sourceDirPathName 源目录路径 
     * @param destDirPathName 目标目录路径
     * @param bReplace 目标文件已存在时的替换标志(true为替换、false为不替换)
     */
    public static void copyDir(String sourceDirPathName, String destDirPathName, boolean bReplace) {
		try {
			createDir(destDirPathName);
			File sourceDir = new File(sourceDirPathName);
			File[] fileList = sourceDir.listFiles();
			int iLength = fileList.length;
			for (int i = 0; i < iLength; i++) {
				File file = fileList[i];
				if (file.isFile()) {
					String destFilePath = destDirPathName + JavaConstant.LOCAL_FILE_SEPARATOR + file.getName();
					copyFile(file.getAbsolutePath(), destFilePath, bReplace);
				} else if (file.isDirectory()) {
					String subSourceDirPath = sourceDirPathName + JavaConstant.LOCAL_FILE_SEPARATOR + file.getName();
					String subDestDirPath = destDirPathName + JavaConstant.LOCAL_FILE_SEPARATOR + file.getName();
					copyDir(subSourceDirPath, subDestDirPath, bReplace);
				}
			}
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
	}

    /**
     * 移动目录(强制替换目录内已经存在的文件)
     * @param sourceDirPathName 源目录路径 
     * @param destDirPathName 目标目录路径
     */
    public static void moveDir(String sourceDirPathName, String destDirPathName) {
		copyDir(sourceDirPathName, destDirPathName, true);
		deleteDir(sourceDirPathName);
	}
    
    /**
     * 删除指定目录中的所有文件
     * @param dirPathName
     */
    public static void deleteAllFiles(String dirPathName) {	
		File dir = new File(dirPathName);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File[] fileList = dir.listFiles();
				int iLenght = fileList.length;
				for (int i = 0; i < iLenght; i++) {
					if (fileList[i].isFile()) {
						fileList[i].delete();
					} else {
						deleteAllFiles(fileList[i].getAbsolutePath());
						deleteDir(fileList[i].getAbsolutePath());
					}
				}
			}
		}
	}
    
    /**
     * 删除指定目录及其中的所有文件
     * @param dirPathName
     */
    public static void deleteDir(String dirPathName) {
		try {
			deleteAllFiles(dirPathName);
			File file = new File(dirPathName);
			file.delete();
		} catch (Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
	}
    
    /**
     * 文件排序
     * @param fileArray 待排序文件数组
     * @param sortTag 排序标识(name -- 按名称； date -- 按最后修改日期； 其它则按文件大小)
     * @param bOrder 排序规则(true为升序，false为降序)
     * @return File[] 排序的文件数组  
     * 
     */ 
    public static File[] sortFile(File[] fileArray, String sortTag, boolean bOrder) {
    	int iLength = fileArray.length;
        if (bOrder == true) {
            for (int i = iLength; i > 1; i--) {
                for (int j = 0; j < i - 1; j++) {
                    boolean bChange = false;
                    if (sortTag.equals("name")) {
                    	//按文件名进行排序
                        if (fileArray[j].getName().compareTo(fileArray[j + 1].getName()) > 0)
                            bChange = true;
                    } else if (sortTag.equals("date")) {
                    	//按日期对文件进行排序
                        if (fileArray[j].lastModified()> fileArray[j + 1].lastModified())
                            bChange = true;
                    } else {
                    	//按文件大小进行排序
                        if (fileArray[j].length() > fileArray[j + 1].length())
                            bChange = true;
                    }
                    if (bChange) {
                         File tempFile = fileArray[j];
                         fileArray[j] = fileArray[j + 1];
                         fileArray[j + 1] = tempFile;
                    }
                }
            }
        } else {
            for (int i = iLength; i > 1; i--) {
                for (int j = 0; j < i - 1; j++) {
                    boolean bChange = false;
                    if (sortTag.equals("name")) {
                    	//按文件名进行排序
                        if (fileArray[j].getName().compareTo(fileArray[j + 1].getName()) < 0)
                            bChange = true;
                    } else if (sortTag.equals("date")) {
                    	//按日期对文件进行排序
                        if (fileArray[j].lastModified() < fileArray[j + 1].lastModified())
                            bChange = true;
                    } else {
                    	//按文件大小进行排序
                        if (fileArray[j].length() < fileArray[j + 1].length())
                            bChange = true;
                    }
                    if (bChange) {
                         File temp = fileArray[j];
                         fileArray[j] = fileArray[j + 1];
                         fileArray[j + 1] = temp;
                    }
                }
            }            
        }
        return fileArray;
    }

    /**
	 * 取得指定目录下所有文件列表
	 * @param destDirPathName 指定目录路径名称
	 */
	public static List<File> getFileListOfDir(String destDirPathName) {
		List<File> returnValue = new ArrayList<File>();
		File tempFile = new File(destDirPathName);
		if (tempFile.isDirectory()) {			
			File[] subFileList = tempFile.listFiles();
			int iLength = subFileList.length;			
			for (int i = 0; i < iLength; i++) {
				returnValue.add(subFileList[i]);
			}					
		} 
		return returnValue;
	}
   
	/**
	 * 循环取得指定目录下所有文件列表
	 * @param destDirName 指定目录名称
	 * @param destDirPathName 指定目录路径名称
	 * @param resultFileList 文件列表
	 */
	public static void getFileListOfDir(String destDirName, String destDirPathName, List<File> resultFileList) {
		File tempFile = new File(destDirPathName);
		if (tempFile.isDirectory()) {			
			File[] subFileList = tempFile.listFiles();
			int iLength = subFileList.length;
			if (iLength > 0) {
				for (int i = 0; i < iLength; i++) {
					getFileListOfDir(destDirName, subFileList[i].getAbsolutePath(), resultFileList);
				}
			} else {
				//空目录也要添加进来
				resultFileList.add(tempFile);
			}			
		} else {
			resultFileList.add(tempFile);
		}
	}
	
	/**
	 * 循环取得指定目录下所有文件列表
	 * @param destDirName 指定目录名称
	 * @param destDirPathName 指定目录路径名称
	 * @param resultFileList 文件列表
	 * @param sourceRelativeFilePath 文件相对于指定目录的相对路径的列表
	 */
	public static void getFileListOfDir(String destDirName, String destDirPathName, 
		List<File> resultFileList, List<String> sourceRelativeFilePath) {
		File tempFile = new File(destDirPathName);
		if (tempFile.isDirectory()) {			
			File[] subFileList = tempFile.listFiles();
			int iLength = subFileList.length;
			if (iLength > 0) {
				for (int i = 0; i < iLength; i++) {
					getFileListOfDir(destDirName, subFileList[i].getAbsolutePath(), resultFileList, sourceRelativeFilePath);
				}
			} else {
				//空目录也要添加进来
				resultFileList.add(tempFile);
				String dirPath = tempFile.getAbsolutePath();
				String relativeDirPath = dirPath.substring(dirPath.lastIndexOf(JavaConstant.LOCAL_FILE_SEPARATOR + destDirName + JavaConstant.LOCAL_FILE_SEPARATOR) + destDirName.length() + 2, dirPath.length());
				//加一个文件分隔符，标识这是一个目录
				sourceRelativeFilePath.add(relativeDirPath + JavaConstant.LOCAL_FILE_SEPARATOR);
			}			
		} else {
			resultFileList.add(tempFile);
			String filePath = tempFile.getAbsolutePath();
			String relativeFilePath = filePath.substring(filePath.lastIndexOf(JavaConstant.LOCAL_FILE_SEPARATOR + destDirName + JavaConstant.LOCAL_FILE_SEPARATOR) + destDirName.length() + 2, filePath.length());
			sourceRelativeFilePath.add(relativeFilePath);
		}
	}
	
	/**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param rootDirectory
     * @param subDirectory
	 */
	private static void createDirectory(String rootDirectory, String subDirectory) {
		String dir[];
		File rootDir = new File(rootDirectory);
		try {
			if (subDirectory == "" && rootDir.exists() != true) {
				rootDir.mkdir();
			} else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				int iLength = dir.length;
				for (int i = 0; i < iLength; i++) {
					File subFile = new File(rootDirectory + File.separator + dir[i]);
					if (subFile.exists() == false) {
						subFile.mkdir();
					}
					rootDirectory += File.separator + dir[i];
				}
			}
		} catch(Exception e) {
			Log.d(FileUtil.class.getName(), "", e);
		}
	}

	/**
	 * 格式化文件大小
	 * @param fileSize
	 * @return
	 */
	public static String formatFileSize(long fileSize) {
		String resultValue = "0" + UNIT_BYTE;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		if (fileSize <= 0) {
		} else if (fileSize < 1024) {
			resultValue = decimalFormat.format((double) fileSize) + UNIT_BYTE;
		} else if (fileSize < 1048576) {
			resultValue = decimalFormat.format((double) fileSize / 1024) + UNIT_KB;
		} else if (fileSize < 1073741824) {
			resultValue = decimalFormat.format((double) fileSize / 1048576) + UNIT_MB;
		} else {
			resultValue = decimalFormat.format((double) fileSize / 1073741824) + UNIT_GB;
		}
		return resultValue;
	}

	/**
	 * 格式化文件大小
	 * @param fileSize
	 * @param unit
	 * @return
	 */
	public static double formatFileSize(long fileSize, String unit) {
		double resultValue = 0;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		unit = StringUtil.dealString(unit).toLowerCase(Locale.getDefault());
		if (unit.equals(UNIT_BYTE)) {
			resultValue = Double.valueOf(decimalFormat.format((double) fileSize));
		} else if (unit.equals(UNIT_KB)) {
			resultValue = Double.valueOf(decimalFormat.format((double) fileSize / 1024));
		} else if (unit.equals(UNIT_MB)) {
			resultValue = Double.valueOf(decimalFormat.format((double) fileSize / 1048576));
		} else if (unit.equals(UNIT_GB)) {
			resultValue = Double.valueOf(decimalFormat.format((double) fileSize / 1073741824));
		} else {
			resultValue = Double.valueOf(decimalFormat.format((double) fileSize));
		}
		return resultValue;
	}
}
