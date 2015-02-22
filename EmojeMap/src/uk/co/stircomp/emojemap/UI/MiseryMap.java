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
        MapPolygonImpl ukraine = new MapPolygonImpl("Ukraine", c(47, 38), c(50, 40), c(52, 34), c(51, 30), c(52, 24), c(51, 24), c(48, 22), c(48, 28), c(46, 31));
        countryList.add(ukraine);
        MapPolygonImpl germany = new MapPolygonImpl("Germany", c(54, 7), c(50, 6), c(49, 8), c(48, 8), c(49, 14), c(50, 12), c(51, 15), c(54, 13), c(55, 9));
        countryList.add(germany);
        MapPolygonImpl holland = new MapPolygonImpl("Holland", c(51, 3), c(53, 5), c(53, 7), c(52, 6), c(51, 6));
        countryList.add(holland);
        MapPolygonImpl russia = new MapPolygonImpl("Russia", c(42, 48), c(44, 40), c(45, 37), c(49, 40), c(52, 34), c(56, 31), c(56, 28), c(61, 28), c(63, 32), c(69, 29), c(70, 31), c(66, 43), c(69, 60), c(68, 68), c(73, 70), c(74, 87), c(75, 87), c(78, 104), c(76, 114), c(74, 113), c(72, 139), c(73, 141), c(66, 190), c(51, 157), c(57, 156), c(62, 164), c(59, 143), c(55, 135), c(53, 141), c(43, 135), c(43, 131), c(48, 135), c(53, 124), c(50, 118), c(49, 88), c(55, 71), c(54, 61), c(51, 60), c(52, 47));
        countryList.add(russia);
        MapPolygonImpl poland = new MapPolygonImpl("Poland", c(54, 15), c(54, 23), c(50, 24), c(49, 20), c(51, 15));
        countryList.add(poland);
        MapPolygonImpl italy = new MapPolygonImpl("Italy", c(43.78,7.532), c(44.17,7.662), c(44.28,6.976), c(44.54,6.853), c(44.83,7.032), c(45.12,6.624), c(45.26,7.128), c(45.43,7.147), c(45.79,6.799), c(45.93,7.038), c(45.92,7.856), c(46.46,8.441), c(46.25,8.445), c(45.84,9.037), c(46.50,9.294), c(46.31,9.545), c(46.38,9.947), c(46.23,10.14), c(46.54,10.05), c(46.54,10.46), c(46.87,10.47), c(46.77,11.02), c(46.97,11.18), c(47.09,12.19), c(46.93,12.16), c(46.69,12.44), c(46.53,13.72), c(46.30,13.38), c(46.18,13.67), c(46.01,13.48), c(45.64,13.92), c(45.60,13.72), c(45.77,13.63), c(45.78,13.19), c(45.64,13.09), c(45.47,12.29), c(45.26,12.16), c(44.96,12.54), c(44.72,12.25), c(44.25,12.37), c(43.57,13.60), c(42.67,14.02), c(42.09,14.74), c(41.92,15.16), c(41.91,16.15), c(41.79,16.19), c(41.61,15.90), c(41.44,15.99), c(40.64,18.01), c(40.13,18.51), c(39.79,18.35), c(39.94,18.04), c(40.28,17.86), c(40.33,17.39), c(40.41,17.20), c(40.49,17.32), c(40.45,16.92), c(39.75,16.49), c(39.40,17.15), c(38.96,17.17), c(38.80,16.60), c(38.43,16.57), c(37.92,16.06), c(38.00,15.64), c(38.23,15.63), c(38.47,15.91), c(38.63,15.83), c(38.87,16.22), c(40.03,15.66), c(40.00,15.35), c(40.23,14.94), c(40.63,14.83), c(40.60,14.40), c(40.75,14.45), c(40.82,14.07), c(41.25,13.71), c(41.26,13.03), c(42.30,11.63), c(42.40,11.10), c(42.55,11.16), c(42.96,10.59), c(44.00,10.11), c(44.43,8.747), c(43.82,7.773), c(43.78,7.532));
        countryList.add(italy);
        MapPolygonImpl uk = new MapPolygonImpl("United Kingdom", c(58.65,-3.023), c(58.38,-3.110), c(58.28,-3.337), c(57.87,-4.007), c(57.85,-3.774), c(57.57,-4.432), c(57.70,-2.076), c(57.46,-1.773), c(56.58,-2.531), c(56.36,-3.278), c(56.45,-2.884), c(56.27,-2.583), c(56.03,-3.724), c(55.94,-3.053), c(56.05,-2.631), c(55.58,-1.636), c(54.76,-1.297), c(54.11,-0.07931), c(54.01,-0.2122), c(53.58,0.1421), c(53.62,-0.1396), c(53.40,0.2356), c(53.09,0.3392), c(52.88,0.002109), c(52.78,0.3789), c(52.97,0.5478), c(52.97,0.8847), c(52.75,1.675), c(52.46,1.749), c(52.08,1.587), c(51.93,1.331), c(52.02,1.163), c(51.84,1.266), c(51.72,0.7011), c(51.74,0.9357), c(51.61,0.9525), c(51.45,0.3889), c(51.39,1.386), c(51.18,1.408), c(51.10,1.263), c(50.74,0.2539), c(50.85,-1.094), c(50.56,-2.425), c(50.73,-2.927), c(50.60,-3.437), c(50.21,-3.717), c(50.36,-4.380), c(50.17,-5.048), c(49.96,-5.193), c(50.12,-5.471), c(50.06,-5.717), c(50.60,-4.772), c(51.19,-4.228), c(51.21,-3.028), c(51.76,-2.380), c(51.38,-3.346), c(51.62,-3.838), c(51.54,-4.243), c(51.68,-4.075), c(51.73,-4.574), c(51.59,-4.941), c(51.62,-5.051), c(51.75,-4.884), c(51.73,-5.247), c(51.78,-5.103), c(51.92,-5.239), c(52.33,-4.131), c(52.91,-4.134), c(52.79,-4.758), c(53.21,-4.196), c(53.35,-2.705), c(53.36,-2.954), c(53.56,-3.106), c(53.72,-2.900), c(53.91,-3.053), c(54.22,-2.814), c(54.10,-3.215), c(54.51,-3.633), c(54.88,-3.381), c(54.97,-3.027), c(54.99,-3.571), c(54.77,-3.965), c(54.91,-4.398), c(54.68,-4.387), c(54.87,-4.852), c(54.80,-4.961), c(54.63,-4.864), c(54.99,-5.178), c(55.49,-4.614), c(55.70,-4.916), c(55.94,-4.878), c(55.92,-4.485), c(55.99,-4.855), c(56.11,-4.829), c(55.86,-4.987), c(55.85,-5.305), c(56.23,-5.032), c(56.01,-5.429), c(55.35,-5.523), c(55.31,-5.789), c(56.33,-5.571), c(56.56,-5.070), c(56.48,-5.399), c(56.81,-5.121), c(56.49,-5.677), c(56.63,-6.010), c(56.69,-5.552), c(56.70,-6.228), c(56.87,-5.663), c(56.89,-5.920), c(57.00,-5.524), c(57.11,-5.725), c(57.11,-5.403), c(57.16,-5.648), c(57.23,-5.405), c(57.33,-5.599), c(57.42,-5.451), c(57.36,-5.818), c(57.57,-5.840), c(57.53,-5.510), c(57.64,-5.811), c(57.82,-5.817), c(57.85,-5.103), c(58.07,-5.453), c(58.08,-5.281), c(58.26,-5.389), c(58.26,-5.072), c(58.35,-5.175), c(58.62,-5.002), c(58.56,-4.702), c(58.45,-4.761), c(58.57,-4.578), c(58.65,-3.023));
        countryList.add(uk);
        MapPolygonImpl france = new MapPolygonImpl("France", c(47.25,-2.140), c(47.30,-2.540), c(47.50,-2.367), c(47.50,-2.832), c(47.64,-2.700), c(47.60,-3.125), c(47.48,-3.133), c(47.65,-3.217), c(47.89,-3.982), c(47.81,-4.367), c(48.04,-4.722), c(48.12,-4.283), c(48.17,-4.547), c(48.32,-4.565), c(48.33,-4.761), c(48.51,-4.777), c(48.67,-4.354), c(48.68,-3.582), c(48.83,-3.537), c(48.87,-3.226), c(48.50,-2.685), c(48.68,-2.307), c(48.51,-1.976), c(48.69,-1.988), c(48.64,-1.369), c(48.75,-1.563), c(49.22,-1.610), c(49.72,-1.942), c(49.68,-1.264), c(49.37,-1.110), c(49.28,-0.2283), c(49.45,0.4247), c(49.52,0.07584), c(49.70,0.1860), c(50.12,1.461), c(50.88,1.625), c(51.09,2.542), c(50.82,2.651), c(50.78,3.159), c(50.52,3.297), c(50.28,4.165), c(49.98,4.149), c(49.95,4.511), c(50.17,4.825), c(49.80,4.868), c(49.51,5.473), c(49.55,5.808), c(49.46,6.362), c(49.17,6.729), c(49.18,7.426), c(48.96,8.226), c(48.58,7.802), c(48.12,7.578), c(47.58,7.588), c(47.43,7.385), c(47.50,6.991), c(47.29,6.972), c(46.59,6.129), c(46.26,6.116), c(46.21,5.967), c(46.15,6.133), c(46.39,6.296), c(46.45,6.738), c(46.15,6.783), c(45.93,7.038), c(45.79,6.799), c(45.43,7.147), c(45.26,7.128), c(45.12,6.624), c(44.83,7.032), c(44.54,6.853), c(44.28,6.976), c(44.17,7.662), c(43.78,7.532), c(43.76,7.439), c(43.76,7.403), c(43.73,7.392), c(43.31,6.636), c(43.19,6.642), c(43.05,6.165), c(43.35,5.040), c(43.47,5.232), c(43.55,5.024), c(43.40,4.815), c(43.57,4.707), c(43.36,4.653), c(43.54,3.965), c(43.07,3.081), c(42.84,2.961), c(42.44,3.178), c(42.35,2.021), c(42.51,1.724), c(42.62,1.739), c(42.60,1.446), c(42.86,0.7161), c(42.69,0.6755), c(42.78,-0.5558), c(42.96,-0.7541), c(43.05,-1.440), c(43.25,-1.385), c(43.36,-1.781), c(43.64,-1.444), c(44.63,-1.209), c(44.67,-1.041), c(44.68,-1.246), c(45.56,-1.089), c(44.90,-0.5399), c(45.47,-0.7815), c(45.71,-1.239), c(45.90,-1.071), c(46.32,-1.115), c(46.49,-1.786), c(46.83,-2.125), c(47.04,-1.986), c(47.13,-2.171), c(47.25,-2.140));
        countryList.add(france);
        MapPolygonImpl spain = new MapPolygonImpl("Spain", c(36.16,-5.356), c(36.01,-5.614), c(36.19,-6.044), c(36.69,-6.432), c(36.86,-6.356), c(37.22,-6.960), c(37.25,-7.432), c(37.70,-7.447), c(38.17,-6.942), c(38.44,-7.317), c(39.02,-6.956), c(39.67,-7.533), c(39.67,-7.017), c(40.02,-6.871), c(40.19,-7.027), c(40.52,-6.796), c(41.03,-6.924), c(41.58,-6.190), c(41.69,-6.546), c(41.95,-6.602), c(41.81,-8.136), c(42.15,-8.198), c(41.95,-8.745), c(41.88,-8.875), c(42.11,-8.898), c(42.35,-8.580), c(42.25,-8.865), c(42.43,-8.656), c(42.48,-8.906), c(42.70,-8.720), c(42.52,-9.029), c(42.79,-8.916), c(42.75,-9.106), c(43.05,-9.259), c(43.29,-8.950), c(43.40,-8.330), c(43.76,-7.898), c(43.49,-7.045), c(43.65,-5.854), c(43.40,-4.515), c(43.51,-3.586), c(43.35,-3.151), c(43.36,-1.781), c(43.25,-1.385), c(43.05,-1.440), c(42.96,-0.7541), c(42.78,-0.5558), c(42.69,0.6755), c(42.86,0.7161), c(42.60,1.446), c(42.45,1.452), c(42.51,1.724), c(42.35,2.021), c(42.44,3.178), c(42.32,3.318), c(42.23,3.118), c(41.87,3.175), c(41.29,2.117), c(41.03,0.9644), c(40.80,0.7036), c(40.70,0.8853), c(40.61,0.5892), c(40.03,0.04750), c(39.44,-0.3376), c(38.98,-0.1433), c(38.73,0.2072), c(38.33,-0.5117), c(37.72,-0.8583), c(37.60,-0.7235), c(37.56,-1.327), c(37.37,-1.644), c(36.73,-2.123), c(36.84,-2.347), c(36.68,-2.769), c(36.72,-4.400), c(36.51,-4.640), c(36.41,-5.173), c(36.16,-5.335), c(36.16,-5.356));
        countryList.add(spain);
        MapPolygonImpl portugal = new MapPolygonImpl("Portugal", c(37.25,-7.432), c(37.01,-7.898), c(37.03,-8.989), c(37.44,-8.796), c(38.37,-8.802), c(38.41,-8.673), c(38.52,-8.769), c(38.42,-9.184), c(38.67,-9.274), c(38.77,-8.920), c(38.94,-8.994), c(38.72,-9.118), c(38.71,-9.476), c(39.36,-9.360), c(39.62,-9.086), c(40.69,-8.660), c(41.75,-8.881), c(41.95,-8.745), c(42.15,-8.198), c(41.81,-8.136), c(41.95,-6.602), c(41.69,-6.546), c(41.58,-6.190), c(41.03,-6.924), c(40.52,-6.796), c(40.19,-7.027), c(40.02,-6.871), c(39.67,-7.017), c(39.67,-7.533), c(39.02,-6.956), c(38.44,-7.317), c(38.17,-6.942), c(37.70,-7.447), c(37.25,-7.432));
        countryList.add(portugal);
        MapPolygonImpl sweden = new MapPolygonImpl("Sweden", c(58.99,11.43), c(58.91,11.62), c(59.10,11.75), c(59.59,11.67), c(59.70,11.90), c(59.85,11.82), c(60.11,12.49), c(60.52,12.59), c(61.00,12.22), c(61.06,12.64), c(61.36,12.86), c(61.73,12.12), c(62.27,12.29), c(62.59,12.05), c(62.89,12.03), c(63.02,12.17), c(63.27,11.94), c(63.59,12.15), c(64.05,12.94), c(64.02,13.99), c(64.18,14.15), c(64.47,14.12), c(64.58,13.67), c(65.31,14.49), c(65.82,14.63), c(66.12,14.51), c(66.28,15.47), c(66.48,15.36), c(67.02,16.35), c(67.24,16.36), c(67.41,16.09), c(67.90,16.73), c(68.09,17.27), c(67.95,17.88), c(68.17,18.16), c(68.51,18.10), c(68.34,19.95), c(68.47,20.20), c(68.54,19.96), c(68.79,20.35), c(69.04,20.10), c(69.06,20.58), c(68.48,22.05), c(68.39,22.83), c(67.95,23.66), c(67.87,23.49), c(67.48,23.43), c(67.42,23.77), c(67.16,23.57), c(66.80,24.00), c(66.31,23.66), c(65.81,24.17), c(65.70,23.08), c(65.90,22.68), c(65.76,22.68), c(65.86,22.37), c(65.63,22.25), c(65.54,22.40), c(65.71,21.77), c(65.54,22.19), c(65.38,21.47), c(65.25,21.66), c(65.34,21.26), c(65.15,21.62), c(64.82,21.04), c(64.76,21.30), c(64.68,21.12), c(64.44,21.59), c(64.14,20.96), c(63.87,20.77), c(63.43,19.70), c(63.55,19.43), c(63.18,19.06), c(63.27,18.90), c(63.00,18.29), c(62.96,18.58), c(62.78,18.20), c(62.99,17.70), c(62.60,18.05), c(62.49,17.33), c(62.23,17.65), c(61.94,17.35), c(61.63,17.50), c(61.72,17.15), c(61.69,17.26), c(61.60,17.10), c(61.43,17.22), c(61.40,17.11), c(60.94,17.15), c(60.68,17.28), c(60.50,17.69), c(60.59,17.96), c(60.24,18.60), c(60.31,18.32), c(60.19,18.42), c(60.10,18.82), c(59.89,19.07), c(59.80,18.86), c(59.77,19.08), c(59.40,18.24), c(59.33,18.09), c(59.36,17.96), c(59.39,17.78), c(59.59,17.82), c(59.64,17.65), c(59.44,17.73), c(59.65,17.38), c(59.47,17.40), c(59.61,16.56), c(59.49,16.03), c(59.38,16.88), c(59.47,16.70), c(59.49,16.84), c(59.26,17.29), c(59.32,18.09), c(59.44,18.48), c(59.32,18.64), c(59.31,18.28), c(59.13,18.31), c(58.86,17.89), c(59.17,17.67), c(58.91,17.63), c(58.75,17.35), c(58.63,16.19), c(58.49,16.93), c(58.48,16.42), c(58.37,16.77), c(58.18,16.83), c(57.99,16.62), c(57.88,16.76), c(57.99,16.51), c(57.74,16.70), c(57.89,16.42), c(57.70,16.71), c(57.41,16.67), c(57.27,16.47), c(57.05,16.58), c(56.21,16.00), c(56.09,15.87), c(56.16,14.69), c(56.01,14.74), c(56.06,14.56), c(55.83,14.22), c(55.54,14.37), c(55.39,14.19), c(55.34,13.38), c(55.40,12.98), c(55.57,12.92), c(55.68,13.06), c(56.30,12.46), c(56.23,12.80), c(56.42,12.62), c(56.46,12.90), c(56.64,12.88), c(56.92,12.35), c(57.43,12.10), c(57.42,11.90), c(57.69,11.89), c(57.70,11.70), c(58.20,11.88), c(58.32,11.80), c(58.33,11.73), c(58.23,11.53), c(58.40,11.20), c(59.00,11.11), c(59.05,11.22), c(59.10,11.32), c(58.99,11.43));
        countryList.add(sweden);
        MapPolygonImpl denmark = new MapPolygonImpl("Denmark", c(56.75,10.31), c(56.65,9.866), c(56.69,10.34), c(56.47,10.19), c(56.58,10.31), c(56.44,10.96), c(56.16,10.74), c(56.10,10.52), c(56.20,10.36), c(56.29,10.45), c(56.17,10.24), c(55.83,10.18), c(55.84,9.870), c(55.82,10.04), c(55.70,9.993), c(55.70,9.555), c(55.60,9.819), c(55.50,9.674), c(55.42,9.590), c(55.19,9.677), c(55.12,9.462), c(54.90,9.766), c(54.83,9.445), c(54.91,8.665), c(55.11,8.676), c(55.13,8.458), c(55.14,8.689), c(55.43,8.618), c(55.56,8.093), c(55.98,8.128), c(55.81,8.191), c(55.89,8.396), c(56.08,8.263), c(56.02,8.108), c(56.65,8.161), c(56.49,8.740), c(56.62,8.681), c(56.81,9.073), c(56.57,9.063), c(56.53,9.318), c(56.92,9.178), c(57.02,9.722), c(57.07,9.974), c(57.00,10.26), c(56.98,10.31), c(56.75,10.31));
        countryList.add(denmark);
        MapPolygonImpl croatia = new MapPolygonImpl("Croatia", c(42.94,17.58), c(43.40,16.88), c(43.50,15.99), c(43.68,15.94), c(44.20,15.15), c(44.26,15.52), c(44.58,14.99), c(45.12,14.83), c(45.31,14.48), c(45.35,14.32), c(44.77,13.90), c(45.16,13.60), c(45.48,13.59), c(45.49,14.39), c(45.67,14.61), c(45.47,14.83), c(45.43,15.17), c(45.76,15.32), c(45.84,15.70), c(46.22,15.66), c(46.53,16.29), c(46.48,16.61), c(45.84,17.66), c(45.75,18.41), c(45.91,18.82), c(45.78,18.96), c(45.57,18.90), c(45.52,19.10), c(45.38,18.98), c(45.22,19.42), c(45.21,19.17), c(44.86,19.04), c(45.08,18.63), c(45.05,17.86), c(45.27,16.91), c(45.23,16.53), c(45.00,16.29), c(45.21,16.02), c(45.17,15.79), c(44.81,15.74), c(44.49,16.13), c(44.20,16.15), c(43.46,17.25), c(43.05,17.65), c(42.94,17.58));
        countryList.add(croatia);
        MapPolygonImpl lithuania = new MapPolygonImpl("Lithuania", c(53.95,23.50), c(54.24,23.36), c(54.36,22.79), c(54.69,22.72), c(54.84,22.86), c(55.04,22.60), c(55.25,21.26), c(56.08,21.05), c(56.42,22.07), c(56.26,24.17), c(56.45,24.89), c(56.20,25.10), c(56.15,25.58), c(55.67,26.61), c(55.34,26.46), c(55.27,26.81), c(54.87,25.79), c(54.33,25.55), c(54.33,25.71), c(54.15,25.77), c(54.15,25.54), c(54.30,25.47), c(53.89,24.39), c(53.95,23.50));
        countryList.add(lithuania);
        MapPolygonImpl greece = new MapPolygonImpl("Greece", c(38.36,23.65), c(38.19,24.07), c(37.65,24.03), c(38.04,23.51), c(37.88,22.99), c(37.43,23.52), c(37.29,23.18), c(37.45,23.13), c(37.56,22.73), c(36.44,23.20), c(36.80,22.63), c(36.39,22.49), c(37.03,22.12), c(36.97,21.93), c(36.72,21.88), c(36.82,21.70), c(37.16,21.57), c(37.44,21.65), c(37.89,21.11), c(38.21,21.38), c(38.16,21.64), c(38.32,21.83), c(38.34,21.85), c(38.32,21.89), c(37.94,22.86), c(38.15,23.22), c(38.45,22.41), c(38.33,22.37), c(38.41,21.95), c(38.32,21.54), c(38.43,21.36), c(38.30,21.16), c(38.67,20.99), c(38.80,20.73), c(38.93,20.78), c(38.86,21.09), c(39.06,21.08), c(39.11,20.82), c(38.95,20.73), c(39.69,20.01), c(39.65,20.22), c(39.82,20.41), c(39.99,20.32), c(40.10,20.67), c(40.43,20.79), c(40.56,21.04), c(40.86,20.98), c(40.87,21.60), c(41.13,21.98), c(41.16,22.74), c(41.34,22.94), c(41.57,24.26), c(41.24,25.28), c(41.35,26.14), c(41.71,26.08), c(41.71,26.36), c(41.61,26.57), c(41.36,26.64), c(41.23,26.33), c(40.95,26.36), c(40.74,26.04), c(41.01,25.14), c(40.74,23.72), c(40.40,23.88), c(40.15,24.39), c(40.35,23.73), c(40.11,23.99), c(39.94,23.93), c(40.28,23.40), c(40.14,23.37), c(39.91,23.72), c(39.99,23.38), c(40.24,23.29), c(40.40,22.90), c(40.63,22.94), c(40.47,22.59), c(40.01,22.60), c(39.18,23.34), c(39.10,23.05), c(39.18,23.22), c(39.36,22.94), c(39.21,22.82), c(39.04,23.07), c(38.87,22.52), c(38.64,23.32), c(38.36,23.65));
        countryList.add(greece);
        MapPolygonImpl finland = new MapPolygonImpl("Finland", c(65.81,24.17), c(66.31,23.66), c(66.80,24.00), c(67.16,23.57), c(67.42,23.77), c(67.48,23.43), c(67.87,23.49), c(67.95,23.66), c(68.39,22.83), c(68.48,22.05), c(69.06,20.58), c(69.04,21.06), c(69.21,21.03), c(69.33,21.32), c(69.28,21.68), c(68.71,22.40), c(68.63,23.20), c(68.83,23.98), c(68.58,24.93), c(68.99,25.76), c(69.26,25.71), c(69.70,25.98), c(69.94,26.48), c(70.09,27.91), c(69.83,28.38), c(69.69,29.13), c(69.49,29.30), c(69.24,28.83), c(69.05,28.96), c(68.90,28.44), c(68.85,28.82), c(68.53,28.46), c(68.20,28.69), c(68.08,29.36), c(67.69,30.03), c(66.90,29.08), c(66.13,29.90), c(65.71,30.13), c(65.65,29.82), c(65.24,29.60), c(65.12,29.87), c(65.05,29.62), c(64.92,29.64), c(64.77,30.14), c(64.66,30.21), c(64.58,29.98), c(64.41,30.06), c(64.22,30.58), c(64.05,30.60), c(63.74,30.00), c(63.22,31.22), c(62.91,31.58), c(62.51,31.26), c(60.55,27.81), c(60.43,26.56), c(60.56,26.57), c(60.39,26.42), c(60.42,26.06), c(60.29,26.08), c(60.40,25.84), c(60.24,25.92), c(60.36,25.66), c(59.99,24.47), c(59.95,23.43), c(60.07,23.54), c(59.84,23.25), c(59.81,22.90), c(60.02,23.34), c(59.93,23.11), c(60.04,23.25), c(60.15,22.87), c(60.35,23.08), c(60.22,22.60), c(60.21,22.57), c(60.22,22.54), c(60.24,22.45), c(60.38,22.63), c(60.65,21.36), c(61.31,21.55), c(61.56,21.47), c(61.54,21.66), c(61.95,21.28), c(62.26,21.37), c(62.60,21.07), c(63.03,21.44), c(63.02,21.68), c(63.20,21.50), c(63.28,22.34), c(63.45,22.19), c(63.53,22.29), c(63.90,23.32), c(64.52,24.34), c(64.80,24.54), c(64.82,25.32), c(64.96,25.19), c(64.95,25.44), c(65.17,25.27), c(65.52,25.30), c(65.65,24.67), c(65.76,24.55), c(65.90,24.69), c(65.81,24.17));
        countryList.add(finland);
        MapPolygonImpl estonia = new MapPolygonImpl("Estonia", c(59.48,28.02), c(59.30,28.16), c(59.27,27.92), c(58.82,27.43), c(58.21,27.48), c(57.87,27.82), c(57.82,27.55), c(57.54,27.37), c(57.63,26.90), c(57.53,26.51), c(58.08,25.30), c(57.87,24.31), c(58.32,24.55), c(58.39,24.33), c(58.24,24.10), c(58.37,23.73), c(58.69,23.50), c(58.77,23.86), c(58.81,23.48), c(58.94,23.44), c(58.97,23.64), c(59.02,23.41), c(59.23,23.51), c(59.56,24.80), c(59.49,25.40), c(59.66,25.48), c(59.44,26.97), c(59.48,28.02));
        countryList.add(estonia);
        MapPolygonImpl turkey = new MapPolygonImpl("Turkey", c(35.93,35.92), c(36.31,35.79), c(36.65,36.22), c(36.92,36.01), c(36.58,35.56), c(36.81,34.66), c(36.18,33.71), c(36.09,32.57), c(36.52,32.06), c(36.85,31.05), c(36.84,30.62), c(36.20,30.40), c(36.30,30.21), c(36.12,29.68), c(36.35,29.15), c(36.68,29.05), c(36.88,28.46), c(36.55,27.98), c(36.80,28.12), c(36.68,27.37), c(37.04,28.32), c(36.97,27.26), c(37.15,27.32), c(37.23,27.60), c(37.41,27.42), c(37.35,27.19), c(37.95,27.27), c(38.26,26.28), c(38.43,26.51), c(38.45,26.39), c(38.67,26.40), c(38.31,26.69), c(38.45,27.16), c(38.64,26.73), c(38.87,27.06), c(38.96,26.80), c(39.10,26.87), c(39.26,26.64), c(39.55,26.95), c(39.46,26.11), c(39.98,26.17), c(40.38,26.70), c(40.45,27.12), c(40.31,27.51), c(40.38,27.88), c(40.53,27.75), c(40.49,28.02), c(40.36,27.95), c(40.37,29.06), c(40.48,29.08), c(40.53,28.78), c(40.64,28.98), c(40.72,29.93), c(40.91,29.13), c(41.03,29.03), c(41.22,29.16), c(41.09,31.23), c(41.72,32.28), c(42.02,33.34), c(41.94,34.72), c(42.09,34.98), c(41.64,35.49), c(41.73,35.97), c(41.24,36.43), c(41.36,36.81), c(41.15,37.13), c(40.91,38.36), c(41.11,39.41), c(40.92,40.15), c(41.52,41.53), c(41.43,42.47), c(41.58,42.83), c(41.11,43.46), c(40.74,43.75), c(40.45,43.58), c(40.10,43.67), c(40.02,44.35), c(39.71,44.78), c(39.63,44.81), c(39.78,44.61), c(39.42,44.40), c(39.38,44.03), c(38.84,44.30), c(38.40,44.31), c(38.34,44.48), c(37.90,44.22), c(37.72,44.62), c(37.44,44.59), c(37.30,44.82), c(37.15,44.79), c(36.97,44.32), c(37.32,44.12), c(37.23,43.62), c(37.38,42.79), c(37.11,42.36), c(37.29,42.18), c(37.09,41.55), c(37.12,40.77), c(36.67,39.22), c(36.91,38.24), c(36.66,37.37), c(36.83,36.66), c(36.49,36.55), c(36.23,36.68), c(36.21,36.39), c(35.82,36.17), c(35.93,35.92));
        countryList.add(turkey);
        MapPolygonImpl netherlands = new MapPolygonImpl("Netherlands", c(53.24,7.208), c(52.65,7.053), c(52.55,6.690), c(52.39,7.063), c(52.24,7.053), c(52.08,6.736), c(51.98,6.829), c(51.81,5.964), c(51.47,6.222), c(51.13,6.097), c(51.05,5.865), c(50.91,6.081), c(50.76,6.012), c(50.85,5.639), c(51.15,5.847), c(51.26,5.239), c(51.49,5.038), c(51.38,4.252), c(51.53,3.444), c(51.61,3.835), c(51.45,4.284), c(51.59,3.998), c(51.67,4.209), c(51.81,3.868), c(52.45,4.574), c(52.25,5.423), c(52.51,5.878), c(52.61,5.854), c(52.66,5.601), c(52.84,5.718), c(52.88,5.371), c(53.07,5.370), c(52.95,5.100), c(52.70,5.303), c(52.64,5.050), c(52.62,5.030), c(52.42,5.078), c(52.48,4.582), c(52.96,4.739), c(52.96,5.094), c(53.40,5.982), c(53.47,6.742), c(53.24,7.208));
        countryList.add(netherlands);
        MapPolygonImpl norway = new MapPolygonImpl("Norway", c(62.91,7.459), c(62.97,7.939), c(63.00,7.881), c(63.10,8.098), c(62.85,8.532), c(62.95,8.418), c(62.97,8.657), c(63.12,8.161), c(63.21,8.940), c(63.29,8.480), c(63.34,8.759), c(63.41,8.672), c(63.37,9.418), c(63.49,9.153), c(63.63,9.718), c(63.26,10.26), c(63.43,10.09), c(63.46,10.91), c(63.51,10.76), c(63.60,10.91), c(63.60,10.72), c(63.80,11.46), c(63.85,11.10), c(64.01,11.49), c(64.11,11.36), c(63.80,10.58), c(63.88,11.02), c(63.74,10.94), c(63.50,10.09), c(63.66,9.789), c(63.76,10.09), c(63.66,9.570), c(63.77,9.545), c(63.93,10.19), c(64.07,10.02), c(64.36,10.65), c(64.42,10.50), c(64.37,10.85), c(64.44,10.66), c(64.60,10.97), c(64.45,11.27), c(64.32,11.22), c(64.58,11.73), c(64.70,11.42), c(64.94,12.22), c(64.74,11.30), c(64.84,11.27), c(64.90,11.70), c(64.86,11.25), c(65.06,11.97), c(65.04,12.15), c(65.13,12.62), c(65.32,12.94), c(65.13,12.48), c(65.23,12.42), c(65.26,12.65), c(65.23,12.25), c(65.42,12.63), c(65.64,12.35), c(65.63,12.78), c(65.75,12.55), c(65.92,12.67), c(65.85,13.17), c(66.04,12.95), c(66.06,12.67), c(66.23,13.56), c(66.10,13.55), c(66.32,14.14), c(66.19,13.03), c(66.31,13.53), c(66.32,13.02), c(66.43,13.16), c(66.52,12.98), c(66.55,13.23), c(66.66,13.19), c(66.63,13.54), c(66.71,13.23), c(66.79,13.99), c(66.93,13.55), c(67.02,14.56), c(67.08,14.27), c(67.12,14.74), c(67.20,14.58), c(67.07,15.50), c(67.19,15.38), c(67.18,15.74), c(67.31,15.15), c(67.24,14.36), c(67.57,15.04), c(67.47,14.93), c(67.47,15.44), c(67.27,15.64), c(67.47,15.53), c(67.56,15.89), c(67.52,15.28), c(67.62,15.18), c(67.72,15.31), c(67.73,15.78), c(67.79,15.37), c(67.64,14.81), c(67.78,15.03), c(67.80,14.76), c(67.92,15.87), c(68.01,15.96), c(68.04,15.85), c(68.03,15.29), c(68.24,16.01), c(67.90,16.21), c(67.79,16.50), c(68.00,16.21), c(68.07,16.72), c(68.07,16.34), c(68.18,16.48), c(68.31,16.14), c(68.13,16.80), c(68.35,16.22), c(68.37,17.13), c(68.18,17.36), c(68.37,17.20), c(68.36,17.57), c(68.41,17.37), c(68.53,17.55), c(68.51,16.47), c(68.70,16.97), c(68.66,17.67), c(68.75,17.24), c(68.76,17.79), c(68.83,17.47), c(68.87,17.84), c(68.90,17.43), c(68.99,17.49), c(69.15,18.15), c(69.28,18.00), c(69.34,18.13), c(69.49,18.26), c(69.30,18.54), c(69.29,19.01), c(69.45,18.46), c(69.55,18.85), c(69.46,19.01), c(69.56,19.23), c(69.61,18.94), c(69.80,19.77), c(69.40,19.53), c(69.43,19.68), c(69.71,19.83), c(69.97,20.29), c(69.88,20.42), c(69.48,20.28), c(69.26,19.95), c(69.57,20.47), c(69.49,20.85), c(69.63,20.48), c(69.76,20.57), c(69.95,21.06), c(69.85,20.89), c(69.79,21.05), c(70.02,21.31), c(69.74,22.10), c(70.04,21.81), c(70.11,22.09), c(70.06,21.73), c(70.25,21.30), c(70.20,22.96), c(70.04,22.30), c(70.10,22.87), c(69.94,23.32), c(70.02,23.53), c(70.09,23.18), c(70.41,23.65), c(70.46,24.35), c(70.57,24.16), c(70.69,24.36), c(70.62,24.71), c(70.77,24.26), c(70.78,24.63), c(70.96,24.59), c(70.81,25.27), c(70.97,25.37), c(70.89,25.64), c(70.89,25.90), c(70.51,25.09), c(70.40,25.27), c(70.09,24.94), c(70.08,25.15), c(70.93,26.52), c(70.90,26.73), c(70.72,26.67), c(70.64,26.35), c(70.64,26.65), c(70.35,26.57), c(70.58,27.27), c(70.71,27.09), c(70.82,27.31), c(70.80,27.57), c(70.92,27.13), c(71.02,27.23), c(71.08,28.21), c(70.92,28.52), c(70.79,27.77), c(70.73,28.12), c(70.61,27.66), c(70.71,28.29), c(70.48,27.85), c(70.50,28.33), c(70.06,28.04), c(70.74,28.54), c(70.87,29.04), c(70.66,29.35), c(70.71,30.11), c(70.60,30.34), c(70.54,30.01), c(70.54,30.60), c(70.29,31.07), c(70.07,30.09), c(70.11,28.62), c(69.96,29.67), c(69.86,29.37), c(69.91,29.73), c(69.66,29.49), c(69.69,30.18), c(69.88,30.17), c(69.82,30.40), c(69.67,30.35), c(69.81,30.45), c(69.79,30.85), c(69.55,30.91), c(69.66,30.11), c(69.50,30.10), c(69.32,29.31), c(69.10,29.23), c(69.05,28.96), c(69.24,28.83), c(69.49,29.30), c(69.69,29.13), c(69.83,28.38), c(70.09,27.91), c(69.94,26.48), c(69.70,25.98), c(69.26,25.71), c(68.99,25.76), c(68.58,24.93), c(68.83,23.98), c(68.63,23.20), c(68.71,22.40), c(69.28,21.68), c(69.33,21.32), c(69.21,21.03), c(69.04,21.06), c(69.06,20.58), c(69.04,20.10), c(68.79,20.35), c(68.54,19.96), c(68.47,20.20), c(68.34,19.95), c(68.51,18.10), c(68.17,18.16), c(67.95,17.88), c(68.09,17.27), c(67.90,16.73), c(67.41,16.09), c(67.24,16.36), c(67.02,16.35), c(66.48,15.36), c(66.28,15.47), c(66.12,14.51), c(65.82,14.63), c(65.31,14.49), c(64.58,13.67), c(64.47,14.12), c(64.18,14.15), c(64.02,13.99), c(64.05,12.94), c(63.59,12.15), c(63.27,11.94), c(63.02,12.17), c(62.89,12.03), c(62.59,12.05), c(62.27,12.29), c(61.73,12.12), c(61.36,12.86), c(61.06,12.64), c(61.00,12.22), c(60.52,12.59), c(60.11,12.49), c(59.85,11.82), c(59.70,11.90), c(59.59,11.67), c(59.10,11.75), c(58.91,11.62), c(58.99,11.43), c(59.11,11.36), c(59.19,10.80), c(59.72,10.56), c(59.89,10.74), c(59.88,10.53), c(59.54,10.56), c(59.73,10.23), c(59.31,10.52), c(59.04,10.23), c(58.95,9.878), c(59.11,9.552), c(58.98,9.693), c(58.12,8.214), c(57.99,7.008), c(58.10,6.559), c(58.24,6.760), c(58.38,6.012), c(58.75,5.460), c(59.03,5.559), c(58.83,6.169), c(58.93,6.130), c(58.90,6.036), c(59.07,5.870), c(59.32,6.460), c(59.33,5.997), c(59.56,6.469), c(59.35,5.940), c(59.44,5.883), c(59.48,6.146), c(59.47,5.808), c(59.41,5.668), c(59.34,5.853), c(59.28,5.517), c(59.51,5.179), c(59.73,5.481), c(59.52,5.460), c(59.68,5.568), c(59.84,6.304), c(59.83,5.698), c(60.30,6.205), c(60.41,6.635), c(60.08,6.522), c(60.44,6.742), c(60.50,7.102), c(60.41,6.221), c(59.99,5.747), c(60.15,5.642), c(60.15,5.549), c(60.38,5.728), c(60.13,5.411), c(60.36,5.145), c(60.52,5.286), c(60.42,5.513), c(60.42,5.637), c(60.46,5.706), c(60.67,5.720), c(60.71,5.660), c(60.61,5.457), c(60.55,5.268), c(60.80,4.931), c(60.63,5.428), c(60.77,5.238), c(60.87,5.532), c(60.83,5.070), c(61.04,5.012), c(61.06,6.379), c(61.14,6.818), c(60.86,7.114), c(61.09,6.998), c(61.18,7.429), c(61.29,7.304), c(61.47,7.562), c(61.33,7.279), c(61.19,7.354), c(61.11,6.961), c(61.21,6.579), c(61.39,6.713), c(61.12,6.436), c(61.11,6.428), c(61.12,6.344), c(61.14,5.116), c(61.18,5.243), c(61.26,4.952), c(61.36,5.627), c(61.42,4.968), c(61.45,5.796), c(61.50,5.188), c(61.59,5.339), c(61.63,4.972), c(61.74,4.983), c(61.89,5.362), c(61.89,5.155), c(62.02,5.399), c(62.18,5.080), c(62.01,5.469), c(62.18,5.456), c(62.06,6.360), c(62.25,5.949), c(62.37,6.320), c(62.11,6.533), c(62.38,6.395), c(62.44,6.703), c(62.41,6.878), c(62.08,7.018), c(62.27,7.028), c(62.28,7.363), c(62.48,6.787), c(62.42,6.384), c(62.50,6.647), c(62.58,6.253), c(62.65,7.090), c(62.50,7.539), c(62.57,7.773), c(62.57,7.474), c(62.67,7.523), c(62.69,8.138), c(62.72,6.955), c(62.97,7.036), c(62.91,7.459));
        countryList.add(norway);
        
        final JCheckBox showAnger = new JCheckBox("Anger Visible");
        showAnger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (showAnger.isSelected())
            	{
            		for(int i = 0; i < countryList.size(); i++){
            			float j = data.getRegionalIndex(Region.getRegionIndex(countryList.get(i).getName()), Emotion.SAD);
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
