/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

/**
 *
 * @author lv
 */
public class Tool {

    public static boolean strContain(String s, String[] strArray) {
        for (int i = 0; i < strArray.length; i++) {
            if (s.toLowerCase().equals(strArray[i].toLowerCase())) {
               return true;
            }
        }
        return false;
    }
}
