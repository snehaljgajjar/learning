package com.tengen.MCDParser.utils;

import com.tengen.MCDParser.ContainerParsers.QuicktimeContainerParser;
import com.tengen.MCDParser.utils.MCDXmlTagName.McdType;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * User: pgajjar Date: 8/22/13 Time: 5:41 PM
 */
public class MCDParserUtils {
    private static Logger log = Logger.getLogger(MCDParserUtils.class.getName());

    private static String readFullFile(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        byte[] b = new byte[(int) file.length()];
        is.read(b, 0, (int) file.length());
        return new String(b);
    }

    public static Document loadXMLFromString(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static Node parse(String xmlFileName) throws MCDParseException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            dbf.setIgnoringElementContentWhitespace(true);

            String descriptionXml = readFullFile(new File(xmlFileName));

            // Document document = dbf.newDocumentBuilder().parse(descriptionXml);
            Document document = loadXMLFromString(descriptionXml);
//            document.getDocumentElement().normalize();

            McdType mcdType = McdType.getMcdType(document.getDocumentElement());
            switch (mcdType) {
                case Mpeg2Movie:
                    break;
                case JpegAsset:
                    break;
                case IttSubtitleAsset:
                    break;
                case SccCaptionAsset:
                    break;
                case Ac3AudioAsset:
                    break;
                case QuickTimeMovie:
                    QuicktimeContainerParser quicktimeContainerParser = new QuicktimeContainerParser(document);
                    break;
                case Invalid:
                    log.error("Error: Invalid MCD Document detected with root tag: " + document.getDocumentElement().getTagName() + ", not supported at this moment.");
                    throw new MCDParseException(MCDParseException.XmlParseErrorCode.XML_PARSING_ERROR, "Invalid MCD detected for file: " + xmlFileName, null);
            }

            return document;
        } catch (ParserConfigurationException e) {
            throw new MCDParseException(MCDParseException.XmlParseErrorCode.XML_PARSING_ERROR, "Parse Configuration Error for file: " + xmlFileName, e);
        } catch (SAXException e) {
            throw new MCDParseException(MCDParseException.XmlParseErrorCode.XML_PARSING_ERROR, "Unable to parse file: " + xmlFileName, e);
        } catch (IOException e) {
            throw new MCDParseException(MCDParseException.XmlParseErrorCode.XML_FILE_IO_ERROR, "IO Error for file: " + xmlFileName, e);
        }
    }
}
