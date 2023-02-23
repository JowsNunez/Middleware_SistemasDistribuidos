
package com.middleware.interpret;

import com.middleware.bussines.Student;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public interface interpreter {
    String encode(String action, Student data, String to, String from);
    Map<String, Object> decode(String data);
    
    
}
