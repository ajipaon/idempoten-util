package com.tki.katalis.idempoten.properties;
import com.tki.katalis.idempoten.enums.TokenStorageEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("idempotent")
public class IdempotentProperties {

    private Boolean enable = Boolean.FALSE;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


}
