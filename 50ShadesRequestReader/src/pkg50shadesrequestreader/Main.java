/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg50shadesrequestreader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KDtreeKNN;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author martin
 */
public class Main {
    
    private Database database;
    private Gson gson;
    private double zoom;
    
    public Main() throws IOException {
        database = new Database("eventdata");
        gson = new GsonBuilder().create();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    
    class BoundingBox {
        
        public double bottomLeftX;
        public double bottomLeftY;
        public double topRightX;
        public double topRightY;

        @Override
        public String toString() {
            return "BLx=" + bottomLeftX + "; BLy=" + bottomLeftY + "; TRx=" + topRightX + 
                    "; TRy=" + topRightY;
        }
        
        
        
    }
    
    double lerp(double start, double end, double perc) {
        return (start + perc*(end-start));
    }

    class MyHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                InputStreamReader isr =
                        new InputStreamReader(t.getRequestBody(),"utf-8");
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                BoundingBox box = getBox(query);
                

                List<TrainingEntity> trainingData = database.getEntities(box);
                System.out.println("Size " + trainingData.size());
                Dataset dataset = new DefaultDataset();
                for (TrainingEntity entity : trainingData) {
                    double [] arr = new double[2];
                    arr[0] = entity.x;
                    arr[1] = entity.y;
                    Instance instance = new DenseInstance(arr, entity.label);
                    dataset.add(instance);
                }
                
                int x = trainingData.size();
                int val = ((x / 10) + 3);
                if (val < 5) val = 5;
                Classifier knn = new KNearestNeighbors(val);
                knn.buildClassifier(dataset);
                
                Map<String, List<Point>> outputTmp = new HashMap<String, List<Point>>();
                for (Instance instance : dataset) {
                    Object predictedClassValue = knn.classify(instance);
                    Object realClassValue = instance.classValue();
                    String key = predictedClassValue.toString();
                    List<Point> points = outputTmp.get(key);
                    if (points == null) {
                        points = new LinkedList<Point>();
                        outputTmp.put(key, points);
                    }
                    points.add(new Point(instance.value(0), instance.value(1)));
                }
                //outputTmp.remove("Other");
                Set<OutputEntity> output = new HashSet<OutputEntity>();
                for (Entry<String, List<Point>> entry : outputTmp.entrySet()) {
                    //System.out.println(entry.getKey() + " " + entry.getValue().size());
                    output.add(new OutputEntity(entry.getKey(), entry.getValue()));
                }
                
                Type type = new TypeToken<Set<OutputEntity>>(){}.getType();
                String response = gson.toJson(output, type);
                System.out.println("Done");
                t.sendResponseHeaders(200, response.length());
                
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        private BoundingBox getBox(String line) {
            System.out.println(line);
            BoundingBox box = new BoundingBox();
            String [] params = line.split("&");
            for (String param : params) {
                String [] sp = param.split("=");
                if (sp[0].equals("blx")) {
                    box.bottomLeftX = Double.parseDouble(sp[1]);
                } else if (sp[0].equals("bly")) {
                    box.bottomLeftY = Double.parseDouble(sp[1]);
                } else if (sp[0].equals("trx")) {
                    box.topRightX = Double.parseDouble(sp[1]);
                } else if (sp[0].equals("try")) {
                    box.topRightY = Double.parseDouble(sp[1]);
                } else if (sp[0].equals("zoom")) {
                    zoom = Integer.parseInt(sp[1]);
                }
            }
            System.out.println(box);
            return box;
        }
        
    }
    
    
    
    public static void main(String[] args) throws IOException {
        new Main();
    }
    
}
