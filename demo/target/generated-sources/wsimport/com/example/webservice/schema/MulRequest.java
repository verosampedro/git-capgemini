
package com.example.webservice.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="op1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="op2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "op1",
    "op2"
})
@XmlRootElement(name = "mulRequest")
public class MulRequest {

    protected double op1;
    protected double op2;

    /**
     * Gets the value of the op1 property.
     * 
     */
    public double getOp1() {
        return op1;
    }

    /**
     * Sets the value of the op1 property.
     * 
     */
    public void setOp1(double value) {
        this.op1 = value;
    }

    /**
     * Gets the value of the op2 property.
     * 
     */
    public double getOp2() {
        return op2;
    }

    /**
     * Sets the value of the op2 property.
     * 
     */
    public void setOp2(double value) {
        this.op2 = value;
    }

}