
package com.middleware.persists;

import com.middleware.bussines.Student;
import java.util.HashSet;

/**
 *
 * @author el_fr
 */
public class Students {

    private HashSet<Student> students;

    public Students() {
        this.students = new HashSet<>();
    }

    public HashSet<Student> getStudents() {
        return students;
    }

    public void setStudents(HashSet<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void updateStudent(Student student) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(student.getName())) {
                s.setQualification(student.getQualification());
            }
        }
    }

    @Override
    public String toString() {
        return "Students{" + "students=" + students + '}';
    }
    

}
