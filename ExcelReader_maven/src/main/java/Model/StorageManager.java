/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.servlet.http.Part;

/**
 *
 * @author admin
 */
public interface StorageManager {
    public String add(Part filePart) throws Exception;
    public Boolean remove(String path) throws Exception;
}
