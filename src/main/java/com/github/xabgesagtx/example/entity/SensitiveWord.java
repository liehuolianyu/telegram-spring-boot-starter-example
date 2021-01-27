package com.github.xabgesagtx.example.entity;

public class SensitiveWord {
    private Integer id;

    private String name;

    private String descs;

    private String remark;

    private String rsrv1;

    private String rsrv2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRsrv1() {
        return rsrv1;
    }

    public void setRsrv1(String rsrv1) {
        this.rsrv1 = rsrv1 == null ? null : rsrv1.trim();
    }

    public String getRsrv2() {
        return rsrv2;
    }

    public void setRsrv2(String rsrv2) {
        this.rsrv2 = rsrv2 == null ? null : rsrv2.trim();
    }
}