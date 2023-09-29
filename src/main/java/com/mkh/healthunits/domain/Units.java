package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Units.
 */
@Entity
@Table(name = "units")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Units implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private Integer priority;

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

    public Units id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Units name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Units priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public DoctorsUnit getDoctorsUnit() {
        return this.doctorsUnit;
    }

    public void setDoctorsUnit(DoctorsUnit doctorsUnit) {
        this.doctorsUnit = doctorsUnit;
    }

    public Units doctorsUnit(DoctorsUnit doctorsUnit) {
        this.setDoctorsUnit(doctorsUnit);
        return this;
    }

    public ExtraPassUnit getExtraPassUnit() {
        return this.extraPassUnit;
    }

    public void setExtraPassUnit(ExtraPassUnit extraPassUnit) {
        this.extraPassUnit = extraPassUnit;
    }

    public Units extraPassUnit(ExtraPassUnit extraPassUnit) {
        this.setExtraPassUnit(extraPassUnit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Units)) {
            return false;
        }
        return id != null && id.equals(((Units) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Units{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priority=" + getPriority() +
            "}";
    }
}
