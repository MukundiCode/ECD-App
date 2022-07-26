package com.example.ecd_app

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


/**
 * @author Mukundi Chitamba
 * This class will take xml as string or as document and use Rome to
 * help with processing
 */

class XMLUtil () {
//    val XMLString: String = XMLString
//
//    fun test(){
//        var document: Document? = loadXMLFromString(XMLString)
//        if (document != null) {
//            System.out.println(document.getElementsByTagName("p").item(1).textContent)
//        }
//    }

    @Throws(Exception::class)
    fun loadXMLFromString(xml: String?): Document? {
        var xmlWithRoot = "<root>" + xml + "</root>"
        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val builder: DocumentBuilder = factory.newDocumentBuilder()
        val `is` = InputSource(StringReader(xmlWithRoot))
        return builder.parse(`is`)
    }

    fun getStringFromDocument(document : Document?): String {
        var result : String = ""
        var textElements = document!!.getElementsByTagName("p")
        var size = textElements.length
        for (i in 0..size-1){
            result = result + document!!.getElementsByTagName("p").item(i).textContent
        }
        return result
    }
}