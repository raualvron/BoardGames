package com.example.boardgames.Handlers;

import com.example.boardgames.Model.Hotness;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class HotnessHandler extends DefaultHandler {
    private List<Hotness> games;
    private Hotness currentGame;
    private StringBuilder sbText;

    public List<Hotness> getGames() {
        return games;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (this.currentGame != null)
            sbText.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentGame != null) {
            if (localName.equals("item")) {
                games.add(currentGame);
            }
            sbText.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        games = new ArrayList<Hotness>();
        sbText = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);

        if (localName.equals("item")) {
            currentGame = new Hotness();
            currentGame.setRank(attributes.getValue("rank"));
        } else if (localName.equals("thumbnail")) {
            currentGame.setImage(attributes.getValue("value"));
        } else if (localName.equals("name")) {
            currentGame.setNameGame(attributes.getValue("value"));
        } else if (localName.equals("yearpublished")) {
            currentGame.setYear(attributes.getValue("value"));
        }
    }
}