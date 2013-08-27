package com.tengen.MCDParser;

import com.tengen.MCDParser.utils.MCDParseException;
import com.tengen.MCDParser.utils.MCDParserUtils;
import org.apache.log4j.Logger;

/**
 * User: pgajjar Date: 8/23/13 Time: 12:02 PM
 */
public class MCDParser {
    private static Logger log = Logger.getLogger(MCDParser.class.getName());

    public static void main(String[] args) throws MCDParseException {
        if (args.length != 1) {
            System.out.println("usage: MCDParser <XML File Name>");
            System.exit(-1);
        }
        log.info(MCDParserUtils.parse(args[0]));
    }
}
