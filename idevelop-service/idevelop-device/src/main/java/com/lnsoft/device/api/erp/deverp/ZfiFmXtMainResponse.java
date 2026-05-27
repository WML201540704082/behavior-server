
package com.lnsoft.device.api.erp.deverp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "response"
})
@XmlRootElement(name = "ZfiFmXtMainResponse")
public class ZfiFmXtMainResponse {

    @XmlElement(name = "Response", required = true)
    protected String response;

    /**
     * ��ȡresponse���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getResponse() {
        return response;
    }

    /**
     * ����response���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setResponse(String value) {
        this.response = value;
    }

}
