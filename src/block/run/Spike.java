/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package block.run;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author user
 */

public class Spike extends Obstacle{
    
    
    private static Image image = new Image("img/spike.png");
    private ImageView imageView = new ImageView(image);

    public Spike(int x, int y, World world) {
        super(x,y, world);
        
        getVisuals().getChildren().add(imageView);
    }
    
    
    /**
     * Spike moves across the map
     */
    
    public void produce() {
        
        System.out.println("Hello");
        
        /*
        
        if (true) {
            
            Timeline spikeObject = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                imageView.setLayoutX(imageView.getX() - 10);
                
            }
        }));
        
        spikeObject.play();
            
        }
        */    
    }      
}