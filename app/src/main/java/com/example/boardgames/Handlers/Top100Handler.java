// <forum id="10" title="Hot Deals" numthreads="21726" numposts="287115" lastpostdate="Thu, 01 Jan 1970 00:00:00 +0000" noposting="0" termsofuse="https://boardgamegeek.com/xmlapi/termsofuse">
//   <threads>
//      <thread id="1" subject="Subject" author="Author" numarticles="1" postdate="Wed, 12 Aug 2015 14:37:12 +0000" lastpostdate="Wed, 12 Aug 2015 14:37:12 +0000"/>
//      ...
//      ...
//   </threads>
// </forum>

package com.example.boardgames.Handlers;

import com.example.boardgames.Model.Top100;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class Top100Handler  extends DefaultHandler  {
    private List<Top100> theards;
    private Top100 currentThread;
    private StringBuilder sbText;

    public List<Top100> getTheards() {
        return theards;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (this.currentThread != null)
            sbText.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentThread != null) {
            if (localName.equals("thread")) {
                theards.add(currentThread);
            }
            sbText.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        theards = new ArrayList<Top100>();
        sbText = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);

        if (localName.equals("thread")) {
            currentThread = new Top100();
            currentThread.setId(attributes.getValue("id"));
            currentThread.setSubject(attributes.getValue("subject"));
            currentThread.setPostdate(attributes.getValue("postdate"));
            currentThread.setAuthor(attributes.getValue("author"));
        }
    }
}