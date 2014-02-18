package com.blogspot.toomuchcoding.xml.xmlunit

import org.custommonkey.xmlunit.XMLUnit

class XmlComparator {
    static void setupXmlUnit() {
        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreComments(true)
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true)
        XMLUnit.setNormalizeWhitespace(true)
    }
    
}
