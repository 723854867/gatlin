package org.gatlin.util.serial;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.gatlin.util.Consts;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public interface SerializeUtil {
	
	public static final Gson GSON = new Gson();
	public static final Gson GSON_ANNO = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	class REDIS {
		public static final byte[] encode(Object value) {
			return (value instanceof byte[]) ? (byte[]) value : _encode(value.toString());
		}
		
		public static final byte[][] encode(Object... params) {
			byte[][] buffer = new byte[params.length][];
			for (int i = 0, len = params.length; i < len; i++) {
				if (params[i] instanceof byte[])
					buffer[i] = (byte[]) params[i];
				else
					buffer[i] = _encode(params[i].toString());
			}
			return buffer;
		}
		
		private static final byte[] _encode(String value) {
			return value.getBytes(Consts.UTF_8);
		}
	}
	
	class JSON {
		private static final Type MAP = new TypeToken<Map<String, String>>() {}.getType();
		public static final Map<String, String> beanToMap(Gson gson, Object bean) {
			String json = gson.toJson(bean);
			return gson.fromJson(json, MAP);
		}
	}
	
	class XmlUtil {

		/**
		 * Please be attention, the clazz must annotated
		 * with @XmlRootElement(name="rootElementName")
		 * 
		 * @param xml
		 * @param clazz
		 * @return
		 * @throws JAXBException
		 */
		@SuppressWarnings("unchecked")
		public static <T> T xmlToBean(String xml, Class<T> clazz) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(xml);
				SAXParserFactory sax = SAXParserFactory.newInstance();
				sax.setNamespaceAware(false);
				XMLReader xmlReader = sax.newSAXParser().getXMLReader();
				Source source = new SAXSource(xmlReader, new InputSource(reader));
				T instance = (T) unmarshaller.unmarshal(source);
				return instance;
			} catch (JAXBException | SAXException | ParserConfigurationException e) {
				throw new RuntimeException("Serial failure!", e);
			}
		}

		public static String beanToXml(Object obj, String encoding) {
			String strxml = null;
			try {
				JAXBContext context = JAXBContext.newInstance(obj.getClass());
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
				StringWriter writer = new StringWriter();
				marshaller.marshal(obj, writer);
				strxml = writer.toString();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return strxml;
		}
	}
}
