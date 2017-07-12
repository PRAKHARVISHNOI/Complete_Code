//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.10 at 05:53:57 PM SGT 
//


package com.sinberbest.api.entity.common;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * ErrorLog
 * 
 * <p>Java class for ErrorLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorLog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="errorID" type="{http://www.sinberbest.sg/common}ErrorID" minOccurs="0"/&gt;
 *         &lt;element name="errorTime" type="{http://www.sinberbest.sg/common}ErrorTime" minOccurs="0"/&gt;
 *         &lt;element name="errorCode" type="{http://www.sinberbest.sg/common}ErrorCode"/&gt;
 *         &lt;element name="deviceID" type="{http://www.sinberbest.sg/common}DeviceID" minOccurs="0"/&gt;
 *         &lt;element name="errorTrace" type="{http://www.sinberbest.sg/common}ErrorTrace" minOccurs="0"/&gt;
 *         &lt;element name="source" type="{http://www.sinberbest.sg/common}Source"/&gt;
 *         &lt;element name="severity" type="{http://www.sinberbest.sg/common}Severity"/&gt;
 *         &lt;element name="details" type="{http://www.sinberbest.sg/common}Details" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorLog", propOrder = {
    "errorID",
    "errorTime",
    "errorCode",
    "deviceID",
    "errorTrace",
    "source",
    "severity",
    "details"
})
public class ErrorLog
    implements Equals, HashCode, ToString
{

    protected Integer errorID;
    protected String errorTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    @NotNull
    protected ErrorCode errorCode;
    protected Integer deviceID;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    @Size(min = 0, max = 256)
    protected String errorTrace;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    @NotNull
    @Size(min = 0, max = 30)
    protected String source;
    @XmlElement(required = true)
    @XmlSchemaType(name = "token")
    @NotNull
    protected Severity severity;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    @Size(min = 0, max = 200)
    protected String details;

    /**
     * Gets the value of the errorID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getErrorID() {
        return errorID;
    }

    /**
     * Sets the value of the errorID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setErrorID(Integer value) {
        this.errorID = value;
    }

    /**
     * Gets the value of the errorTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorTime() {
        return errorTime;
    }

    /**
     * Sets the value of the errorTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorTime(String value) {
        this.errorTime = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorCode }
     *     
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorCode }
     *     
     */
    public void setErrorCode(ErrorCode value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the deviceID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeviceID() {
        return deviceID;
    }

    /**
     * Sets the value of the deviceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeviceID(Integer value) {
        this.deviceID = value;
    }

    /**
     * Gets the value of the errorTrace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorTrace() {
        return errorTrace;
    }

    /**
     * Sets the value of the errorTrace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorTrace(String value) {
        this.errorTrace = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link Severity }
     *     
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Severity }
     *     
     */
    public void setSeverity(Severity value) {
        this.severity = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetails(String value) {
        this.details = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof ErrorLog)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ErrorLog that = ((ErrorLog) object);
        {
            Integer lhsErrorID;
            lhsErrorID = this.getErrorID();
            Integer rhsErrorID;
            rhsErrorID = that.getErrorID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "errorID", lhsErrorID), LocatorUtils.property(thatLocator, "errorID", rhsErrorID), lhsErrorID, rhsErrorID)) {
                return false;
            }
        }
        {
            String lhsErrorTime;
            lhsErrorTime = this.getErrorTime();
            String rhsErrorTime;
            rhsErrorTime = that.getErrorTime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "errorTime", lhsErrorTime), LocatorUtils.property(thatLocator, "errorTime", rhsErrorTime), lhsErrorTime, rhsErrorTime)) {
                return false;
            }
        }
        {
            ErrorCode lhsErrorCode;
            lhsErrorCode = this.getErrorCode();
            ErrorCode rhsErrorCode;
            rhsErrorCode = that.getErrorCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "errorCode", lhsErrorCode), LocatorUtils.property(thatLocator, "errorCode", rhsErrorCode), lhsErrorCode, rhsErrorCode)) {
                return false;
            }
        }
        {
            Integer lhsDeviceID;
            lhsDeviceID = this.getDeviceID();
            Integer rhsDeviceID;
            rhsDeviceID = that.getDeviceID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "deviceID", lhsDeviceID), LocatorUtils.property(thatLocator, "deviceID", rhsDeviceID), lhsDeviceID, rhsDeviceID)) {
                return false;
            }
        }
        {
            String lhsErrorTrace;
            lhsErrorTrace = this.getErrorTrace();
            String rhsErrorTrace;
            rhsErrorTrace = that.getErrorTrace();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "errorTrace", lhsErrorTrace), LocatorUtils.property(thatLocator, "errorTrace", rhsErrorTrace), lhsErrorTrace, rhsErrorTrace)) {
                return false;
            }
        }
        {
            String lhsSource;
            lhsSource = this.getSource();
            String rhsSource;
            rhsSource = that.getSource();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "source", lhsSource), LocatorUtils.property(thatLocator, "source", rhsSource), lhsSource, rhsSource)) {
                return false;
            }
        }
        {
            Severity lhsSeverity;
            lhsSeverity = this.getSeverity();
            Severity rhsSeverity;
            rhsSeverity = that.getSeverity();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "severity", lhsSeverity), LocatorUtils.property(thatLocator, "severity", rhsSeverity), lhsSeverity, rhsSeverity)) {
                return false;
            }
        }
        {
            String lhsDetails;
            lhsDetails = this.getDetails();
            String rhsDetails;
            rhsDetails = that.getDetails();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "details", lhsDetails), LocatorUtils.property(thatLocator, "details", rhsDetails), lhsDetails, rhsDetails)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            Integer theErrorID;
            theErrorID = this.getErrorID();
            strategy.appendField(locator, this, "errorID", buffer, theErrorID);
        }
        {
            String theErrorTime;
            theErrorTime = this.getErrorTime();
            strategy.appendField(locator, this, "errorTime", buffer, theErrorTime);
        }
        {
            ErrorCode theErrorCode;
            theErrorCode = this.getErrorCode();
            strategy.appendField(locator, this, "errorCode", buffer, theErrorCode);
        }
        {
            Integer theDeviceID;
            theDeviceID = this.getDeviceID();
            strategy.appendField(locator, this, "deviceID", buffer, theDeviceID);
        }
        {
            String theErrorTrace;
            theErrorTrace = this.getErrorTrace();
            strategy.appendField(locator, this, "errorTrace", buffer, theErrorTrace);
        }
        {
            String theSource;
            theSource = this.getSource();
            strategy.appendField(locator, this, "source", buffer, theSource);
        }
        {
            Severity theSeverity;
            theSeverity = this.getSeverity();
            strategy.appendField(locator, this, "severity", buffer, theSeverity);
        }
        {
            String theDetails;
            theDetails = this.getDetails();
            strategy.appendField(locator, this, "details", buffer, theDetails);
        }
        return buffer;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            Integer theErrorID;
            theErrorID = this.getErrorID();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "errorID", theErrorID), currentHashCode, theErrorID);
        }
        {
            String theErrorTime;
            theErrorTime = this.getErrorTime();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "errorTime", theErrorTime), currentHashCode, theErrorTime);
        }
        {
            ErrorCode theErrorCode;
            theErrorCode = this.getErrorCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "errorCode", theErrorCode), currentHashCode, theErrorCode);
        }
        {
            Integer theDeviceID;
            theDeviceID = this.getDeviceID();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "deviceID", theDeviceID), currentHashCode, theDeviceID);
        }
        {
            String theErrorTrace;
            theErrorTrace = this.getErrorTrace();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "errorTrace", theErrorTrace), currentHashCode, theErrorTrace);
        }
        {
            String theSource;
            theSource = this.getSource();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "source", theSource), currentHashCode, theSource);
        }
        {
            Severity theSeverity;
            theSeverity = this.getSeverity();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "severity", theSeverity), currentHashCode, theSeverity);
        }
        {
            String theDetails;
            theDetails = this.getDetails();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "details", theDetails), currentHashCode, theDetails);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
