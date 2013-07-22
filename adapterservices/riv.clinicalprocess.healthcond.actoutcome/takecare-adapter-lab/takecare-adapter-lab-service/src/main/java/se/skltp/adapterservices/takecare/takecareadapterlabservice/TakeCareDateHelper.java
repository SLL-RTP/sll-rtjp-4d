package se.skltp.adapterservices.takecare.takecareadapterlabservice;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

public final class TakeCareDateHelper {

	private static final String ss = "00";

	public static final BigInteger yyyyMMddHHmmss(Date date) {
		FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss");
		return new BigInteger(dateFormat.format(date));
	}

	public static final BigInteger yyyyMMddHHmm(Date date) {
		FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMddHHmm");
		return new BigInteger(dateFormat.format(date));
	}

	/**
	 * RIV TA is YYYYMMDDttmmss, Take Care YYYYMMDDttmm
	 * 
	 * @return
	 */
	public static final String toTakeCareLongTime(String yyyyMMddHHmmss) {
		if (StringUtils.isEmpty(yyyyMMddHHmmss) || yyyyMMddHHmmss.length() != 14) {
			throw new RuntimeException("Time is not in correct RIV TA format, expecting yyyyMMddHHmmss");
		}
		return yyyyMMddHHmmss.substring(0, 12);
	}

	/**
	 * RIV TA is YYYYMMDDttmmss, Take Care YYYYMMDD
	 * 
	 * @return
	 */
	public static final Long toTakeCareShortTime(String yyyyMMddHHmmss) {
		if (StringUtils.isEmpty(yyyyMMddHHmmss) || yyyyMMddHHmmss.length() != 14
				|| !StringUtils.isNumeric(yyyyMMddHHmmss)) {
			throw new RuntimeException("Time is not in correct RIV TA format, expecting yyyyMMddHHmmss");
		}
		return Long.valueOf(yyyyMMddHHmmss.substring(0, 8));
	}

	/**
	 * RIV TA is YYYYMMDDttmmss, Take Care YYYYMMDDttmm
	 * 
	 * @return
	 */
	public static final String toRivTaLongTime(BigInteger yyyyMMddHHmm) {
		if (yyyyMMddHHmm == null || yyyyMMddHHmm.toString().length() != 12) {
			throw new RuntimeException("Time is not in correct Take Care format, expecting yyyyMMddHHmm");
		}
		return String.valueOf(yyyyMMddHHmm + ss);
	}
	
	public static final XMLGregorianCalendar convertToXmlDate(Date myDate) {
	        GregorianCalendar c = new GregorianCalendar();
	        c.setTime(myDate);
	        XMLGregorianCalendar date = null;
	        try {
	            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	        } catch (DatatypeConfigurationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return date;
	    }
}
