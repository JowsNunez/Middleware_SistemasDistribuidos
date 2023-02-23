
package com.middleware.interpret;

import com.middleware.builders.StudentBuilder;
import com.middleware.bussines.Student;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class TeacherDataInterpreter implements interpreter {

    // formato ACTION:CREATE,{Name:Alfredo|Id:12828|Score:122},to:destiny,from:who?
    @Override
    public Map<String, Object> decode(String data) {
        Map<String, Object> msgDecode = new HashMap<>();
        String delimiter[] = data.split(",");

        StudentBuilder sb = new StudentBuilder();

        String action = delimiter[0].substring("ACTION:".length());
        String obj = delimiter[1].substring(1, delimiter[1].length() - 1); // eliminar llaves

        String[] attributes = obj.split("\\|");
        for (String attribute : attributes) {
            String[] parts = attribute.split(":");
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
                sb.setAge(Integer.valueOf(attributeValue));
            } else if (attributeName.equals("qualification")) {
                sb.setQualification(Integer.valueOf(attributeValue));
            } else {
                System.out.println("Atributo no valido");
            }
        }

        String to = delimiter[2].substring("to:".length(), delimiter[2].length());
        String from = delimiter[3].substring("from:".length(), delimiter[3].length());

        msgDecode.put("Action", action);
        msgDecode.put("Student", sb.build());
        msgDecode.put("to", to);
        msgDecode.put("from", from);

        return msgDecode;
    }

    @Override
    public String encode(String action, Student data, String to, String from) {
        StringBuilder sb = new StringBuilder();
        sb.append("ACTION:").append(action);
        sb.append(",{idStudent:").append(data.getIdStudent());
        sb.append("|name:").append(data.getName());
        sb.append("|lastName:").append(data.getName());
        sb.append("|residence:").append(data.getResidence());
        sb.append("|age:").append(String.valueOf(data.getAge()));
        sb.append("|qualification:")
                .append(String.valueOf(data.getQualification()))
                .append("}");
        sb.append(",to:").append(to);
        sb.append(",from:").append(from);
        return sb.toString();
    }

}
