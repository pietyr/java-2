package org.example.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

public class XmlUrlReader {

    public static <T> T readObservationFromUrl(String urlString, Class<T> clazz) {

        try {
            URL url = URI.create(urlString).toURL();
            InputStream inputStream = url.openStream();

            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T t = clazz.cast(unmarshaller.unmarshal(inputStream));

            inputStream.close();
            return t;
        }
        catch (MalformedURLException _ignored) { return null; }
        catch (IOException _ignored) { return null; }
        catch (JAXBException _ignored) { return null; }
    }
}