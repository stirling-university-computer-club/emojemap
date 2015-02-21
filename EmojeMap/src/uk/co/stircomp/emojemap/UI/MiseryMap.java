// License: GPL. For details, see Readme.txt file.
package uk.co.stircomp.emojemap.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import uk.co.stircomp.emojemap.data.DataManager;
import uk.co.stircomp.emojemap.data.Emotion;
import uk.co.stircomp.emojemap.data.Region;

/**
 * Demonstrates the usage of {@link JMapViewer}
 *
 * @author Jan Peter Stotz
 *
 */
public class MiseryMap extends JFrame implements JMapViewerEventListener  {

    private static final long serialVersionUID = 1L;

    private JMapViewerTree treeMap = null;

    private JLabel zoomLabel=null;
    private JLabel zoomValue=null;

    private JLabel mperpLabelName=null;
    private JLabel mperpLabelValue = null;
    
    final DataManager data;

    /**
     * Constructs the {@code Demo}.
     */
    public MiseryMap(final DataManager data) {
        super("Misery Mapping");
    	this.setVisible(true);
        setSize(400, 400);
        
    	this.data = data;

        treeMap = new JMapViewerTree("Zones");

        // Listen to the map viewer for user operations so components will
        // receive events and update
        map().addJMVListener(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName=new JLabel("Meters/Pixels: ");
        mperpLabelValue=new JLabel(String.format("%s",map().getMeterPerPixel()));

        zoomLabel=new JLabel("Zoom: ");
        zoomValue=new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });
        JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
                new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(),
                new BingAerialTileSource(),
                new MapQuestOsmTileSource(),
                new MapQuestOpenAerialTileSource() });
        tileSourceSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map().setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox<TileLoader> tileLoaderSelector;
        try {
            tileLoaderSelector = new JComboBox<>(new TileLoader[] { new OsmFileCacheTileLoader(map()), new OsmTileLoader(map()) });
        } catch (IOException e) {
            tileLoaderSelector = new JComboBox<>(new TileLoader[] { new OsmTileLoader(map()) });
        }
        tileLoaderSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map().setTileLoader((TileLoader) e.getItem());
            }
        });
        map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        panelTop.add(tileSourceSelector);
       // panelTop.add(tileLoaderSelector);
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map().getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
       // panelBottom.add(showMapMarker);
        ///
        final JCheckBox showTreeLayers = new JCheckBox("Tree Layers visible");
        showTreeLayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                treeMap.setTreeVisible(showTreeLayers.isSelected());
            }
        });
     //   panelBottom.add(showTreeLayers);
        ///
        final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
        showToolTip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setToolTipText(null);
            }
        });
      //  panelBottom.add(showToolTip);
        ///
        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map().isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setTileGridVisible(showTileGrid.isSelected());
            }
        });
     //   panelBottom.add(showTileGrid);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomControlsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
     //   panelBottom.add(showZoomControls);
        final JCheckBox scrollWrapEnabled = new JCheckBox("Scrollwrap enabled");
        scrollWrapEnabled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map().setScrollWrapEnabled(scrollWrapEnabled.isSelected());
            }
        });
        
        final ArrayList<MapPolygonImpl> countryList = new ArrayList<MapPolygonImpl>();
        MapPolygonImpl turkey = new MapPolygonImpl("Turkey", c(37, 44), c(39, 44), c(41,42), c(41, 38), c(42, 33), c(41, 30), c(41, 26), c(36, 27), c(36, 35));
        countryList.add(turkey);  
        MapPolygonImpl ukraine = new MapPolygonImpl("Ukraine", c(47, 38), c(50, 40), c(52, 34), c(51, 30), c(52, 24), c(51, 24), c(48, 22), c(48, 28), c(46, 31));
        countryList.add(ukraine);
        MapPolygonImpl uk = new MapPolygonImpl("UK", c(51, 1), c(53, 2), c(56, -3), c(58, -2), c(58, -4), c(59, -3), c(59, -5), c(58, -7), c(54, -3), c(53, -4), c(50, -5));
        countryList.add(uk);  
        MapPolygonImpl sweden = new MapPolygonImpl("Sweden", c(56, 16), c(60, 19), c(61, 17), c(66, 24), c(69, 21), c(64, 13), c(59, 11), c(55, 13));
        countryList.add(sweden);
        MapPolygonImpl finland = new MapPolygonImpl("Finland", c(70, 28), c(69, 25), c(69, 21), c(66, 24), c(65, 25), c(63, 21), c(60, 23), c(62, 30), c(70, 29));
        countryList.add(finland);
        MapPolygonImpl france = new MapPolygonImpl("France", c(51, 2), c(48, -5), c(46, -1), c(43, -1), c(42, 3), c(44, 4), c(43, 6), c(44, 8), c(47, 6), c(49, 8));
        countryList.add(france);
        MapPolygonImpl germany = new MapPolygonImpl("Germany", c(54, 7), c(50, 6), c(49, 8), c(48, 8), c(49, 14), c(50, 12), c(51, 15), c(54, 13), c(55, 9));
        countryList.add(germany);
        MapPolygonImpl italy = new MapPolygonImpl("Italy", c(47, 13), c(47, 10), c(46, 7), c(44, 8), c(44, 9), c(39, 16), c(38, 16), c(40, 17), c(40, 18), c(45, 12));
        countryList.add(italy);
        MapPolygonImpl norway = new MapPolygonImpl("Norway", c(59, 11), c(58, 6), c(62, 5), c(70, 18), c(70, 31), c(69, 25), c(69, 20), c(63, 12));
        countryList.add(norway);
        MapPolygonImpl lithuania = new MapPolygonImpl("Lithuania" , c(56, 21), c(55, 21), c(55, 23), c(54, 23), c(54, 24), c(54, 26), c(56, 27), c(56, 25));
        countryList.add(lithuania);
        MapPolygonImpl holland = new MapPolygonImpl("Holland", c(51, 3), c(53, 5), c(53, 7), c(52, 6), c(51, 6));
        countryList.add(holland);
        MapPolygonImpl russia = new MapPolygonImpl("Russia", c(44, 40), c(60, 29), c(70, 31), c(77, 105), c(66, 190), c(49, 134), c(52, 99), c(54, 61), c(50, 47), c(45, 47));
        countryList.add(russia);
        MapPolygonImpl spain = new MapPolygonImpl("Spain", c(42, -9), c(44, -8), c(42, 3), c(40, -1), c(37, -2), c(36, -6), c(37, -7), c(42, -7)); 
        countryList.add(spain);  
        MapPolygonImpl poland = new MapPolygonImpl("Poland", c(54, 15), c(54, 23), c(50, 24), c(49, 20), c(51, 15));
        countryList.add(poland);
        
        
        final JCheckBox showAnger = new JCheckBox("Anger Visible");
        showAnger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (showAnger.isSelected())
            	{
            		for(int i = 0; i < countryList.size(); i++){
            			float j = data.getRegionalIndex(Region.getRegionIndex("africa"), Emotion.getEmotionIndex("anger"));
            			System.out.println(j);
            			countryList.get(i).setBackColor(new Color(255, 0, 0, (int)(j * 100 + 1)));
            		}

            		for(int i = 0; i < countryList.size(); i++){
            			map().addMapPolygon(countryList.get(i));
            		}
            	}
            	else
            	{
            		for(int i = 0; i < countryList.size(); i++){
            			map().removeMapPolygon(countryList.get(i));
            		}
            	}
            }
        });
        panelBottom.add(showAnger);
        
        //LayerGroup germanyGroup = new LayerGroup("Germany");
    	//Layer germanyWestLayer = germanyGroup.addLayer("Germany West");
        //MapMarkerCircle germanyThing = new MapMarkerCircle("Place", germanyGroup, .5);
        //map().addMapMarker(germanyThing);
        
        
        panelBottom.add(scrollWrapEnabled);
        //panelBottom.add(button);

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        add(treeMap, BorderLayout.CENTER);


        //
//        LayerGroup germanyGroup = new LayerGroup("Germany");
//        Layer germanyWestLayer = germanyGroup.addLayer("Germany West");
//        Layer germanyEastLayer = germanyGroup.addLayer("Germany East");
//        MapMarkerDot eberstadt = new MapMarkerDot(germanyEastLayer, "Eberstadt", 49.814284999, 8.642065999);
//        MapMarkerDot ebersheim = new MapMarkerDot(germanyWestLayer, "Ebersheim", 49.91, 8.24);
//        MapMarkerDot empty = new MapMarkerDot(germanyEastLayer, 49.71, 8.64);
//        MapMarkerDot darmstadt = new MapMarkerDot(germanyEastLayer, "Darmstadt", 49.8588, 8.643);
//        map().addMapMarker(eberstadt);
//        map().addMapMarker(ebersheim);
//        map().addMapMarker(empty);
//        Layer franceLayer = treeMap.addLayer("France");
//        map().addMapMarker(new MapMarkerDot(franceLayer, "La Gallerie", 48.71, -1));
//        map().addMapMarker(new MapMarkerDot(43.604, 1.444));
//        map().addMapMarker(new MapMarkerCircle(53.343, -6.267, 0.666));
//        map().addMapRectangle(new MapRectangleImpl(new Coordinate(53.343, -6.267), new Coordinate(43.604, 1.444)));
//        map().addMapMarker(darmstadt);
//        treeMap.addLayer(germanyWestLayer);
//        treeMap.addLayer(germanyEastLayer);
//
//        MapPolygon bermudas = new MapPolygonImpl(c(49,1), c(45,10), c(40,5));
//        map().addMapPolygon( bermudas );
//        map().addMapPolygon( new MapPolygonImpl(germanyEastLayer, "Riedstadt", ebersheim, darmstadt, eberstadt, empty));
//
//        map().addMapMarker(new MapMarkerCircle(germanyWestLayer, "North of Suisse", new Coordinate(48, 7), .5));
//        Layer spain = treeMap.addLayer("Spain");
//        map().addMapMarker(new MapMarkerCircle(spain, "La Garena", new Coordinate(40.4838, -3.39), .002));
//        spain.setVisible(false);
//
//        Layer wales = treeMap.addLayer("UK");
//        map().addMapRectangle(new MapRectangleImpl(wales, "Wales", c(53.35,-4.57), c(51.64,-2.63)));

        // map.setDisplayPosition(new Coordinate(49.807, 8.6), 11);
        // map.setTileGridVisible(true);

        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                if(showToolTip.isSelected()) map().setToolTipText(map().getPosition(p).toString());
            }
        });
    }
    private JMapViewer map(){
        return treeMap.getViewer();
    }
    private static Coordinate c(double lat, double lon){
        return new Coordinate(lat, lon);
    }

    private void updateZoomParameters() {
        if (mperpLabelValue!=null)
            mperpLabelValue.setText(String.format("%s",map().getMeterPerPixel()));
        if (zoomValue!=null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }

    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
    


}
