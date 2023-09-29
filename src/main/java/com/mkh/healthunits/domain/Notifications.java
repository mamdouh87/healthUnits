package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Notifications.
 */
@Entity
@Table(name = "notifications")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private Integer status;

    @Column(name = "day_value")
    private Integer dayValue;

    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Units unit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notifications id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Notifications title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public Notifications message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Notifications status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDayValue() {
        return this.dayValue;
    }

    public Notifications dayValue(Integer dayValue) {
        this.setDayValue(dayValue);
        return this;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public Units getUnit() {
        return this.unit;
    }

    public void setUnit(Units units) {
        this.unit = units;
    }

    public Notifications unit(Units units) {
        this.setUnit(units);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notifications)) {
            return false;
        }
        return id != null && id.equals(((Notifications) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notifications{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", status=" + getStatus() +
            ", dayValue=" + getDayValue() +
            "}";
    }
}
