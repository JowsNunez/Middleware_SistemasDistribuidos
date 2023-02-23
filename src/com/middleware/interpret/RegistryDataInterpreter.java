
package com.middleware.interpret;

import com.middleware.builders.StudentBuilder;
import com.middleware.bussines.Student;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class RegistryDataInterpreter implements interpreter{

     // ("Action"="CREATE")("atr=val")(to=a)(from=a)
    @Override
    public String encode(String action, Student data, String to, String from) {
        StringBuilder sb = new StringBuilder();
        sb.append("(Action=").append(action).append(")");
        sb.append("(idStudent=").append(data.getIdStudent());
        sb.append(",name=").append(data.getName());
        sb.append(",lastName=").append(data.getLastName());
        sb.append(",residence=").append(data.getResidence());
        sb.append(",age=").append(data.getAge());
        sb.append(",qualification=").append(data.getQualification());
        sb.append(")(to=").append(to).append(")");
        sb.append("(from=").append(from).append(")");
        return sb.toString();
    }

    @Override
    public Map<String, Object> decode(String data) {
        Map<String, Object> msgDecode = new HashMap<>();
        String delimiter[] = data.split("\\)\\(");
       
        StudentBuilder sb = new StudentBuilder();

        String action = delimiter[0].substring("\\(Action=".length()-1, delimiter[0].length());
       
        String[] attributes = delimiter[1].split(",");
         
        for (String attribute : attributes) {
            
            String[] parts = attribute.split("=");
            String attributeName = parts[0];
            String attributeValue = parts[1];
           if (attributeName.equals("idStudent")) {
                sb.setIdStudent(attributeValue);
            } else if (attributeName.equals("name")) {
                sb.setName(attributeValue);
            } else if (attributeName.equals("lastName")) {
                sb.setLastName(attributeValue);
            } else if (attributeName.equals("residence")) {
                sb.setResidence(attributeValue);
            } else if (attributeName.equals("age")) {
                sb.setAge(Integer.parseInt(attributeValue));
            } else if (attributeName.equals("qualification")) {
                sb.setQualification(Integer.parseInt(attributeValue));
            } else {
                System.out.println("Atributo no válido: " + attributeName);
            }
        }

        String to = delimiter[2].substring("(to=".length()-1, delimiter[2].length());
        String from = delimiter[3].substring("(from=".length()-1, delimiter[3].length()-1);
        

        msgDecode.put("Action", action);
        msgDecode.put("Student", sb.build());
        msgDecode.put("to", to);
        msgDecode.put("from", from);
        

        return msgDecode;
    }
    
}
