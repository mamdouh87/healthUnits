package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.DoctorsUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorsUnitDTO implements Serializable {

    private Long id;

    private Integer dayValue;

    private Integer shiftType;

    private Integer activeWeek;

    private Integer donePass;

    private ZonedDateTime passDate;

    private Integer unitPassed;

    private Integer passBlocked;

    private Integer justView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayValue() {
        return dayValue;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public Integer getShiftType() {
        return shiftType;
    }

    public void setShiftType(Integer shiftType) {
        this.shiftType = shiftType;
    }

    public Integer getActiveWeek() {
        return activeWeek;
    }

    public void setActiveWeek(Integer activeWeek) {
        this.activeWeek = activeWeek;
    }

    public Integer getDonePass() {
        return donePass;
    }

    public void setDonePass(Integer donePass) {
        this.donePass = donePass;
    }

    public ZonedDateTime getPassDate() {
        return passDate;
    }

    public void setPassDate(ZonedDateTime passDate) {
        this.passDate = passDate;
    }

    public Integer getUnitPassed() {
        return unitPassed;
    }

    public void setUnitPassed(Integer unitPassed) {
        this.unitPassed = unitPassed;
    }

    public Integer getPassBlocked() {
        return passBlocked;
    }

    public void setPassBlocked(Integer passBlocked) {
        this.passBlocked = passBlocked;
    }

    public Integer getJustView() {
        return justView;
    }

    public void setJustView(Integer justView) {
        this.justView = justView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorsUnitDTO)) {
            return false;
        }

        DoctorsUnitDTO doctorsUnitDTO = (DoctorsUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorsUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorsUnitDTO{" +
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
