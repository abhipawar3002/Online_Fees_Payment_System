package com.system.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.project.entities.Fees;
import com.system.project.repository.FeesRepository;

import jakarta.transaction.Transactional;

@Service
public class FeesService {

    @Autowired
    private FeesRepository feesRepository;

    public void saveFees(Fees fees) {
        feesRepository.save(fees);
    }

    public List<Fees> getAllFees() {
        return feesRepository.findAllWithRelations(); // Use the custom query
    }

	public void saveFees(double amount, long courseId, long classId, long casteId) {
		// TODO Auto-generated method stub

	}

	public boolean isDuplicateFees(Long courseId, Long classId, Long casteId) {
        return feesRepository.existsByCourseIdAndClassEntityIdAndCasteId(courseId, classId, casteId);
    }

	public Fees getFeesById(Integer id) {
        return feesRepository.findById(id).orElse(null);
    }

    public void deleteFees(Long id) {
        feesRepository.deleteById(id);
    }

    @Transactional
    public double getFeesByCourseClassAndCaste(Long courseId, Long classId, Long casteId) {
        Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getAmount).orElse(0.0);
    }

    public double getTuitionFees(Long courseId, Long classId, Long casteId) {
    	Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getTuitionFees).orElse(0.0);
    }

	public double getGymkhanaFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getGymkhanaFees).orElse(0.0);
    }

	public double getLibraryFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getLibraryFees).orElse(0.0);
	}

	public double getAdminProcessingFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getAdminProcessingFees).orElse(0.0);
	}

	public double getStudentWelfareFund(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getStudentWelfareFund).orElse(0.0);
    }

	public double getUtilityFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getUtilityFees).orElse(0.0);
    }

	public double getIdLibCardFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getIdLibCardFees).orElse(0.0);
    }

	public double getECharges(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::geteCharges).orElse(0.0);
    }

	public double getVcFund(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getVcFund).orElse(0.0);
    }

	public double getNssFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getNssFees).orElse(0.0);
    }

	public double getSportsFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getSportsFees).orElse(0.0);
    }

	public double getCollegeCulFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getCollegeCulFees).orElse(0.0);
    }

	public double getOtherFees(Long courseId, Long classId, Long casteId) {
		Optional<Fees> fees = feesRepository.findByCourse_IdAndClassEntity_IdAndCaste_Id(courseId, classId, casteId);
        return fees.map(Fees::getOtherFees).orElse(0.0);
    }

	public double calculateTotalFees(Object id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Fees getFeesById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}