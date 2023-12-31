/**
 * @author abdobella
 * Date: Dec 07, 2023
 * Time: 11:52:13 PM
*/
package com.nfs.app;
// import pi
import java.util.Scanner;

public class calc {

    public static void main(String[] args) {
        getWithPercentageRange();
        getWithLinesCount();
    }

    private static void getWithLinesCount() {
        System.out.print("Give the number of Lines you want to generate: ");
        Scanner sc = new Scanner(System.in);
        int numPoints = sc.nextInt();
        sc.close();

        float lineLength = 7.0f;
        float lineStrokeWidth = 2.0f;

        // Original point
        double circleOriginX = 125 - lineLength/2;
        double circleOriginY = 125;
        double circleRadius = 88;
        double radius = circleRadius - lineLength/2;

        // Start angle
        double startAngle = Math.toRadians(270);

        // Calculate points on the circle
        for (int i = 0; i < numPoints; i++) {
            double angle = startAngle + 2 * Math.PI * i / numPoints;
            double x = circleOriginX + radius * Math.cos(angle);
            double y = circleOriginY + radius * Math.sin(angle);

            System.out.println("<Line endX=\"" + lineLength + "\" layoutX=\"" + x + "\" layoutY=\"" + y + "\" rotate=\"" +
                    Math.toDegrees(angle) + "\" strokeWidth=\"" + lineStrokeWidth + "\" strokeLineCap=\"ROUND\" />");
        }
    }

    private static void getWithPercentageRange() {
        System.out.print("Give the percentage of the circle you want to cover (0-100): ");

        
        Scanner sc = new Scanner(System.in);
        int percentage = sc.nextInt();
        sc.close();

        // Validate the percentage input
        if (percentage < 0 || percentage > 100) {
            System.out.println("Invalid percentage. Please enter a value between 0 and 100.");
            return;
        }

        float lineLength = 10.0f;
        float lineStrokeWidth = 2.0f;
        
        // Original point
        double circleOriginX = 125 - lineLength/2;
        double circleOriginY = 125;
        double radius = 83 - lineLength/2 - lineStrokeWidth/2;
        
        // Calculate the angle range based on the percentage
        double startAngle = Math.toRadians(270); // Convert to radians
        double angleRange = 2 * Math.PI * percentage / 100.0;
        
        // Calculate points on the circle within the specified angle range
        for (double angle = startAngle; angle < startAngle + angleRange; angle += 0.1) {
            double x = circleOriginX + radius * Math.cos(angle);
            double y = circleOriginY + radius * Math.sin(angle);
            
            System.out.println("<Line endX=\"10.0\" layoutX=\"" + x + "\" layoutY=\"" + y + "\" rotate=\""
            + Math.toDegrees(angle) + "\" strokeWidth=\""+lineStrokeWidth+"\" strokeLineCap=\"ROUND\" stroke=\"#6b6b6b\" />");
        }
    }
    
}
