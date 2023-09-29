package com.mkh.healthunits.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.mkh.healthunits.domain.UnitImgs} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnitImgsDTO implements Serializable {

    private Long id;

    private String fileDescription;

    @Lob
    private byte[] img;

    private String imgContentType;
    private UnitsDTO unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitImgsDTO)) {
            return false;
        }

        UnitImgsDTO unitImgsDTO = (UnitImgsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, unitImgsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitImgsDTO{" +
            "id=" + getId() +
            ", fileDescription='" + getFileDescription() + "'" +
            ", img='" + getImg() + "'" +
            ", unit=" + getUnit() +
            "}";
    }
}
