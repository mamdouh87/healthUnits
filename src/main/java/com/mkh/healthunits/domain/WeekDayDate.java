package com.mkh.healthunits.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A WeekDayDate.
 */
@Entity
@Table(name = "week_day_date")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WeekDayDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "day_value")
    private Integer dayValue;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "day_date")
    private String dayDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WeekDayDate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayValue() {
        return this.dayValue;
    }

    public WeekDayDate dayValue(Integer dayValue) {
        this.setDayValue(dayValue);
        return this;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public String getDayName() {
        return this.dayName;
    }

    public WeekDayDate dayName(String dayName) {
        this.setDayName(dayName);
        return this;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayDate() {
        return this.dayDate;
    }

    public WeekDayDate dayDate(String dayDate) {
        this.setDayDate(dayDate);
        return this;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekDayDate)) {
            return false;
        }
        return id != null && id.equals(((WeekDayDate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeekDayDate{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", dayName='" + getDayName() + "'" +
            ", dayDate='" + getDayDate() + "'" +
            "}";
    }
}
