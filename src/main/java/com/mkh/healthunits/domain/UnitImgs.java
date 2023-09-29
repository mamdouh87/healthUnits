package com.mkh.healthunits.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A UnitImgs.
 */
@Entity
@Table(name = "unit_imgs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UnitImgs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "file_description")
    private String fileDescription;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @JsonIgnoreProperties(value = { "doctorsUnit", "extraPassUnit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Units unit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UnitImgs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileDescription() {
        return this.fileDescription;
    }

    public UnitImgs fileDescription(String fileDescription) {
        this.setFileDescription(fileDescription);
        return this;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public byte[] getImg() {
        return this.img;
    }

    public UnitImgs img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public UnitImgs imgContentType(String imgContentType) {
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

    public UnitImgs unit(Units units) {
        this.setUnit(units);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitImgs)) {
            return false;
        }
        return id != null && id.equals(((UnitImgs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitImgs{" +
            "id=" + getId() +
            ", fileDescription='" + getFileDescription() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
