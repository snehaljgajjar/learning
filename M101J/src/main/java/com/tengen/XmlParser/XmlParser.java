package com.tengen.XmlParser;

import org.apache.log4j.Logger;

/**
 * User: pgajjar Date: 8/23/13 Time: 12:02 PM
 */
public class XmlParser {
    private static Logger log = Logger.getLogger(XmlParser.class.getName());

    public static void main(String[] args) throws XmlParseException {
        if (args.length != 1) {
            System.out.println("usage: XmlParser <XML File Name>");
            System.exit(-1);
        }
        log.info(XmlParserUtils.parse(args[0]));
    }
}
