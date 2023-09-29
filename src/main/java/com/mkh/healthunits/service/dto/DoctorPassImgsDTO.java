package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.DoctorPassImgs} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorPassImgsDTO implements Serializable {

    private Long id;

    private Integer dayValue;

    @Lob
    private byte[] img;

    private String imgContentType;
    private UnitsDTO unit;

    private DoctorsDTO doctor;

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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public UnitsDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitsDTO unit) {
        this.unit = unit;
    }

    public DoctorsDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorsDTO doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorPassImgsDTO)) {
            return false;
        }

        DoctorPassImgsDTO doctorPassImgsDTO = (DoctorPassImgsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorPassImgsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorPassImgsDTO{" +
            "id=" + getId() +
            ", dayValue=" + getDayValue() +
            ", img='" + getImg() + "'" +
            ", unit=" + getUnit() +
            ", doctor=" + getDoctor() +
            "}";
    }
}
