//package tw.com.remecomic.util;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class KMeans {
//	
//    private int numClusters;
//    private List<Double> slope;
//    private List<Point> slope;
//    private List<Point> centroids;
//
//    public KMeans(int numClusters, List<Point> points) {
//        this.numClusters = numClusters;
//        this.points = points;
//        this.centroids = new ArrayList<>();
//        initializeCentroids();
//    }
//
//    private void initializeCentroids() {
//        Random random = new Random();
//        for (int i = 0; i < numClusters; i++) {
//            centroids.add(points.get(random.nextInt(points.size())));
//        }
//    }
//
//    public void calculate() {
//        boolean finish = false;
//        while (!finish) {
//            // Assign points to the nearest centroid
//            List<List<Point>> clusters = new ArrayList<>(numClusters);
//            for (int i = 0; i < numClusters; i++) {
//                clusters.add(new ArrayList<>());
//            }
//
//            for (Point point : points) {
//                int clusterIndex = nearestCentroid(point);
//                clusters.get(clusterIndex).add(point);
//            }
//
//            // Calculate new centroids
//            List<Point> newCentroids = new ArrayList<>();
//            for (List<Point> cluster : clusters) {
//                newCentroids.add(average(cluster));
//            }
//
//            // Check if centroids have changed
//            finish = centroids.equals(newCentroids);
//            centroids = newCentroids;
//        }
//    }
//
//    private int nearestCentroid(Point point) {
//        int nearest = -1;
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < centroids.size(); i++) {
//            double distance = point.distance(centroids.get(i));
//            if (distance < minDistance) {
//                minDistance = distance;
//                nearest = i;
//            }
//        }
//        return nearest;
//    }
//
//    private Point average(List<Point> points) {
//        double sumX = 0, sumY = 0;
//        for (Point point : points) {
//            sumX += point.getX();
//            sumY += point.getY();
//        }
//        return new Point(sumX / points.size(), sumY / points.size());
//    }
//
//    // Define the Point class with x and y coordinates and a method to calculate distance.
//    public static class Point {
//        private double x, y;
//
//        public Point(double x, double y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public double getX() { return x; }
//        public double getY() { return y; }
//
//        public double distance(Point other) {
//            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null || getClass() != obj.getClass()) return false;
//            Point point = (Point) obj;
//            return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
//        }
//    }
//
//    // Main method to test the KMeans algorithm
//    public static void main(String[] args) {
//        List<Point> points = new ArrayList<>();
//        // Add points to the list
//        // ...
//
//       
//    }
//
//
//}
