package com.example.proiectdam.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GradeDAO {

    @Query("SELECT * FROM grades WHERE uid= :studentId ")
    public List<Grade> getStudentGrade(int studentId);

    @Query("SELECT * FROM grades WHERE uid=:studentId AND tip=:type")
    public List<Grade> getStudentGradeByType(int studentId,String type);

    @Query("SELECT * FROM grades")
    public List<Grade> getAllGrades();

    @Insert
    public void insertGrade(Grade grade);

    @Delete
    public void deleteGrade(Grade grade);
}
