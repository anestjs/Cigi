package main.java.com.cigiproject.tests;


import java.util.Optional;

import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Year;

public class ClassDaoTest {

    public static void main(String[] args) {
        ClassDaoImpl classDao = new ClassDaoImpl();

        // Test adding a new class
        Class newClass = new Class();

        newClass.setStudent_count(30);
        newClass.setYear(Year._3);

        boolean isSaved = classDao.save(newClass);
        System.out.println("Class saved: " + isSaved);

        // Test finding all classes
        System.out.println("All Classes:");
        classDao.findAll().forEach(c -> System.out.println(c.getName() + " (Capacity: " + c.getStudent_count() + ")"));

        // Test finding a class by ID
        Optional<Class> foundClass = classDao.findById(1);
        foundClass.ifPresent(c -> System.out.println("Found Class: " + c.getName()));

        // Test updating a class
        // if (foundClass.isPresent()) {
        //     Class updatedClass = foundClass.get();
        //     updatedClass.setStudent_count(null);
        //     boolean isUpdated = classDao.update(updatedClass);
        //     System.out.println("Class updated: " + isUpdated);
        // }

        // Test deleting a class
        // boolean isDeleted = classDao.delete(1);
        // System.out.println("Class deleted: " + isDeleted);
    }
}