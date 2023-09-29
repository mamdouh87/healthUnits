package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.Doctors} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorsDTO implements Serializable {

    private Long id;

    private String name;

    private String perferedDay;

    private Integer doubleShift;

    private String shiftType;

    private String perferedUnit;

    private DoctorsUnitDTO doctorsUnit;

    private ExtraPassUnitDTO extraPassUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerferedDay() {
        return perferedDay;
    }

    public void setPerferedDay(String perferedDay) {
        this.perferedDay = perferedDay;
    }

    public Integer getDoubleShift() {
        return doubleShift;
    }

    public void setDoubleShift(Integer doubleShift) {
        this.doubleShift = doubleShift;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getPerferedUnit() {
        return perferedUnit;
    }

    public void setPerferedUnit(String perferedUnit) {
        this.perferedUnit = perferedUnit;
    }

    public DoctorsUnitDTO getDoctorsUnit() {
        return doctorsUnit;
    }

    public void setDoctorsUnit(DoctorsUnitDTO doctorsUnit) {
        this.doctorsUnit = doctorsUnit;
    }

    public ExtraPassUnitDTO getExtraPassUnit() {
        return extraPassUnit;
    }

    public void setExtraPassUnit(ExtraPassUnitDTO extraPassUnit) {
        this.extraPassUnit = extraPassUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorsDTO)) {
            return false;
        }

        DoctorsDTO doctorsDTO = (DoctorsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", perferedDay='" + getPerferedDay() + "'" +
            ", doubleShift=" + getDoubleShift() +
            ", shiftType='" + getShiftType() + "'" +
            ", perferedUnit='" + getPerferedUnit() + "'" +
            ", doctorsUnit=" + getDoctorsUnit() +
            ", extraPassUnit=" + getExtraPassUnit() +
            "}";
    }
}
