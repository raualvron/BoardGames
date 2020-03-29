package com.example.boardgames.Handlers;

import android.util.Log;

import com.example.boardgames.Model.Game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GameHandler extends DefaultHandler {

    private Game currentGame;
    private StringBuilder sbText;
    String currentValue = "";
    boolean currentEl = false;

    public Game getGame() {
        return currentGame;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentEl) {
            currentValue = currentValue +  new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (localName.equalsIgnoreCase("image")) {
            currentGame.setImage(currentValue);
        } else if (localName.equalsIgnoreCase("description")) {
            currentGame.setDescription(currentValue);
        }
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        currentEl = true;
        currentValue = "";
        if (localName.equals("item")) {
            this.currentGame = new Game();
        } else if (localName.equals("name") && this.currentGame.getName() == null) {
            this.currentGame.setName(attributes.getValue("value"));
        } else if (localName.equals("yearpublished")) {
            this.currentGame.setYearPublished(attributes.getValue("value"));
        } else if (localName.equals("minplayers")) {
            this.currentGame.setMinPlayer(attributes.getValue("value"));
        } else if (localName.equals("maxplayers")) {
            this.currentGame.setMaxPlayer(attributes.getValue("value"));
        } else if (localName.equals("link") && this.currentGame.getCategory() == null) {
            this.currentGame.setCategory(attributes.getValue("value"));
        } else if (localName.equals("playingtime")) {
            this.currentGame.setPlayingTime(attributes.getValue("value"));
        } else if (localName.equals("minplaytime")) {
            this.currentGame.setMinPlayTime(attributes.getValue("value"));
        } else if (localName.equals("maxplaytime")) {
            this.currentGame.setMaxPlayTime(attributes.getValue("value"));
        } else if (localName.equals("minage")) {
            this.currentGame.setMinAge(attributes.getValue("value"));
        }
    }
}
