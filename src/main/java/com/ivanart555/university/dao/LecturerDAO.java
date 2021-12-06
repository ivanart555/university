package com.ivanart555.university.dao;

import java.util.List;

import com.ivanart555.university.dao.generic.GenericDAO;
import com.ivanart555.university.entities.Lecturer;

public interface LecturerDAO extends GenericDAO<Lecturer, Integer> {

    List<Lecturer> getAllActive();

}
