//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.28 at 01:28:13 PM CEST 
//


package com.example.webservices.schemas.calculator;

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
@XmlRootElement(name = "subRequest")
public class SubRequest {

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