package uk.co.stircomp.emojemap.UI;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParse {

	
	public static void main (String[] args)
	{
		ArrayList<ArrayList<String>> coordinates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> coordinateX = new ArrayList<ArrayList<ArrayList<String>>>(), coordinateY = new ArrayList<ArrayList<ArrayList<String>>>(); 
		ArrayList<String> names = new ArrayList<String>();
		
		String filename = "world.xml";
		
		
		try {
		 
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList data = getNode(doc.getChildNodes(), "root").getChildNodes();
			
			for (int i = 1; i < data.getLength(); i += 2)
			{
				int arrayIndex = i - (i + 1) / 2;
				coordinates.add(new ArrayList<String>());
				NodeList country = data.item(i).getChildNodes();
				// coordinates
				// polygons 1
				NodeList polygons = getNode(getNode(country,"geometry").getChildNodes(),"MultiGeometry").getChildNodes();
				for (int j = 1; j < polygons.getLength(); j += 2)
				{
					// polygon -> outerBoundaryIs -> LinearRing -> coordinates
					//System.out.println();
					Node item = getNode(getNode(getNode(polygons.item(j).getChildNodes(),"outerBoundaryIs").getChildNodes(),"LinearRing").getChildNodes(),"coordinates");
					//System.out.println(item.getTextContent());
					coordinates.get(arrayIndex).add(item.getTextContent());
				}
				// name 3
				Node name = getNode(country,"Name");
				names.add(name.getTextContent());
			}
			
			
		    } catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		for (int i = 0; i < names.size(); i++)
		{
			coordinateX.add(new ArrayList<ArrayList<String>>());
			coordinateY.add(new ArrayList<ArrayList<String>>());
			for (int j = 0; j < coordinates.get(i).size(); j++)
			{
				coordinateX.get(i).add(new ArrayList<String>());
				coordinateY.get(i).add(new ArrayList<String>());
				
				String[] arr = coordinates.get(i).get(j).split(",");
				for (int k = 0; k < arr.length; k++)
				{
					// if even and is y
					if (arr[k].contains("0 ")){
						coordinateY.get(i).get(j).add(arr[k].substring(2, arr[k].length()-1));
						//System.out.println(arr[k]);
					}
					else if (k == 0)
					{
						coordinateY.get(i).get(j).add(arr[k]);
					}
					// if x
					else if (k != arr.length - 1)
					{
						coordinateX.get(i).get(j).add(arr[k]);
						//System.out.println(arr[k]);
					}
				}/*
				for (int k = 0; k < coordinateY.get(i).get(j).size(); k++)
					System.out.println(coordinateX.get(i).get(j).get(k) + " / " + coordinateY.get(i).get(j).get(k));*/
			}
			
			//System.out.println(names.get(i));
		}
		

		// MapPolygon andorra = new MapPolygonImpl("Andorra", c(1.44583614022,42.601945143), c(1.73860914671,42.6163911862), c(1.72360906013,42.5094360894), c(1.45152728555,42.4462450884), c(1.44583614022,42.601945143));
		// coutryList.add(andorra);
		
		
		for (int i = 0; i < names.size(); i++)
		{
			for (int j = 0; j < coordinates.get(i).size(); j++)
			{
				System.out.print("MapPolygonImpl " + names.get(i).toLowerCase() + j + " = new MapPolygonImpl(\"" + names.get(i) + "\", ");
			
				for (int k = 0; k < coordinateX.get(i).get(j).size(); k++)
				{
					String x = String.format("%.4g", Float.parseFloat(coordinateX.get(i).get(j).get(k)));
					String y = String.format("%.4g", Float.parseFloat(coordinateY.get(i).get(j).get(k)));
					if (k != coordinateX.get(i).get(j).size() - 1)
						System.out.print("c(" + x + "," + y + "), ");
					else
						System.out.print("c(" + x + "," + y + ")");
				}
				System.out.println(");");
				System.out.println("countryList.add(" + names.get(i).toLowerCase() + j + ");");
			}
			
		}

	}
	
	private static Node getNode(NodeList list, String name)
	{
		Node node = null;
		
		for (int i = 0; i < list.getLength(); i++)
		{
			if (list.item(i).getNodeName() == name)
				return list.item(i);
		}
		
		return node;
	}
}
