package com.example.boardgames.Handlers;

import com.example.boardgames.Model.User;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ProfileHandler extends DefaultHandler {
    private List<User> users;
    private User currentUser;
    private StringBuilder sbText;

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (this.currentUser != null)
            sbText.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentUser != null) {
            if (localName.equals("user")) {
                users.add(currentUser);
            }
            sbText.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        users = new ArrayList<User>();
        sbText = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equals("user")) {
            currentUser = new User();
        } else if (localName.equals("firstname")) {
            currentUser.setFirstname(attributes.getValue("value"));
        } else if (localName.equals("lastname")) {
            currentUser.setLastname(attributes.getValue("value"));
        } else if (localName.equals("country")) {
            currentUser.setCountry(attributes.getValue("value"));
        } else if (localName.equals("stateorprovince")) {
            currentUser.setState(attributes.getValue("value"));
        } else if (localName.equals("lastlogin")) {
            currentUser.setLastLogin(attributes.getValue("value"));
        } else if (localName.equals("yearregistered")) {
            currentUser.setYearRegistered(attributes.getValue("value"));
        }
    }
}