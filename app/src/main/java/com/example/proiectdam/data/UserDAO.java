package com.example.proiectdam.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users")
    public List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE rol=:role")
    public List<User> getUsersByRole(String role);

    @Query("SELECT * FROM users WHERE email=:email AND parola=:password")
    public User getUser(String email,String password);

    @Insert
    public void insertUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);
}
