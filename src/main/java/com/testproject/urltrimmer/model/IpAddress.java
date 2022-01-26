package com.testproject.urltrimmer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ip_addresses")
public class IpAddress extends BaseEntity{

    @Column(name = "url_id", nullable = false)
    private Integer urlId;

    @Column(name = "ip", unique = true)
    private String ip;

    public IpAddress(Integer urlId, String ip) {
        this.urlId = urlId;
        this.ip = ip;
    }

    public IpAddress() {
    }

    public Integer getUrlId() {
        return urlId;
    }

    public String getIp() {
        return ip;
    }
}
