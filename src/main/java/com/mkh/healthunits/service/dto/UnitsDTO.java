package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.Units} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnitsDTO implements Serializable {

    private Long id;

    private String name;

    private Integer priority;

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
        if (!(o instanceof UnitsDTO)) {
            return false;
        }

        UnitsDTO unitsDTO = (UnitsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, unitsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priority=" + getPriority() +
            ", doctorsUnit=" + getDoctorsUnit() +
            ", extraPassUnit=" + getExtraPassUnit() +
            "}";
    }
}
