package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Doctors.
 */
@Entity
@Table(name = "doctors")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doctors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "perfered_day")
    private String perferedDay;

    @Column(name = "double_shift")
    private Integer doubleShift;

    @Column(name = "shift_type")
    private String shiftType;

    @Column(name = "perfered_unit")
    private String perferedUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "doctors", "units" }, allowSetters = true)
    private DoctorsUnit doctorsUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "doctors", "units" }, allowSetters = true)
    private ExtraPassUnit extraPassUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doctors id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Doctors name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerferedDay() {
        return this.perferedDay;
    }

    public Doctors perferedDay(String perferedDay) {
        this.setPerferedDay(perferedDay);
        return this;
    }

    public void setPerferedDay(String perferedDay) {
        this.perferedDay = perferedDay;
    }

    public Integer getDoubleShift() {
        return this.doubleShift;
    }

    public Doctors doubleShift(Integer doubleShift) {
        this.setDoubleShift(doubleShift);
        return this;
    }

    public void setDoubleShift(Integer doubleShift) {
        this.doubleShift = doubleShift;
    }

    public String getShiftType() {
        return this.shiftType;
    }

    public Doctors shiftType(String shiftType) {
        this.setShiftType(shiftType);
        return this;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getPerferedUnit() {
        return this.perferedUnit;
    }

    public Doctors perferedUnit(String perferedUnit) {
        this.setPerferedUnit(perferedUnit);
        return this;
    }

    public void setPerferedUnit(String perferedUnit) {
        this.perferedUnit = perferedUnit;
    }

    public DoctorsUnit getDoctorsUnit() {
        return this.doctorsUnit;
    }

    public void setDoctorsUnit(DoctorsUnit doctorsUnit) {
        this.doctorsUnit = doctorsUnit;
    }

    public Doctors doctorsUnit(DoctorsUnit doctorsUnit) {
        this.setDoctorsUnit(doctorsUnit);
        return this;
    }

    public ExtraPassUnit getExtraPassUnit() {
        return this.extraPassUnit;
    }

    public void setExtraPassUnit(ExtraPassUnit extraPassUnit) {
        this.extraPassUnit = extraPassUnit;
    }

    public Doctors extraPassUnit(ExtraPassUnit extraPassUnit) {
        this.setExtraPassUnit(extraPassUnit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctors)) {
            return false;
        }
        return id != null && id.equals(((Doctors) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctors{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", perferedDay='" + getPerferedDay() + "'" +
            ", doubleShift=" + getDoubleShift() +
            ", shiftType='" + getShiftType() + "'" +
            ", perferedUnit='" + getPerferedUnit() + "'" +
            "}";
    }
}
