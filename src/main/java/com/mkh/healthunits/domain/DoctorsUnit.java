package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DoctorsUnit.
 */
@Entity
@Table(name = "doctors_unit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorsUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "day_value")
    private Integer dayValue;

    @Column(name = "shift_type")
    private Integer shiftType;

    @Column(name = "active_week")
    private Integer activeWeek;

    @Column(name = "done_pass")
    private Integer donePass;

    @Column(name = "pass_date")
    private ZonedDateTime passDate;

    @Column(name = "unit_passed")
    private Integer unitPassed;

    @Column(name = "pass_blocked")
    private Integer passBlocked;

    @Column(name = "just_view")
    private Integer justView;

    @OneToMany(mappedBy = "doctorsUnit")
    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    private Set<Doctors> doctors = new HashSet<>();

    @OneToMany(mappedBy = "doctorsUnit")
    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    private Set<Units> units = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DoctorsUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayValue() {
        return this.dayValue;
    }

    public DoctorsUnit dayValue(Integer dayValue) {
        this.setDayValue(dayValue);
        return this;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public Integer getShiftType() {
        return this.shiftType;
    }

    public DoctorsUnit shiftType(Integer shiftType) {
        this.setShiftType(shiftType);
        return this;
    }

    public void setShiftType(Integer shiftType) {
        this.shiftType = shiftType;
    }

    public Integer getActiveWeek() {
        return this.activeWeek;
    }

    public DoctorsUnit activeWeek(Integer activeWeek) {
        this.setActiveWeek(activeWeek);
        return this;
    }

    public void setActiveWeek(Integer activeWeek) {
        this.activeWeek = activeWeek;
    }

    public Integer getDonePass() {
        return this.donePass;
    }

    public DoctorsUnit donePass(Integer donePass) {
        this.setDonePass(donePass);
        return this;
    }

    public void setDonePass(Integer donePass) {
        this.donePass = donePass;
    }

    public ZonedDateTime getPassDate() {
        return this.passDate;
    }

    public DoctorsUnit passDate(ZonedDateTime passDate) {
        this.setPassDate(passDate);
        return this;
    }

    public void setPassDate(ZonedDateTime passDate) {
        this.passDate = passDate;
    }

    public Integer getUnitPassed() {
        return this.unitPassed;
    }

    public DoctorsUnit unitPassed(Integer unitPassed) {
        this.setUnitPassed(unitPassed);
        return this;
    }

    public void setUnitPassed(Integer unitPassed) {
        this.unitPassed = unitPassed;
    }

    public Integer getPassBlocked() {
        return this.passBlocked;
    }

    public DoctorsUnit passBlocked(Integer passBlocked) {
        this.setPassBlocked(passBlocked);
        return this;
    }

    public void setPassBlocked(Integer passBlocked) {
        this.passBlocked = passBlocked;
    }

    public Integer getJustView() {
        return this.justView;
    }

    public DoctorsUnit justView(Integer justView) {
        this.setJustView(justView);
        return this;
    }

    public void setJustView(Integer justView) {
        this.justView = justView;
    }

    public Set<Doctors> getDoctors() {
        return this.doctors;
    }

    public void setDoctors(Set<Doctors> doctors) {
        if (this.doctors != null) {
            this.doctors.forEach(i -> i.setDoctorsUnit(null));
        }
        if (doctors != null) {
            doctors.forEach(i -> i.setDoctorsUnit(this));
        }
        this.doctors = doctors;
    }

    public DoctorsUnit doctors(Set<Doctors> doctors) {
        this.setDoctors(doctors);
        return this;
    }

    public DoctorsUnit addDoctor(Doctors doctors) {
        this.doctors.add(doctors);
        doctors.setDoctorsUnit(this);
        return this;
    }

    public DoctorsUnit removeDoctor(Doctors doctors) {
        this.doctors.remove(doctors);
        doctors.setDoctorsUnit(null);
        return this;
    }

    public Set<Units> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Units> units) {
        if (this.units != null) {
            this.units.forEach(i -> i.setDoctorsUnit(null));
        }
        if (units != null) {
            units.forEach(i -> i.setDoctorsUnit(this));
        }
        this.units = units;
    }

    public DoctorsUnit units(Set<Units> units) {
        this.setUnits(units);
        return this;
    }

    public DoctorsUnit addUnit(Units units) {
        this.units.add(units);
        units.setDoctorsUnit(this);
        return this;
    }

    public DoctorsUnit removeUnit(Units units) {
        this.units.remove(units);
        units.setDoctorsUnit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorsUnit)) {
            return false;
        }
        return id != null && id.equals(((DoctorsUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorsUnit{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", shiftType=" + getShiftType() +
            ", activeWeek=" + getActiveWeek() +
            ", donePass=" + getDonePass() +
            ", passDate='" + getPassDate() + "'" +
            ", unitPassed=" + getUnitPassed() +
            ", passBlocked=" + getPassBlocked() +
            ", justView=" + getJustView() +
            "}";
    }
}
