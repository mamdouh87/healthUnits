package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.Notifications} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationsDTO implements Serializable {

    private Long id;

    private String title;

    private String message;

    private Integer status;

    private Integer dayValue;

    private UnitsDTO unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDayValue() {
        return dayValue;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public UnitsDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitsDTO unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationsDTO)) {
            return false;
        }

        NotificationsDTO notificationsDTO = (NotificationsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", dayValue=" + getDayValue() +
            ", unit=" + getUnit() +
            "}";
    }
}
