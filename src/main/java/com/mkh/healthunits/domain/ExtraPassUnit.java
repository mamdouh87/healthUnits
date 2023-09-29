package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ExtraPassUnit.
 */
@Entity
@Table(name = "extra_pass_unit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExtraPassUnit implements Serializable {

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

    @OneToMany(mappedBy = "extraPassUnit")
    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    private Set<Doctors> doctors = new HashSet<>();

    @OneToMany(mappedBy = "extraPassUnit")
    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    private Set<Units> units = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExtraPassUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayValue() {
        return this.dayValue;
    }

    public ExtraPassUnit dayValue(Integer dayValue) {
        this.setDayValue(dayValue);
        return this;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public Integer getShiftType() {
        return this.shiftType;
    }

    public ExtraPassUnit shiftType(Integer shiftType) {
        this.setShiftType(shiftType);
        return this;
    }

    public void setShiftType(Integer shiftType) {
        this.shiftType = shiftType;
    }

    public Integer getActiveWeek() {
        return this.activeWeek;
    }

    public ExtraPassUnit activeWeek(Integer activeWeek) {
        this.setActiveWeek(activeWeek);
        return this;
    }

    public void setActiveWeek(Integer activeWeek) {
        this.activeWeek = activeWeek;
    }

    public Integer getDonePass() {
        return this.donePass;
    }

    public ExtraPassUnit donePass(Integer donePass) {
        this.setDonePass(donePass);
        return this;
    }

    public void setDonePass(Integer donePass) {
        this.donePass = donePass;
    }

    public ZonedDateTime getPassDate() {
        return this.passDate;
    }

    public ExtraPassUnit passDate(ZonedDateTime passDate) {
        this.setPassDate(passDate);
        return this;
    }

    public void setPassDate(ZonedDateTime passDate) {
        this.passDate = passDate;
    }

    public Integer getUnitPassed() {
        return this.unitPassed;
    }

    public ExtraPassUnit unitPassed(Integer unitPassed) {
        this.setUnitPassed(unitPassed);
        return this;
    }

    public void setUnitPassed(Integer unitPassed) {
        this.unitPassed = unitPassed;
    }

    public Set<Doctors> getDoctors() {
        return this.doctors;
    }

    public void setDoctors(Set<Doctors> doctors) {
        if (this.doctors != null) {
            this.doctors.forEach(i -> i.setExtraPassUnit(null));
        }
        if (doctors != null) {
            doctors.forEach(i -> i.setExtraPassUnit(this));
        }
        this.doctors = doctors;
    }

    public ExtraPassUnit doctors(Set<Doctors> doctors) {
        this.setDoctors(doctors);
        return this;
    }

    public ExtraPassUnit addDoctor(Doctors doctors) {
        this.doctors.add(doctors);
        doctors.setExtraPassUnit(this);
        return this;
    }

    public ExtraPassUnit removeDoctor(Doctors doctors) {
        this.doctors.remove(doctors);
        doctors.setExtraPassUnit(null);
        return this;
    }

    public Set<Units> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Units> units) {
        if (this.units != null) {
            this.units.forEach(i -> i.setExtraPassUnit(null));
        }
        if (units != null) {
            units.forEach(i -> i.setExtraPassUnit(this));
        }
        this.units = units;
    }

    public ExtraPassUnit units(Set<Units> units) {
        this.setUnits(units);
        return this;
    }

    public ExtraPassUnit addUnit(Units units) {
        this.units.add(units);
        units.setExtraPassUnit(this);
        return this;
    }

    public ExtraPassUnit removeUnit(Units units) {
        this.units.remove(units);
        units.setExtraPassUnit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtraPassUnit)) {
            return false;
        }
        return id != null && id.equals(((ExtraPassUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtraPassUnit{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", shiftType=" + getShiftType() +
            ", activeWeek=" + getActiveWeek() +
            ", donePass=" + getDonePass() +
            ", passDate='" + getPassDate() + "'" +
            ", unitPassed=" + getUnitPassed() +
            "}";
    }
}
