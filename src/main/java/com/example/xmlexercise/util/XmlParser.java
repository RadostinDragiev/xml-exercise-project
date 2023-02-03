package com.example.xmlexercise.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    void marshalFile(Object object, String path) throws JAXBException;

    <T>Object unmarshalFile(Class<T> tClass, String path) throws JAXBException, FileNotFoundException;
}
