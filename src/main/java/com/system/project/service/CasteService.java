package com.system.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.project.entities.Caste;
import com.system.project.repository.CasteRepository;

@Service
public class CasteService {

    @Autowired
    private CasteRepository casteRepository;


    public List<Caste> getAllCastes() {
        return casteRepository.findAll();
    }

     public Caste getCasteById(Long id) {
        return casteRepository.findById(id).orElse(null);
    }

    // Add this method to save a cast
    public void saveCaste(Caste caste) {
        casteRepository.save(caste);
    }

	public List<Caste> getCastesByClassId(Long classId) {
		// TODO Auto-generated method stub
		return null;
	}



}