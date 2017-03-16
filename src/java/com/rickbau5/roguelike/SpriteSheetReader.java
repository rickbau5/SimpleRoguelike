package com.rickbau5.roguelike;

import com.rickbau5.roguelike.tiles.HidableTileTemplate;
import com.rickbau5.roguelike.tiles.TileTemplate;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.sprite.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rick on 3/14/2017.
 */
public final class SpriteSheetReader {
    public static ArrayList<TileTemplate> loadSpritesAsTiles(String spritePath, int spriteSheetId) {
        String xmlPath = spritePath.substring(0, spritePath.lastIndexOf('.')) + ".xml";
        File xmlFile = new File(xmlPath);
        assert xmlFile.exists();

        BufferedImage img = SpriteManager.load(spritePath);
        assert img != null;

        ArrayList<TileTemplate> tiles = new ArrayList<>();

        SpriteManager spriteManager = new SpriteManager(new SpriteSheet(img, spriteSheetId));

        DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = dbfact.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            Element root = (Element) doc.getElementsByTagName("tiles").item(0);
            int width = Integer.parseInt(root.getAttribute("width"));
            int height = Integer.parseInt(root.getAttribute("height"));

            NodeList nodeTiles = root.getElementsByTagName("tile");
            for (int i = 0; i < nodeTiles.getLength(); i++) {
                Element nodeTile = (Element) nodeTiles.item(i);
                int id = Integer.parseInt(nodeTile.getAttribute("id"));
                String name = nodeTile.getElementsByTagName("name").item(0).getTextContent();
                boolean passable = Boolean.parseBoolean(nodeTile.getElementsByTagName("passable").item(0).getTextContent());

                int x = id % (img.getWidth() / width);
                int y = id / (img.getHeight() / height);

                HidableTileTemplate tile = new HidableTileTemplate(spriteManager.getSectionAt(spriteSheetId, x * width, y * height, width, height), id, name, width, height, !passable);
                tiles.add(tile);
            }

        } catch (ParserConfigurationException ex) {
            System.out.println("Error instantiating parser.");
            ex.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return tiles;
    }
}
