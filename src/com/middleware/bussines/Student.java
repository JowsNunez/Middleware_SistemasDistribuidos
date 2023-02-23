
package com.middleware.bussines;
/**
 *
 * @author el_fr
 */
public class Student {
    private String idStudent;
    private String name;
    private String lastName;
    private int age;
    private String residence;
    private int qualification;

    

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public String toString() {
        return "Student{" + "idStudent=" + idStudent + ", name=" + name + ", lastName=" + lastName + ", age=" + age + ", residence=" + residence + ", qualification=" + qualification + '}';
    }
    

    

    
}
