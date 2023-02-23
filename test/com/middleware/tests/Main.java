/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.middleware.tests;

import com.middleware.bussines.Student;
import com.middleware.interpret.RegistryDataInterpreter;
import com.middleware.interpret.TeacherDataInterpreter;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        TeacherDataInterpreter teacher = new TeacherDataInterpreter();
        RegistryDataInterpreter registry = new RegistryDataInterpreter();
        String data = "(Action=Create)(name=Alfredo,lastName=Nuñez,residence=Campos De Oca,age=21,qualification=10)(to:Server)(from=Client)";
        System.out.println("Original data\t" + data);
        Thread.sleep(500);
        String data2 = "ACTION:Update,{name:Alfredo|lastName:Alfredo|residence:Campos De Oca|age:21|qualification:10},to:Client,from:Server";
        System.out.println("Original data2\t" + data2);
        Thread.sleep(500);
        Map<String, Object> decodeData = registry.decode(data);
        Map<String, Object> decodeData2 = teacher.decode(data2);
        String encodeData = teacher.encode((String) decodeData.get("Action"), (Student) decodeData.get("Student"), (String) decodeData.get("from"), (String) decodeData.get("to"));
        String encodeData2 = registry.encode((String) decodeData2.get("Action"), (Student) decodeData2.get("Student"), (String) decodeData2.get("from"), (String) decodeData2.get("to"));
        System.out.println("decode Data\t " + decodeData);
        Thread.sleep(500);
        System.out.println("decode Data2\t " + decodeData2);
        Thread.sleep(500);
        System.out.println("encode Data\t " + encodeData);
        Thread.sleep(500);
        System.out.println("encode Data2\t " + encodeData2);
        Thread.sleep(500);
    }

}
