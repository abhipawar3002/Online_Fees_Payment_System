package com.system.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Fees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classEntity;

    @ManyToOne
    @JoinColumn(name = "caste_id")
    @JsonIgnore
    private Caste caste;


 // Additional fee fields
    private double tuitionFees;
    private double libraryFees;
    private double gymkhanaFees;
    private double laboratoryFees;
    private double adminProcessingFees;
    private double studentWelfareFund;
    private double utilityFees;
    private double idLibCardFees;
    private double eCharges;
    private double vcFund;
    private double nssFees;
    private double sportsFees;
    private double collegeCulFees;
    private double otherFees;

    // Getters and Setters



    public Double getAmount() {
        return amount;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Class getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }

    public Caste getCaste() {
        return caste;
    }

    public void setCaste(Caste caste) {
        this.caste = caste;
    }

    public String getCasteName() {
        return caste != null ? caste.getCasteName() : null;
    }

	public double getTuitionFees() {
		return tuitionFees;
	}

	public void setTuitionFees(double tuitionFees) {
		this.tuitionFees = tuitionFees;
	}

	public double getLibraryFees() {
		return libraryFees;
	}

	public void setLibraryFees(double libraryFees) {
		this.libraryFees = libraryFees;
	}

	public double getGymkhanaFees() {
		return gymkhanaFees;
	}

	public void setGymkhanaFees(double gymkhanaFees) {
		this.gymkhanaFees = gymkhanaFees;
	}

	public double getLaboratoryFees() {
		return laboratoryFees;
	}

	public void setLaboratoryFees(double laboratoryFees) {
		this.laboratoryFees = laboratoryFees;
	}

	public double getAdminProcessingFees() {
		return adminProcessingFees;
	}

	public void setAdminProcessingFees(double adminProcessingFees) {
		this.adminProcessingFees = adminProcessingFees;
	}

	public double getStudentWelfareFund() {
		return studentWelfareFund;
	}

	public void setStudentWelfareFund(double studentWelfareFund) {
		this.studentWelfareFund = studentWelfareFund;
	}

	public double getUtilityFees() {
		return utilityFees;
	}

	public void setUtilityFees(double utilityFees) {
		this.utilityFees = utilityFees;
	}

	public double getIdLibCardFees() {
		return idLibCardFees;
	}

	public void setIdLibCardFees(double idLibCardFees) {
		this.idLibCardFees = idLibCardFees;
	}

	public double geteCharges() {
		return eCharges;
	}

	public void seteCharges(double eCharges) {
		this.eCharges = eCharges;
	}

	public double getVcFund() {
		return vcFund;
	}

	public void setVcFund(double vcFund) {
		this.vcFund = vcFund;
	}

	public double getNssFees() {
		return nssFees;
	}

	public void setNssFees(double nssFees) {
		this.nssFees = nssFees;
	}

	public double getSportsFees() {
		return sportsFees;
	}

	public void setSportsFees(double sportsFees) {
		this.sportsFees = sportsFees;
	}

	public double getCollegeCulFees() {
		return collegeCulFees;
	}

	public void setCollegeCulFees(double collegeCulFees) {
		this.collegeCulFees = collegeCulFees;
	}

	public double getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(double otherFees) {
		this.otherFees = otherFees;
	}




}