
package com.lnsoft.device.api.erp.deverp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request"
})
@XmlRootElement(name = "ZfiFmXtMain")
public class ZfiFmXtMain_Type {

    @XmlElement(name = "Request", required = true)
    protected String request;

    /**
     * ��ȡrequest���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRequest() {
        return request;
    }

    /**
     * ����request���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequest(String value) {
        this.request = value;
    }

}
