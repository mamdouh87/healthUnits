package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.WeekDayDate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WeekDayDateDTO implements Serializable {

    private Long id;

    private Integer dayValue;

    private String dayName;

    private String dayDate;

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

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekDayDateDTO)) {
            return false;
        }

        WeekDayDateDTO weekDayDateDTO = (WeekDayDateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, weekDayDateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeekDayDateDTO{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", dayName='" + getDayName() + "'" +
            ", dayDate='" + getDayDate() + "'" +
            "}";
    }
}
