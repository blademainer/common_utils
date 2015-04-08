/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiongyingqi.util.xml;

import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

import javax.xml.stream.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;

/**
 * Convenience methods for working with the StAX API. Partly historic due to JAXP 1.3 compatibility;
 * as of Spring 4.0, relying on JAXP 1.4 as included in JDK 1.6 and higher.
 * <p/>
 * <p>In particular, methods for using StAX ({@code javax.xml.stream}) in combination with the TrAX API
 * ({@code javax.xml.transform}), and converting StAX readers/writers into SAX readers/handlers and vice-versa.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 */
public abstract class StaxUtils {

    /**
     * Create a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource} for the given {@link javax.xml.stream.XMLStreamReader}.
     *
     * @param streamReader the StAX stream reader
     * @return a source wrapping the {@code streamReader}
     */
    public static Source createStaxSource(XMLStreamReader streamReader) {
        return new StAXSource(streamReader);
    }

    /**
     * Create a JAXP 1.4 a {@link javax.xml.transform.stax.StAXSource} for the given {@link javax.xml.stream.XMLEventReader}.
     *
     * @param eventReader the StAX event reader
     * @return a source wrapping the {@code eventReader}
     */
    public static Source createStaxSource(XMLEventReader eventReader) throws XMLStreamException {
        return new StAXSource(eventReader);
    }

    /**
     * Create a custom, non-JAXP 1.4 StAX {@link javax.xml.transform.Source} for the given {@link javax.xml.stream.XMLStreamReader}.
     *
     * @param streamReader the StAX stream reader
     * @return a source wrapping the {@code streamReader}
     */
    public static Source createCustomStaxSource(XMLStreamReader streamReader) {
        return new StaxSource(streamReader);
    }

    /**
     * Create a custom, non-JAXP 1.4 StAX {@link javax.xml.transform.Source} for the given {@link javax.xml.stream.XMLEventReader}.
     *
     * @param eventReader the StAX event reader
     * @return a source wrapping the {@code eventReader}
     */
    public static Source createCustomStaxSource(XMLEventReader eventReader) {
        return new StaxSource(eventReader);
    }

    /**
     * Indicate whether the given {@link javax.xml.transform.Source} is a JAXP 1.4 StAX Source or
     * custom StAX Source.
     *
     * @return {@code true} if {@code source} is a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource} or
     * custom StAX Source; {@code false} otherwise
     */
    public static boolean isStaxSource(Source source) {
        return (source instanceof StAXSource || source instanceof StaxSource);
    }

    /**
     * Return the {@link javax.xml.stream.XMLStreamReader} for the given StAX Source.
     *
     * @param source a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource}
     * @return the {@link javax.xml.stream.XMLStreamReader}
     * @throws IllegalArgumentException if {@code source} isn't a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource}
     *                                  or custom StAX Source
     */
    public static XMLStreamReader getXMLStreamReader(Source source) {
        if (source instanceof StAXSource) {
            return ((StAXSource) source).getXMLStreamReader();
        } else if (source instanceof StaxSource) {
            return ((StaxSource) source).getXMLStreamReader();
        } else {
            throw new IllegalArgumentException("Source '" + source + "' is neither StaxSource nor StAXSource");
        }
    }

    /**
     * Return the {@link javax.xml.stream.XMLEventReader} for the given StAX Source.
     *
     * @param source a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource}
     * @return the {@link javax.xml.stream.XMLEventReader}
     * @throws IllegalArgumentException if {@code source} isn't a JAXP 1.4 {@link javax.xml.transform.stax.StAXSource}
     *                                  or custom StAX Source
     */
    public static XMLEventReader getXMLEventReader(Source source) {
        if (source instanceof StAXSource) {
            return ((StAXSource) source).getXMLEventReader();
        } else if (source instanceof StaxSource) {
            return ((StaxSource) source).getXMLEventReader();
        } else {
            throw new IllegalArgumentException("Source '" + source + "' is neither StaxSource nor StAXSource");
        }
    }

    /**
     * Create a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult} for the given {@link javax.xml.stream.XMLStreamWriter}.
     *
     * @param streamWriter the StAX stream writer
     * @return a result wrapping the {@code streamWriter}
     */
    public static Result createStaxResult(XMLStreamWriter streamWriter) {
        return new StAXResult(streamWriter);
    }

    /**
     * Create a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult} for the given {@link javax.xml.stream.XMLEventWriter}.
     *
     * @param eventWriter the StAX event writer
     * @return a result wrapping {@code streamReader}
     */
    public static Result createStaxResult(XMLEventWriter eventWriter) {
        return new StAXResult(eventWriter);
    }

    /**
     * Create a custom, non-JAXP 1.4 StAX {@link javax.xml.transform.Result} for the given {@link javax.xml.stream.XMLStreamWriter}.
     *
     * @param streamWriter the StAX stream writer
     * @return a source wrapping the {@code streamWriter}
     */
    public static Result createCustomStaxResult(XMLStreamWriter streamWriter) {
        return new StaxResult(streamWriter);
    }

    /**
     * Create a custom, non-JAXP 1.4 StAX {@link javax.xml.transform.Result} for the given {@link javax.xml.stream.XMLEventWriter}.
     *
     * @param eventWriter the StAX event writer
     * @return a source wrapping the {@code eventWriter}
     */
    public static Result createCustomStaxResult(XMLEventWriter eventWriter) {
        return new StaxResult(eventWriter);
    }

    /**
     * Indicate whether the given {@link javax.xml.transform.Result} is a JAXP 1.4 StAX Result or
     * custom StAX Result.
     *
     * @return {@code true} if {@code result} is a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult} or
     * custom StAX Result; {@code false} otherwise
     */
    public static boolean isStaxResult(Result result) {
        return (result instanceof StAXResult || result instanceof StaxResult);
    }

    /**
     * Return the {@link javax.xml.stream.XMLStreamWriter} for the given StAX Result.
     *
     * @param result a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult}
     * @return the {@link javax.xml.stream.XMLStreamReader}
     * @throws IllegalArgumentException if {@code source} isn't a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult}
     *                                  or custom StAX Result
     */
    public static XMLStreamWriter getXMLStreamWriter(Result result) {
        if (result instanceof StAXResult) {
            return ((StAXResult) result).getXMLStreamWriter();
        } else if (result instanceof StaxResult) {
            return ((StaxResult) result).getXMLStreamWriter();
        } else {
            throw new IllegalArgumentException("Result '" + result + "' is neither StaxResult nor StAXResult");
        }
    }

    /**
     * Return the {@link javax.xml.stream.XMLEventWriter} for the given StAX Result.
     *
     * @param result a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult}
     * @return the {@link javax.xml.stream.XMLStreamReader}
     * @throws IllegalArgumentException if {@code source} isn't a JAXP 1.4 {@link javax.xml.transform.stax.StAXResult}
     *                                  or custom StAX Result
     */
    public static XMLEventWriter getXMLEventWriter(Result result) {
        if (result instanceof StAXResult) {
            return ((StAXResult) result).getXMLEventWriter();
        } else if (result instanceof StaxResult) {
            return ((StaxResult) result).getXMLEventWriter();
        } else {
            throw new IllegalArgumentException("Result '" + result + "' is neither StaxResult nor StAXResult");
        }
    }

    /**
     * Create a SAX {@link org.xml.sax.ContentHandler} that writes to the given StAX {@link javax.xml.stream.XMLStreamWriter}.
     *
     * @param streamWriter the StAX stream writer
     * @return a content handler writing to the {@code streamWriter}
     */
    public static ContentHandler createContentHandler(XMLStreamWriter streamWriter) {
        return new StaxStreamContentHandler(streamWriter);
    }

    /**
     * Create a SAX {@link org.xml.sax.ContentHandler} that writes events to the given StAX {@link javax.xml.stream.XMLEventWriter}.
     *
     * @param eventWriter the StAX event writer
     * @return a content handler writing to the {@code eventWriter}
     */
    public static ContentHandler createContentHandler(XMLEventWriter eventWriter) {
        return new StaxEventContentHandler(eventWriter);
    }

    /**
     * Create a SAX {@link org.xml.sax.XMLReader} that reads from the given StAX {@link javax.xml.stream.XMLStreamReader}.
     *
     * @param streamReader the StAX stream reader
     * @return a XMLReader reading from the {@code streamWriter}
     */
    public static XMLReader createXMLReader(XMLStreamReader streamReader) {
        return new StaxStreamXMLReader(streamReader);
    }

    /**
     * Create a SAX {@link org.xml.sax.XMLReader} that reads from the given StAX {@link javax.xml.stream.XMLEventReader}.
     *
     * @param eventReader the StAX event reader
     * @return a XMLReader reading from the {@code eventWriter}
     */
    public static XMLReader createXMLReader(XMLEventReader eventReader) {
        return new StaxEventXMLReader(eventReader);
    }

    /**
     * Return a {@link javax.xml.stream.XMLStreamReader} that reads from a {@link javax.xml.stream.XMLEventReader}.
     * Useful because the StAX {@code XMLInputFactory} allows one to create an
     * event reader from a stream reader, but not vice-versa.
     *
     * @return a stream reader that reads from an event reader
     */
    public static XMLStreamReader createEventStreamReader(XMLEventReader eventReader) throws XMLStreamException {
        return new XMLEventStreamReader(eventReader);
    }

    /**
     * Return a {@link javax.xml.stream.XMLStreamWriter} that writes to a {@link javax.xml.stream.XMLEventWriter}.
     *
     * @return a stream writer that writes to an event writer
     * @since 3.2
     */
    public static XMLStreamWriter createEventStreamWriter(XMLEventWriter eventWriter) {
        return new XMLEventStreamWriter(eventWriter, XMLEventFactory.newFactory());
    }

    /**
     * Return a {@link javax.xml.stream.XMLStreamWriter} that writes to a {@link javax.xml.stream.XMLEventWriter}.
     *
     * @return a stream writer that writes to an event writer
     * @since 3.0.5
     */
    public static XMLStreamWriter createEventStreamWriter(XMLEventWriter eventWriter, XMLEventFactory eventFactory) {
        return new XMLEventStreamWriter(eventWriter, eventFactory);
    }

}
