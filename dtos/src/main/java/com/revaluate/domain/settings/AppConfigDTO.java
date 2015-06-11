package com.revaluate.domain.settings;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class AppConfigDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private double version;

    @NotNull
    private List<KeyValueDTO> keyValueDTOList;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public List<KeyValueDTO> getKeyValueDTOList() {
        return keyValueDTOList;
    }

    public void setKeyValueDTOList(List<KeyValueDTO> keyValueDTOList) {
        this.keyValueDTOList = keyValueDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppConfigDTO that = (AppConfigDTO) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(keyValueDTOList, that.keyValueDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, keyValueDTOList);
    }

    @Override
    public String toString() {
        return "AppConfigDTO{" +
                "version=" + version +
                ", keyValueDTOList=" + keyValueDTOList +
                '}';
    }
}