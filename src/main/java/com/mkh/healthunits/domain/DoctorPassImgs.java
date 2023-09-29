package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A DoctorPassImgs.
 */
@Entity
@Table(name = "doctor_pass_imgs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorPassImgs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "day_value")
    private Integer dayValue;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Units unit;

    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Doctors doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DoctorPassImgs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayValue() {
        return this.dayValue;
    }

    public DoctorPassImgs dayValue(Integer dayValue) {
        this.setDayValue(dayValue);
        return this;
    }

    public void setDayValue(Integer dayValue) {
        this.dayValue = dayValue;
    }

    public byte[] getImg() {
        return this.img;
    }

    public DoctorPassImgs img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public DoctorPassImgs imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Units getUnit() {
        return this.unit;
    }

    public void setUnit(Units units) {
        this.unit = units;
    }

    public DoctorPassImgs unit(Units units) {
        this.setUnit(units);
        return this;
    }

    public Doctors getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctors doctors) {
        this.doctor = doctors;
    }

    public DoctorPassImgs doctor(Doctors doctors) {
        this.setDoctor(doctors);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorPassImgs)) {
            return false;
        }
        return id != null && id.equals(((DoctorPassImgs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorPassImgs{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
