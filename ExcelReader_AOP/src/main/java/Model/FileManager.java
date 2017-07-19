/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import javax.servlet.http.Part;

/**
 *
 * @author admin
 */
public class FileManager implements StorageManager {
    private String path;// = "C:\\Users\\admin\\Documents\\uploads";
    
    public String add(Part filePart) throws Exception {
        final String fileName = randomFileName() + ".xls";
        File excelFile = null;

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            excelFile = new File(path + File.separator + fileName);

            // validate file
            validateSize(excelFile);
            validateFileType(excelFile);

            out = new FileOutputStream(excelFile);
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            // parse excel file
            return path + File.separator + fileName;
        } catch (Exception fne) {
            throw fne;
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }
   
    public Boolean remove(String path) throws Exception {
        File f = new File(path);
        return f.delete();
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
     
    private void validateSize(File excelFile) throws Exception {
        if(excelFile.length() > 1024 * 1024)
            throw new Exception("File size should be less than 1 MB");
    }
    
    private void validateFileType(File excelFile) throws Exception {
        if(!getFileExtension(excelFile).equals("xls"))
            throw new Exception("Invalid file type. Only XLS (2003 - 2007) document supported!");
    }
    
    private String randomFileName() {
        Random rand = new Random(System.currentTimeMillis()); 
        int value = rand.nextInt(50000);
        return String.valueOf(value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
